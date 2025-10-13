import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;


public sealed class Base permits Planet, Film, Person, Vehicle, Species {

    protected Instant created;

    protected Instant edited;

    protected String currentEntryUrl;

    public Base(Instant created, Instant edited, String currentEntryUrl) {
        this.created = created;
        this.edited = edited;
        this.currentEntryUrl = currentEntryUrl;
    }

    public String getEdited() {
        return this.edited != null ? this.edited.toString().substring(0, 10) : "Unknown";
    }

    // Parse methods

    /******************************************************/

    protected static String parseString(String value) {
        if (value == null)
            return null;

        value = value.trim();

        if (
            // @formatter:off
                value.equals("unknown") ||
                value.equals("n/a") ||
                value.equals("none") ||
                value.equals("null")
            // @formatter:on
        )
            return null;

        return value;
    }

    protected static Integer parseInteger(String value) {
        value = parseString(value);

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    protected static Double parseDouble(String value) {
        value = parseString(value);

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    protected static Long parseLong(String value) {
        value = parseString(value);

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    protected static LocalDate parseLocalDate(String value) {
        value = parseString(value);

        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException | NullPointerException e) {
            return null;
        }
    }

    protected static List<String> parseStringList(String value) {
        value = parseString(value);

        return Arrays.stream(value.split(", ")).toList();
    }

    // Format methods

    /******************************************************/

    protected String formatInteger(Integer number) {
        return number != null ? String.format("%,d", number) : "Unknown";
    }

    protected String formatLong(Long number) {
        if (number == null) return "Unknown";
        if (number > 1_000_000_000) return String.format("%.1fB", number / 1_000_000_000.0);
        if (number > 1_000_000) return String.format("%.1fM", number / 1_000_000.0);
        return String.format("%,d", number);
    }

    protected String formatDouble(Double number) {
        return number != null ? String.format("%.1f", number) : "Unknown";
    }

    protected String formatString(String text) {
        if (text == null) return "Unknown";
        return text.length() > 50 ? text.substring(0, 47) + "..." : text;
    }

    protected String formatLocalDate(LocalDate date) {
        return date != null ? date.toString() : "Unknown";
    }

    protected String formatStringList(List<String> list) {
        if (list == null || list.isEmpty()) return "Unknown";
        return String.join(", ", list);
    }

    protected Integer getListSize(List<?> list) {
        return list != null ? list.size() : 0;
    }
}
