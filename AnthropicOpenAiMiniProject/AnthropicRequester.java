import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


public class AnthropicRequester extends Thread implements Requester {

    private static final String ENDPOINT = "https://api.anthropic.com/v1/messages";

    private static final String X_AUTH_TOKEN = System.getenv("ANTHROPIC_API_KEY");

    private String prompt;

    public AnthropicRequester(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public void run() {
        final String REQUEST_BODY = constructRequestBody();
        final String ANTHROPIC_VERSION = "2023-06-01";

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(ENDPOINT))
                .headers("content-type", "application/json")
                .header("x-api-key", X_AUTH_TOKEN)
                .header("anthropic-version", ANTHROPIC_VERSION)
                .method("POST", HttpRequest.BodyPublishers.ofString(REQUEST_BODY))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Anthropic response status code: " + response.statusCode());

            AnthropicResponse anthropicResponse = gson.fromJson(response.body(), AnthropicResponse.class);

            final String requestType = "anthropicResponse";
            writeResponse(
                    anthropicResponse.getContent().getFirst().getText(),
                    requestType
            );

        } catch(IOException | InterruptedException e) {
            System.out.println("Error occurred during the request.");
            throw new RuntimeException(e);
        }
    }

    private String constructRequestBody() {
        final String ANTHROPIC_MODEL = "claude-sonnet-4-20250514";
        final Integer MAX_TOKENS = 1024;

        return String.format("""
                        {
                            "model": "%s",
                            "max_tokens": %d,
                            "messages": [
                                {"role": "user", "content": "%s"}
                            ]
                        }
                        """,
                ANTHROPIC_MODEL,
                MAX_TOKENS,
                prompt
        );
    }
}


@Getter
class AnthropicResponse {

    private String id;

    private String type;

    private String role;

    private String model;

    private List<Content> content;

    @Getter
    static class Content {

        private String type;

        private String text;
    }

    private Usage usage;

    @Getter
    static class Usage {

        @SerializedName("input_tokens")
        private Integer inputTokens;

        @SerializedName("cache_creation_input_tokens")
        private Integer cacheCreationInputTokens;

        @SerializedName("cache_read_input_tokens")
        private Integer cacheReadInputTokens;
    }

    @SerializedName("output_tokens")
    private Integer outputTokens;

    @SerializedName("service_tier")
    private String serviceTier;
}
