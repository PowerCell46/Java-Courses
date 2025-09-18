public class Main {
    public static void main(String[] args) {
//        String prompt = "Can you suggest a small but effective amount of exercises I can add to my program, to build my glutes?";
//        String prompt = "Tell me 5 interesting facts about Metal (genre).";
        String prompt = "What are the big differences between C++ and Rust?";

        Thread anthropicRequest = new AnthropicRequester(prompt);
        Thread openAiRequest = new OpenAiRequester(prompt);

        anthropicRequest.start();
        openAiRequest.start();
    }
}
