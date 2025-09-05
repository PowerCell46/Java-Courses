import java.time.Instant;
import java.util.List;
import java.util.Map;


public final class Person extends Base {

    public static final String REQUEST_URL_ENDPOINT = "people";

    private String name;

    private Integer height;

    private Integer mass;

    private String hairColor;

    private String skinColor;

    private String eyeColor;

    private String birthYear;

    private String gender;

    private String homeWorldUrl;

    private List<String> filmEndpoints;

    private List<String> speciesEndpoints;

    private List<String> vehicleEndpoints;

    private List<String> starshipEndpoints;

    public Person(
            String name,
            Integer height,
            Integer mass,
            String hairColor,
            String skinColor,
            String eyeColor,
            String birthYear,
            String gender,
            String homeWorldUrl,
            List<String> filmEndpoints,
            List<String> speciesEndpoints,
            List<String> vehicleEndpoints,
            List<String> starshipEndpoints,
            Instant created,
            Instant edited,
            String currentEntryUrl
    ) {
        super(created, edited, currentEntryUrl);
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.hairColor = hairColor;
        this.skinColor = skinColor;
        this.eyeColor = eyeColor;
        this.birthYear = birthYear;
        this.gender = gender;
        this.homeWorldUrl = homeWorldUrl;
        this.filmEndpoints = filmEndpoints;
        this.speciesEndpoints = speciesEndpoints;
        this.vehicleEndpoints = vehicleEndpoints;
        this.starshipEndpoints = starshipEndpoints;
    }

    public static Person initFromStringMap(Map<String, String> stringStringMap) {
        return new Person(
                parseString(stringStringMap.get("name")),
                parseInteger(stringStringMap.get("height")),
                parseInteger(stringStringMap.get("mass")),
                parseString(stringStringMap.get("hair_color")),
                parseString(stringStringMap.get("skin_color")),
                parseString(stringStringMap.get("eye_color")),
                parseString(stringStringMap.get("birth_year")),
                parseString(stringStringMap.get("gender")),
                parseString(stringStringMap.get("homeworld")),
                parseStringList(stringStringMap.get("films")),
                parseStringList(stringStringMap.get("species")),
                parseStringList(stringStringMap.get("vehicles")),
                parseStringList(stringStringMap.get("starships")),
                Instant.parse(stringStringMap.get("created")),
                Instant.parse(stringStringMap.get("edited")),
                parseString(stringStringMap.get("url"))
        );
    }

    @Override
    public String toString() {
        return String.format("""
                        Person: %s
                        ├─ Basic Info:
                        │  ├─ Gender: %s
                        │  ├─ Birth Year: %s
                        │  └─ HomeWorld: %s
                        ├─ Physical Traits:
                        │  ├─ Height: %s cm
                        │  ├─ Mass: %s kg
                        │  ├─ Hair Color: %s
                        │  ├─ Skin Color: %s
                        │  └─ Eye Color: %s
                        ├─ Associations:
                        │  ├─ Films: %d appearance(s)
                        │  ├─ Species: %d type(s)
                        │  ├─ Vehicles: %d vehicle(s)
                        │  └─ Starships: %d starship(s)
                        └─ Last Updated: %s
                        """,
                formatString(name),
                formatString(gender),
                formatString(birthYear),
                formatString(homeWorldUrl),
                formatInteger(height),
                formatInteger(mass),
                formatString(hairColor),
                formatString(skinColor),
                formatString(eyeColor),
                getListSize(filmEndpoints),
                getListSize(speciesEndpoints),
                getListSize(vehicleEndpoints),
                getListSize(starshipEndpoints),
                getEdited()
        );
    }
}
