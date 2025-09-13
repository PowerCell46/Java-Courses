import java.util.*;

interface MessageHandler {
    void execute(
            Map<String, List<Integer>> messagesStore,
            String[] currentInputData,
            Integer MAX_MESSAGES_PER_PERSON
    );

    static MessageHandler of(String messageHandlerType) {
        return switch (messageHandlerType) {
            case "Add" -> new MessageAdd();
            case "Message" -> new MessageMessage();
            case "Empty" -> new MessageEmpty();
            default -> throw new RuntimeException("Invalid messageHandlerType.");
        };
    }
}

class MessageAdd implements MessageHandler {
    private String username;
    private Integer send;
    private Integer received;

    @Override
    public void execute(
            Map<String, List<Integer>> messagesStore,
            String[] currentInputData,
            Integer MAX_MESSAGES_PER_PERSON
    ) {
        setParameters(currentInputData);

        messagesStore.putIfAbsent(username, List.of(send, received));
    }

    private void setParameters(String[] currentInputData) {
        username = currentInputData[1];
        send = Integer.parseInt(currentInputData[2]);
        received = Integer.parseInt(currentInputData[3]);
    }
}

class MessageMessage implements MessageHandler {
    private String sender;
    private String receiver;

    @Override
    public void execute(
            Map<String, List<Integer>> messagesStore,
            String[] currentInputData,
            Integer MAX_MESSAGES_PER_PERSON
    ) {
        setParameters(currentInputData);

        if (messagesStore.containsKey(sender) && messagesStore.containsKey(receiver)) {
            if (sumSentAndReceivedMessages(messagesStore, sender) + 1 >= MAX_MESSAGES_PER_PERSON) {
                System.out.printf("%s reached the capacity!", sender);
                messagesStore.remove(sender);
            } else {
                messagesStore
                        .put(sender, List.of(messagesStore.get(sender).get(0) + 1, messagesStore.get(sender).get(1)));
            }

            if (sumSentAndReceivedMessages(messagesStore, receiver) + 1 >= MAX_MESSAGES_PER_PERSON) {
                System.out.printf("%s reached the capacity!", receiver);
                messagesStore.remove(receiver);
            } else {
                messagesStore
                        .put(receiver, List.of(messagesStore.get(receiver).get(0), messagesStore.get(receiver).get(1) + 1));
            }
        }
    }
    
    private void setParameters(String[] currentInputData) {
        sender = currentInputData[1];
        receiver = currentInputData[2];
    }
    
    private Integer sumSentAndReceivedMessages(Map<String, List<Integer>> messagesStore, String username) {
        return messagesStore.get(username).get(0) + messagesStore.get(username).get(1) + 1;   
    }
}

class MessageEmpty implements MessageHandler {
    private static final String EMPTY_ALL_COMMAND = "All";
    private String username;

    @Override
    public void execute(
            Map<String, List<Integer>> messagesStore,
            String[] currentInputData,
            Integer MAX_MESSAGES_PER_PERSON
    ) {
        setParameters(currentInputData);

        if (username.equals(EMPTY_ALL_COMMAND)) {
            messagesStore.clear();
        } else {
            messagesStore.remove(username);
        }
    }

    private void setParameters(String[] currentInputData) {
        username = currentInputData[1];
    }
}


public class MessageManager {
    private static final String END_READ_INPUT = "Statistics";
    private static final String INPUT_SPLIT_TOKEN = "=";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Integer MAX_MESSAGES_CAPACITY_PER_PERSON = Integer.parseInt(scanner.nextLine());
        Map<String, List<Integer>> messagesStore = new LinkedHashMap<>();

        while (true) {
            String currentInput = scanner.nextLine();

            if (currentInput.equals(END_READ_INPUT))
                break;

            String[] currentInputData = currentInput.split(INPUT_SPLIT_TOKEN);

            MessageHandler messageHandler = MessageHandler.of(currentInputData[0]);

            messageHandler.execute(
                    messagesStore,
                    currentInputData,
                    MAX_MESSAGES_CAPACITY_PER_PERSON
            );
        }

        printStatistics(messagesStore);
    }

    private static void printStatistics(Map<String, List<Integer>> messagesStore) {
        if (!messagesStore.isEmpty()) {
            System.out.println("Users count: " + messagesStore.keySet().size());

            messagesStore
                    .keySet()
                    .forEach(username -> System.out.printf(
                            "%s - %d\n",
                            username,
                            messagesStore.get(username).get(0) + messagesStore.get(username).get(1)));
        }
    }
}
