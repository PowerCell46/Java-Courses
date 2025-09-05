import java.time.Instant;
import java.util.List;
import java.util.Map;


public sealed class Vehicle extends Base permits Starship {

    public static final String REQUEST_URL_ENDPOINT = "vehicles";

    protected String name;

    protected String model;

    protected String manufacturer;

    protected Long costInCredits;

    protected Double length;

    protected Integer maxAtmospheringSpeed;

    protected String crew;

    protected Integer passengers;

    protected Long cargoCapacity;

    protected String consumables;

    protected String vehicleClass;

    protected List<String> pilots;

    protected List<String> films;

    public Vehicle(
            String name,
            String model,
            String manufacturer,
            Long costInCredits,
            Double length,
            Integer maxAtmospheringSpeed,
            String crew,
            Integer passengers,
            Long cargoCapacity,
            String consumables,
            String vehicleClass,
            List<String> pilots,
            List<String> films,
            Instant created,
            Instant edited,
            String currentEntryUrl
    ) {
        super(created, edited, currentEntryUrl);
        this.name = name;
        this.model = model;
        this.manufacturer = manufacturer;
        this.costInCredits = costInCredits;
        this.length = length;
        this.maxAtmospheringSpeed = maxAtmospheringSpeed;
        this.crew = crew;
        this.passengers = passengers;
        this.cargoCapacity = cargoCapacity;
        this.consumables = consumables;
        this.vehicleClass = vehicleClass;
        this.pilots = pilots;
        this.films = films;
    }

    public static Vehicle initFromStringMap(Map<String, String> stringStringMap) {
        return new Vehicle(
                parseString(stringStringMap.get("name")),
                parseString(stringStringMap.get("model")),
                parseString(stringStringMap.get("manufacturer")),
                parseLong(stringStringMap.get("cost_in_credits")),
                parseDouble(stringStringMap.get("length").trim()),
                parseInteger(stringStringMap.get("max_atmosphering_speed")),
                parseString(stringStringMap.get("crew")),
                parseInteger(stringStringMap.get("passengers")),
                parseLong(stringStringMap.get("cargo_capacity")),
                parseString(stringStringMap.get("consumables")),
                parseString(stringStringMap.get("vehicle_class")),
                parseStringList(stringStringMap.get("pilots")),
                parseStringList(stringStringMap.get("films")),
                Instant.parse(stringStringMap.get("created")),
                Instant.parse(stringStringMap.get("edited")),
                parseString(stringStringMap.get("url"))
        );
    }

    @Override
    public String toString() {
        return String.format("""
                        Vehicle: %s
                        ├─ Basic Info:
                        │  ├─ Model: %s
                        │  ├─ Manufacturer: %s
                        │  └─ Class: %s
                        ├─ Specifications:
                        │  ├─ Length: %s m
                        │  ├─ Max Speed: %s km/h
                        │  ├─ Crew: %s
                        │  ├─ Passengers: %s
                        │  └─ Cargo Capacity: %s kg
                        ├─ Economics:
                        │  ├─ Cost: %s credits
                        │  └─ Consumables: %s
                        ├─ Associations:
                        │  ├─ Pilots: %d pilot(s)
                        │  └─ Films: %d appearance(s)
                        └─ Last Updated: %s
                        """,
                formatString(name),
                formatString(model),
                formatString(manufacturer),
                formatString(vehicleClass),
                formatDouble(length),
                formatInteger(maxAtmospheringSpeed),
                formatString(crew),
                formatInteger(passengers),
                formatLong(cargoCapacity),
                formatLong(costInCredits),
                formatString(consumables),
                getListSize(pilots),
                getListSize(films),
                getEdited()
        );
    }
}
