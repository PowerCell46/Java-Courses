import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> peopleAges = new HashMap<>();
        peopleAges.put("Peter", 22);
        peopleAges.put("Stiliyan", 22);
        peopleAges.put("Ivan", 18);

        System.out.println(peopleAges.containsValue(22));
    }
}