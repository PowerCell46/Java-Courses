package bg.softuni.jsonprocessingexercise.services.impls;

import bg.softuni.jsonprocessingexercise.DTOs.SoldProductQueryDTO;
import bg.softuni.jsonprocessingexercise.DTOs.UserQueryDTO;
import bg.softuni.jsonprocessingexercise.DTOs.UserSeedDTO;
import bg.softuni.jsonprocessingexercise.entities.Product;
import bg.softuni.jsonprocessingexercise.entities.User;
import bg.softuni.jsonprocessingexercise.repositories.UserRepository;
import bg.softuni.jsonprocessingexercise.services.interfaces.UserService;
import bg.softuni.jsonprocessingexercise.utils.ValidationUtil;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private static final String FILE_PATH = "src/main/resources/static/JSON/users.json";

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Override
    public void seedUsers() throws IOException {
        if (userRepository.count() > 0) return;;

        String jsonData = new String(Files.readAllBytes(Path.of(FILE_PATH)));

        UserSeedDTO[] userSeedDTOS = gson.fromJson(jsonData, UserSeedDTO[].class);

        for (UserSeedDTO userSeedDTO : userSeedDTOS) {
            if (validationUtil.isValid(userSeedDTO)) {
                User currentUser = modelMapper.map(userSeedDTO, User.class);
                userRepository.saveAndFlush(currentUser);

            } else {
                validationUtil
                    .getViolations(userSeedDTO)
                    .forEach(err -> System.out.println(err.getMessage()));
            }
        }
    }

    @Override
    public long getLastPossibleId() {
        return this.userRepository.count();
    }

    @Override
    public User findById(long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean exportToJSONUsersWithAtLeastOneSellingItemWithBuyer() {
           String EXPORT_RESULT_JSON_FILE_PATH =
            "src/main/resources/static/resultJSON/usersWithAtLeastOneSellingItemWithBuyer.json";

        Set<User> usersWithSellingItems = userRepository
                .findAllBySoldProductsIsNotEmptyOrderByFirstNameAscLastNameAsc();

        try (FileWriter writer = new FileWriter(EXPORT_RESULT_JSON_FILE_PATH)) {
            List<String> jsonObjectsResult = new ArrayList<>();

            for (User currentUser : usersWithSellingItems) {
                List<Product> productsWithByers = currentUser
                    .getSoldProducts()
                    .stream()
                    .filter(product -> product.getBuyer() != null)
                    .collect(Collectors.toList());
                if (productsWithByers.isEmpty()) continue; // if none of the products has a buyer

                UserQueryDTO userQueryDTO = mapUserToUserQueryDTO(currentUser);
                String json = gson.toJson(userQueryDTO);
                jsonObjectsResult.add(json);
            }

            writer.write(String.valueOf(jsonObjectsResult));
            writer.write(System.lineSeparator());

            System.out.println("Successful operation!");
            return true;

         } catch (IOException e) {
            System.out.println("An Error occurred while Writing the Data!");
            return false;
        }
    }

    private static UserQueryDTO mapUserToUserQueryDTO(User user) {
        return UserQueryDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .soldProducts(mapProductsToSoldProductsQueryDTO(user.getSoldProducts()))
                .build();
    }

    private static Set<SoldProductQueryDTO> mapProductsToSoldProductsQueryDTO(Set<Product> soldProducts) {
        Set<SoldProductQueryDTO> soldProductQueryDTOS = new HashSet<>();
        for (Product soldProduct : soldProducts) {
            if (soldProduct.getBuyer() == null) continue;

            soldProductQueryDTOS.add(
                SoldProductQueryDTO.builder()
                    .name(soldProduct.getName())
                    .price(soldProduct.getPrice())
                    .buyerFirstName(soldProduct.getBuyer().getFirstName())
                    .buyerLastName(soldProduct.getBuyer().getLastName())
                    .build());
        }

        return soldProductQueryDTOS;
    }
}
