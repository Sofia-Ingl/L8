package client;

import client.util.AlertManager;
import client.util.Encryption;
import client.util.ScriptProcessor;
import shared.data.Movie;
import shared.exceptions.ScriptException;
import shared.serializable.ClientRequest;
import shared.serializable.Pair;
import shared.serializable.ServerResponse;
import shared.serializable.User;
import shared.util.CommandExecutionCode;
import shared.util.Serialization;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Client {

    private final String host;
    private final int port;
    private SocketChannel socketChannel;
    private Selector selector;
    private User user;
    private ScriptProcessor scriptProcessor;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void setConnectionWithServer() throws IOException {
        SocketAddress socketAddress = new InetSocketAddress(host, port);
        socketChannel = SocketChannel.open(socketAddress);
        socketChannel.configureBlocking(false);
        selector = Selector.open();
    }

    public boolean processAuthorization(String login, String pass, boolean isRegistration) {
        ServerResponse response;
        ClientRequest request;
        String command;

        try {
            command = (isRegistration) ? "register" : "login";
            user = new User(login, Encryption.getEncodedPassword(pass));
            request = new ClientRequest(command, "", null, user);
            response = processRequest(request);
            if (response.getCode().equals(CommandExecutionCode.ERROR)) {
                AlertManager.displayError(response.getResponseToPrint());
                return false;
            }
            return true;
        } catch (IOException e) {
            try {
                resetConnection();
            } catch (IOException ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void reAuthorisation() {
        ServerResponse response;
        ClientRequest request;
        request = new ClientRequest("login", "", null, user);
        try {
            processRequest(request);
        } catch (IOException | ClassNotFoundException ignored) {
        }
    }

    public LinkedHashSet<Movie> processUserRequest(String command, String commandArg, Movie obj) {
        ServerResponse response;
        ClientRequest request = new ClientRequest(command, commandArg, obj, user);
        try {
            response = processRequest(request);
            if (!response.getResponseToPrint().equals("")) {
                if (response.getCode().equals(CommandExecutionCode.ERROR)) {
                    AlertManager.displayError(response.getResponseToPrint());
                } else {
                    AlertManager.displayCommandResult(request.getCommand(), response);
                }
            }
            return response.getMovieSet();
        } catch (IOException e) {
            try {
                resetConnection();
                reAuthorisation();
                AlertManager.displayInfo("NewTrial");
            } catch (IOException exception) {
                AlertManager.displayError("ConnectionError");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Pair<Boolean, HashSet<Movie>> processScript(String path) {
        ServerResponse response;
        ClientRequest request;
        HashSet<Movie> movies = null;
        scriptProcessor.setFirstScript(path);
        try {
            while (true) {
                request = scriptProcessor.generateRequest();
                if (request == null) {
                    if (scriptProcessor.isDone()) {
                        break;
                    }
                } else {
                    response = processRequest(request);
                    movies = response.getMovieSet();
                    if (response.getCode().equals(CommandExecutionCode.ERROR)) throw new ScriptException();
                }
            }
            return new Pair<>(Boolean.TRUE, movies);
        } catch (ScriptException | ClassNotFoundException e) {
            AlertManager.displayError("ScriptError");
        } catch (IOException e) {
                try {
                    resetConnection();
                    reAuthorisation();
                    AlertManager.displayInfo("NewTrial");
                } catch (IOException exception) {
                    AlertManager.displayError("ConnectionError");
                }
        }
        return new Pair<>(Boolean.FALSE, movies);
    }


    private ServerResponse processRequest(ClientRequest request) throws IOException, ClassNotFoundException {
        socketChannel.register(selector, SelectionKey.OP_WRITE);
        selector.select();
        sendClientRequest(request);
        selector.selectedKeys().clear();
        socketChannel.register(selector, SelectionKey.OP_READ);
        selector.select();
        ServerResponse response = getResponse(getResponseBytes());
        selector.selectedKeys().clear();
        return response;
    }


    public void resetConnection() throws IOException {

        if (socketChannel != null) socketChannel.close();
        if (socketChannel != null) selector.close();
        setConnectionWithServer();
    }

    private void sendClientRequest(ClientRequest clientRequest) throws IOException {
        socketChannel.write(ByteBuffer.wrap(Serialization.serialize(clientRequest)));
    }

    private byte[] getResponseBytes() throws IOException {
        byte[] buffer = new byte[65536];
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        socketChannel.read(byteBuffer);
        return byteBuffer.array();
    }

    private ServerResponse getResponse(byte[] bytes) throws IOException, ClassNotFoundException {
        return (ServerResponse) Serialization.deserialize(bytes);
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public User getUser() {
        return user;
    }

    public void setScriptProcessor(ScriptProcessor scriptProcessor) {
        this.scriptProcessor = scriptProcessor;
        scriptProcessor.setClient(this);
    }
}
