package client;

import client.util.AlertManager;
import client.util.Encryption;
import javafx.scene.control.Alert;
import shared.data.Movie;
import shared.serializable.ClientRequest;
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
import java.util.LinkedHashSet;

public class Client {

    private final String host;
    private final int port;
    private SocketChannel socketChannel;
    private Selector selector;
    private User user;

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
                AlertManager.message("Error", response.getResponseToPrint(), Alert.AlertType.ERROR);
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

    public LinkedHashSet<Movie> processUserRequest(String command, String commandArg, Movie obj) {
        ServerResponse response;
        ClientRequest request = new ClientRequest(command, commandArg, obj, user);
        try {
            response = processRequest(request);
            return response.getMovieSet();
        } catch (IOException e) {
            try {
                resetConnection();
            } catch (IOException ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
}
