import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.http.HttpClient;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.writeString;


public interface Requester {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    HttpClient client = HttpClient.newBuilder().build();

    default void writeResponse(String response, String requestType) throws IOException {
        final String OUTPUT_DIRECTORY = "./src/data/markdown/";

        createDirectories(Path.of(OUTPUT_DIRECTORY)); // Make sure the folder structure is Ok

        writeString(
                Path.of(OUTPUT_DIRECTORY + generateFileName(requestType)),
                response
        );
    }

    default String generateFileName(String requestType) {
        LocalDateTime now = LocalDateTime.now();

        String date_dd_mm_yyyy = now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String timestamp = now.format(DateTimeFormatter.ofPattern("hh-mm-a"));

        return String.format(
                "%s_%s_%s.md",
                requestType,
                date_dd_mm_yyyy,
                timestamp
        );
    }
}
