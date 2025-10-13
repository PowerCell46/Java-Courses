import java.time.Instant;
import java.util.List;
import java.util.Map;


public final class Species extends Base {

    public static final String REQUEST_URL_ENDPOINT = "species";

    private String name;

    private String classification;

    private Integer averageHeight;

    private List<String> skinColors;

    private List<String> hairColors;

    private List<String> eyeColors;

    private Integer averageLifespan;

    private String homeWorld;

    private String language;

    private List<String> people;

    private List<String> films;

    public Species(
            String name,
            String classification,
            Integer averageHeight,
            List<String> skinColors,
            List<String> hairColors,
            List<String> eyeColors,
            Integer averageLifespan,
            String homeWorld,
            String language,
            List<String> people,
            List<String> films,
            Instant created,
            Instant edited,
            String currentEntryUrl
    ) {
        super(created, edited, currentEntryUrl);
        this.name = name;
        this.classification = classification;
        this.averageHeight = averageHeight;
        this.skinColors = skinColors;
        this.hairColors = hairColors;
        this.eyeColors = eyeColors;
        this.averageLifespan = averageLifespan;
        this.homeWorld = homeWorld;
        this.language = language;
        this.people = people;
        this.films = films;
    }

    public static Species initFromStringMap(Map<String, String> stringStringMap) {
        return new Species(
                parseString(stringStringMap.get("name")),
                parseString(stringStringMap.get("classification")),
                parseInteger(stringStringMap.get("average_height")),
                parseStringList(stringStringMap.get("skin_colors")),
                parseStringList(stringStringMap.get("hair_colors")),
                parseStringList(stringStringMap.get("eye_colors")),
                parseInteger(stringStringMap.get("average_lifespan")),
                parseString(stringStringMap.get("homeworld")),
                parseString(stringStringMap.get("language")),
                parseStringList(stringStringMap.get("people")),
                parseStringList(stringStringMap.get("films")),
                Instant.parse(stringStringMap.get("created")),
                Instant.parse(stringStringMap.get("edited")),
                parseString(stringStringMap.get("url"))
        );
    }

    @Override
    public String toString() {
        return String.format("""
                        Species: %s
                        ├─ Basic Info:
                        │  ├─ Classification: %s
                        │  └─ Homeworld: %s
                        ├─ Physical Traits:
                        │  ├─ Average Height: %s cm
                        │  ├─ Skin Colors: %s
                        │  ├─ Hair Colors: %s
                        │  └─ Eye Colors: %s
                        ├─ Biology:
                        │  ├─ Average Lifespan: %s years
                        │  └─ Language: %s
                        ├─ Associations:
                        │  ├─ People: %d individual(s)
                        │  └─ Films: %d appearance(s)
                        └─ Last Updated: %s
                        """,
                formatString(name),
                formatString(classification),
                formatString(homeWorld),
                formatInteger(averageHeight),
                formatStringList(skinColors),
                formatStringList(hairColors),
                formatStringList(eyeColors),
                formatInteger(averageLifespan),
                formatString(language),
                getListSize(people),
                getListSize(films),
                getEdited()
        );
    }
}
