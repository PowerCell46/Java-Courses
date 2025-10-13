import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.function.Function;


public class Main {

    public static void main(String[] args) {
        String data = "";

        try {
            data = fetchStarWarsContent(Planet.REQUEST_URL_ENDPOINT);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed fetching the data.");
        }

        List<Planet> parsedEntries = parseStarWarsObjectsFromStrJson(
                data,
                Planet::initFromStringMap
        );

        parsedEntries.forEach(System.out::println);
    }

    private static String fetchStarWarsContent(String endpoint) throws IOException, InterruptedException {
        final String STAR_WARS_PLANETS_ENDPOINT = "https://swapi.info/api/" + endpoint;

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(STAR_WARS_PLANETS_ENDPOINT))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    // Split the JSON String to String entries
    private static List<String> extractStrEntriesFromJsonString(String data) {
        List<String> strJsonPlanets = new ArrayList<>(50);
        StringBuilder currentPlanetData = new StringBuilder();

        for (char ch : data.toCharArray()) {
            if (ch == '{') {
                currentPlanetData.setLength(0);

            } else if (ch == '}') {
                strJsonPlanets.add(currentPlanetData.toString());
                currentPlanetData.setLength(0);

            } else {
                currentPlanetData.append(ch);
            }
        }

        return strJsonPlanets;
    }

    // Extract the JSON entry String to a Map of key value pairs
    private static Map<String, String> parseJsonToStrStrHashmap(String[] currentJsonEntryData) {
        Map<String, String> currentEntryStringValues = new HashMap<>();

        boolean isReadingArray = false;
        String arrayPropertyName = "";
        List<String> arrayValues = new ArrayList<>();

        for (String currentJsonEntryLine : currentJsonEntryData) {
            currentJsonEntryLine = currentJsonEntryLine.trim(); // Remove spaces, tabs, new lines

            if (!isReadingArray && !currentJsonEntryLine.contains("[")) { // Normal "key: value"
                String[] splitRow = currentJsonEntryLine.split("\": ");
                currentEntryStringValues.put(
                        splitRow[0].substring(1, splitRow[0].length()),
                        splitRow[1].contains("\"") ? splitRow[1].substring(1, splitRow[1].length() - 1) : splitRow[1]
                );

            } else if (currentJsonEntryLine.contains("[")) { // Start of an array entry
                isReadingArray = true;
                arrayPropertyName = currentJsonEntryLine.substring(
                        1,
                        currentJsonEntryLine.indexOf(":") - 1
                );

                if (currentJsonEntryLine.contains("]")) { // Handling empty array
                    currentEntryStringValues.put(arrayPropertyName, "");
                    isReadingArray = false;
                    continue;
                }

                // Parse the entry on the same line
                String value = currentJsonEntryLine.substring(currentJsonEntryLine.indexOf("[") + 1).trim();
                arrayValues = new ArrayList<>(List.of(value.substring(1, value.length() - 1)));

            } else if (currentJsonEntryLine.contains("]")) { // End of an array entry
                isReadingArray = false;

                // Parse the entry on the same line
                String value = currentJsonEntryLine.substring(1, currentJsonEntryLine.lastIndexOf("\""));
                arrayValues.add(value);

                // Add the array entry to the string map
                currentEntryStringValues.put(
                        arrayPropertyName,
                        String.join(", ", arrayValues)
                );

            } else { // Add array value
                arrayValues.add(currentJsonEntryLine.substring(
                        1,
                        currentJsonEntryLine.lastIndexOf("\"")
                ));
            }
        }

        return currentEntryStringValues;
    }

    // Iterate through the String entries and parse them to Map of key value pairs, then to objects
    private static <T extends Base> List<T> parseStarWarsObjectsFromStrJson(
            String data,
            Function<Map<String, String>, T> factory
    ) {

        List<T> parsedEntries = new ArrayList<>(50);
        List<String> strJsonEntries = extractStrEntriesFromJsonString(data);

        for (String strJsonEntry : strJsonEntries) {
            String[] currentJsonEntryData = strJsonEntry.split(",\n\t\t");
            Map<String, String> currentEntryStringValues = parseJsonToStrStrHashmap(currentJsonEntryData);

            T currentParsedEntry = factory.apply(currentEntryStringValues);
            parsedEntries.add(currentParsedEntry);
        }

        return parsedEntries;
    }
}
