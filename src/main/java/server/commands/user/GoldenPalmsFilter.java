package server.commands.user;

import server.commands.abstracts.UserCommand;
import server.commands.util.CommandResultContainer;
import shared.serializable.Pair;
import shared.serializable.User;

public class GoldenPalmsFilter extends UserCommand {

    public GoldenPalmsFilter() {
        super("filter_greater_than_golden_palm_count", "вывести элементы, значение поля goldenPalmCount которых больше заданного", false, true, true);
    }

    @Override
    public Pair<Boolean, CommandResultContainer> execute(String arg, Object obj, User user) {

        CommandResultContainer container = new CommandResultContainer();
        try {
            if (arg.isEmpty()) {
                throw new IllegalArgumentException();
            }
            if (!arg.trim().matches("\\d+")) {
                throw new IllegalArgumentException();
            } else {
                long goldenPalms = Long.parseLong(arg.trim());

                String result = getCollectionStorage().returnGreaterThanGoldenPalms(goldenPalms);

                return new Pair<>(true, container);

            }

        } catch (IllegalArgumentException e) {
            container.setResult("ScriptError");
        }
        return new Pair<>(false, container);
    }
}

