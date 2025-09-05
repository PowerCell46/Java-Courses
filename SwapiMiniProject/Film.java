import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public final class Film extends Base {

    public static final String REQUEST_URL_ENDPOINT = "films";

    private String title;

    private Integer episodeId;

    private String openingCrawl;

    private String director;

    private String producer;

    private LocalDate releaseDate;

    private List<String> characterEndpoints;

    private List<String> planets;

    private List<String> starships;

    private List<String> vehicles;

    private List<String> species;

    public Film(
            String title,
            Integer episodeId,
            String openingCrawl,
            String director,
            String producer,
            LocalDate releaseDate,
            List<String> characterEndpoints,
            List<String> planets,
            List<String> starships,
            List<String> vehicles,
            List<String> species,
            Instant created,
            Instant edited,
            String currentEntryUrl
    ) {
        super(created, edited, currentEntryUrl);
        this.title = title;
        this.episodeId = episodeId;
        this.openingCrawl = openingCrawl;
        this.director = director;
        this.producer = producer;
        this.releaseDate = releaseDate;
        this.characterEndpoints = characterEndpoints;
        this.planets = planets;
        this.starships = starships;
        this.vehicles = vehicles;
        this.species = species;
    }

    public static Film initFromStringMap(Map<String, String> stringStringMap) {
        return new Film(
                parseString(stringStringMap.get("title")),
                parseInteger(stringStringMap.get("episode_id")),
                parseString(stringStringMap.get("opening_crawl")),
                parseString(stringStringMap.get("director")),
                parseString(stringStringMap.get("producer")),
                parseLocalDate(stringStringMap.get("release_date")),
                parseStringList(stringStringMap.get("characters")),
                parseStringList(stringStringMap.get("planets")),
                parseStringList(stringStringMap.get("starships")),
                parseStringList(stringStringMap.get("vehicles")),
                parseStringList(stringStringMap.get("species")),
                Instant.parse(stringStringMap.get("created")),
                Instant.parse(stringStringMap.get("edited")),
                parseString(stringStringMap.get("url"))
        );
    }

    @Override
    public String toString() {
        return String.format("""
                        Film: %s (Episode %s)
                        ├─ Production:
                        │  ├─ Director: %s
                        │  ├─ Producer: %s
                        │  └─ Release Date: %s
                        ├─ Story:
                        │  └─ Opening: %s
                        ├─ Galaxy Content:
                        │  ├─ Characters: %d endpoint(s)
                        │  ├─ Planets: %d location(s)
                        │  ├─ Starships: %d vessel(s)
                        │  ├─ Vehicles: %d vehicle(s)
                        │  └─ Species: %d species
                        └─ Last Updated: %s
                        """,
                formatString(title),
                formatInteger(episodeId),
                formatString(director),
                formatString(producer),
                formatLocalDate(releaseDate),
                formatString(openingCrawl),
                getListSize(characterEndpoints),
                getListSize(planets),
                getListSize(starships),
                getListSize(vehicles),
                getListSize(species),
                getEdited()
        );
    }
}
