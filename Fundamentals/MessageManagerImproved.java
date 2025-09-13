import lombok.AllArgsConstructor;

import java.util.*;

interface MessageHandler {
    void execute(
            Map<String, User> messagesStore,
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
    private Integer sent;
    private Integer received;

    @Override
    public void execute(
            Map<String, User> messagesStore,
            String[] currentInputData,
            Integer MAX_MESSAGES_PER_PERSON
    ) {
        setParameters(currentInputData);

        messagesStore.putIfAbsent(username, new User(username, sent, received));
    }

    private void setParameters(String[] currentInputData) {
        username = currentInputData[1];
        sent = Integer.parseInt(currentInputData[2]);
        received = Integer.parseInt(currentInputData[3]);
    }
}

class MessageMessage implements MessageHandler {
    private static final String REACH_CAPACITY_ERROR = "%s reached the capacity!";
   
    private String sender;
    private String receiver;

    @Override
    public void execute(
            Map<String, User> messagesStore,
            String[] currentInputData,
            Integer MAX_MESSAGES_PER_PERSON
    ) {
        setParameters(currentInputData);

        if (messagesStore.containsKey(sender) && messagesStore.containsKey(receiver)) {
            if (messagesStore.get(sender).getTotalMessages() + 1 >= MAX_MESSAGES_PER_PERSON) {
                System.out.printf(REACH_CAPACITY_ERROR, receiver);
                messagesStore.remove(sender);
            } else {
                messagesStore.get(sender).incrementSentMessages();
            }

            if (messagesStore.get(receiver).getTotalMessages() + 1 >= MAX_MESSAGES_PER_PERSON) {
                System.out.printf(REACH_CAPACITY_ERROR, receiver);
                messagesStore.remove(receiver);
            } else {
                messagesStore.get(receiver).incrementReceivedMessages();
            }
        }
    }

    private void setParameters(String[] currentInputData) {
        sender = currentInputData[1];
        receiver = currentInputData[2];
    }
}

class MessageEmpty implements MessageHandler {
    private static final String EMPTY_ALL_COMMAND = "All";
   
    private String username;

    @Override
    public void execute(
            Map<String, User> messagesStore,
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

@AllArgsConstructor
class User {
    private String username;
    private Integer sentMessagesCount;
    private Integer receivedMessagesCount;

    public Integer getTotalMessages() {
        return sentMessagesCount + receivedMessagesCount;
    }

    public void incrementSentMessages() {
        ++sentMessagesCount;
    }

    public void incrementReceivedMessages() {
        ++receivedMessagesCount;
    }

    @Override
    public String toString() {
        return String.format("%s - %d", username, getTotalMessages());
    }
}

public class MessageManagerImproved {
    private static final String END_READ_INPUT = "Statistics";
    private static final String INPUT_SPLIT_TOKEN = "=";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Integer MAX_MESSAGES_CAPACITY_PER_PERSON = Integer.parseInt(scanner.nextLine());
        Map<String, User> messagesStore = new LinkedHashMap<>();

        while(true) {
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

    private static void printStatistics(Map<String, User> messagesStore) {
        if (!messagesStore.isEmpty()) {
            System.out.println("Users count: " + messagesStore.keySet().size());

            messagesStore
                    .values()
                    .forEach(System.out::println);
        }
    }
}

/*
10
Add=Berg=9=0
Add=Kevin=0=0
Message=Berg=Kevin
Add=Mark=5=4
Statistics
*/

/*
20
Add=Mark=3=9
Add=Berry=5=5
Add=Clark=4=0
Empty=Berry
Add=Blake=9=3
Add=Michael=3=9
Add=Amy=9=9
Message=Blake=Amy
Message=Michael=Amy
Statistics
*/

/*
12
Add=Bonnie=3=5
Add=Johny=4=4
Empty=All
Add=Bonnie=3=3
Statistics
*/
