import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class OpenAiRequester extends Thread implements Requester {

    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";

    private static final String X_AUTH_TOKEN = System.getenv("OPEN_AI_API_KEY");

    private String prompt;

    private String systemRole = "You are a precise and practical mentor combining three domains: expert software " +
            "engineer (clean code, debugging, architecture), certified fitness coach " +
            "(science-based training & nutrition), and experienced investor (fundamental & technical analysis). " +
            "Provide concise, evidence-backed advice, actionable steps, and prioritize clarity" +
            " and efficiency in all responses.";

    public OpenAiRequester(String prompt) {
        this.prompt = prompt;
    }

    public OpenAiRequester(String prompt, String systemRole) {
        this.prompt = prompt;
        this.systemRole = systemRole;
    }

    @Override
    public void run() {
        final String requestBody = constructRequestBody();

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(ENDPOINT))
                .header("Authorization", "Bearer " + X_AUTH_TOKEN)
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("OpenAi response status code: " + response.statusCode());

            OpenAiResponse openAiResponse = gson.fromJson(response.body(), OpenAiResponse.class);

            final String requestType = "openAiResponse";
            writeResponse(
                    openAiResponse.getChoices().getFirst().getMessage().getContent(),
                    requestType
            );

        } catch(IOException | InterruptedException e) {
            System.out.println("Error occurred during the request.");
            throw new RuntimeException(e);
        }
    }

    private String constructRequestBody() {
        final String OPEN_AI_MODEL = "gpt-4o-mini";
        return String.format("""
                        {
                            "model": "%s",
                            "messages": [
                                {"role": "system", "content": "%s"},
                                {"role": "user", "content": "%s"}
                            ]
                        }
                        """,
                OPEN_AI_MODEL,
                systemRole,
                prompt
        );
    }
}


@Getter
class OpenAiResponse {

    private String id;

    private String object;

    private Long created;

    private String model;

    private List<Choice> choices;

    @Getter
    static class Choice {

        private Message message;

        @Getter
        static class Message {

            private String role;

            private String content;
        }
    }

    private Usage usage;

    @Getter
    static class Usage {

        @SerializedName("prompt_tokens")
        private Integer promptTokens;

        @SerializedName("completion_tokens")
        private Integer completionTokens;

        @SerializedName("total_tokens")
        private Integer totalTokens;

        @SerializedName("prompt_tokens_details")
        private PromptTokensDetails promptTokensDetails;

        @Getter
        static class PromptTokensDetails {

            @SerializedName("cached_tokens")
            private Integer cachedTokens;

            @SerializedName("audio_tokens")
            private Integer audioTokens;
        }
    }

    @SerializedName("service_tier")
    private String serviceTier;
}
