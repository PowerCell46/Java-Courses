import java.time.Instant;
import java.util.List;
import java.util.Map;


public final class Starship extends Vehicle {

    private String hyperdriveRating;

    private Integer MGLT;

    public Starship(
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
            String hyperdriveRating,
            Integer MGLT,
            String starshipClass,
            List<String> pilots,
            List<String> films,
            Instant created,
            Instant edited,
            String currentEntryUrl
    ) {
        super(name,
                model,
                manufacturer,
                costInCredits,
                length,
                maxAtmospheringSpeed,
                crew,
                passengers,
                cargoCapacity,
                consumables, starshipClass,
                pilots,
                films,
                created,
                edited,
                currentEntryUrl
        );
        this.hyperdriveRating = hyperdriveRating;
        this.MGLT = MGLT;
    }

    public static Starship initFromStringMap(Map<String, String> stringStringMap) {
        return new Starship(
                parseString(stringStringMap.get("name")),
                parseString(stringStringMap.get("model")),
                parseString(stringStringMap.get("manufacturer")),
                parseLong(stringStringMap.get("cost_in_credits")),
                parseDouble(stringStringMap.get("length")),
                parseInteger(stringStringMap.get("max_atmosphering_speed")),
                parseString(stringStringMap.get("crew")),
                parseInteger(stringStringMap.get("passengers")),
                parseLong(stringStringMap.get("cargoCapacity")),
                parseString(stringStringMap.get("consumables")),
                parseString(stringStringMap.get("hyperdrive_rating")),
                parseInteger(stringStringMap.get("MGLT")),
                parseString(stringStringMap.get("starship_class")),
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
                        Starship: %s
                        ├─ Basic Info:
                        │  ├─ Model: %s
                        │  ├─ Manufacturer: %s
                        │  └─ Class: %s
                        ├─ Performance:
                        │  ├─ Max Speed: %s km/h
                        │  ├─ Hyperdrive Rating: %s
                        │  └─ MGLT: %s
                        ├─ Specifications:
                        │  ├─ Length: %s m
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
                formatInteger(maxAtmospheringSpeed),
                formatString(hyperdriveRating),
                formatInteger(MGLT),
                formatDouble(length),
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
