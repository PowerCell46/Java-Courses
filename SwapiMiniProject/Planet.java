import java.time.Instant;
import java.util.List;
import java.util.Map;


public final class Planet extends Base {

    public static final String REQUEST_URL_ENDPOINT = "planets";

    private String name;

    private Integer rotationPeriod;

    private Integer orbitalPeriod;

    private Integer diameter;

    private String climate;

    private String gravity;

    private String terrain;

    private Integer surfaceWater;

    private Long population;

    private List<String> residentEndpoints;

    private List<String> filmEndpoints;

    public Planet(
            String name,
            Integer rotationPeriod,
            Integer orbitalPeriod,
            Integer diameter,
            String climate,
            String gravity,
            String terrain,
            Integer surfaceWater,
            Long population,
            List<String> residentEndpoints,
            List<String> filmEndpoints,
            Instant created,
            Instant edited,
            String currentEntryUrl
    ) {
        super(created, edited, currentEntryUrl);
        this.name = name;
        this.rotationPeriod = rotationPeriod;
        this.orbitalPeriod = orbitalPeriod;
        this.diameter = diameter;
        this.climate = climate;
        this.gravity = gravity;
        this.terrain = terrain;
        this.surfaceWater = surfaceWater;
        this.population = population;
        this.residentEndpoints = residentEndpoints;
        this.filmEndpoints = filmEndpoints;
    }

    public static Planet initFromStringMap(Map<String, String> stringStringMap) {
        return new Planet(
                parseString(stringStringMap.get("name")),
                parseInteger(stringStringMap.get("rotation_period")),
                parseInteger(stringStringMap.get("orbital_period")),
                parseInteger(stringStringMap.get("diameter")),
                parseString(stringStringMap.get("climate")),
                parseString(stringStringMap.get("gravity")),
                parseString(stringStringMap.get("terrain")),
                parseInteger(stringStringMap.get("surface_water")),
                parseLong(stringStringMap.get("population")),
                parseStringList(stringStringMap.get("residents")),
                parseStringList(stringStringMap.get("films")),
                Instant.parse(stringStringMap.get("created")),
                Instant.parse(stringStringMap.get("edited")),
                parseString(stringStringMap.get("url"))
        );
    }

    @Override
    public String toString() {
        return String.format("""
                        Planet: %s
                        ├─ Physical Properties:
                        │  ├─ Diameter: %s km
                        │  ├─ Rotation Period: %s hours
                        │  └─ Orbital Period: %s days
                        ├─ Environment:
                        │  ├─ Climate: %s
                        │  ├─ Gravity: %s
                        │  ├─ Terrain: %s
                        │  └─ Surface Water: %s%%
                        ├─ Population: %s
                        ├─ Residents: %d endpoint(s)
                        ├─ Films: %d appearance(s)
                        └─ Last Updated: %s
                        """,
                formatString(name),
                formatInteger(diameter),
                formatInteger(rotationPeriod),
                formatInteger(orbitalPeriod),
                formatString(climate),
                formatString(gravity),
                formatString(terrain),
                formatInteger(surfaceWater),
                formatLong(population),
                getListSize(residentEndpoints),
                getListSize(filmEndpoints),
                getEdited()
        );
    }
}
