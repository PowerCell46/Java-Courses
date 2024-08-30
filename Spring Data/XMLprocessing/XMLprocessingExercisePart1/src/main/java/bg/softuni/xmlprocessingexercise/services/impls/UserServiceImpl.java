package bg.softuni.xmlprocessingexercise.services.impls;

import bg.softuni.xmlprocessingexercise.DTOs.querying.*;
import bg.softuni.xmlprocessingexercise.DTOs.seeding.UserRootSeedDTO;
import bg.softuni.xmlprocessingexercise.DTOs.seeding.UserSeedDTO;
import bg.softuni.xmlprocessingexercise.entities.Product;
import bg.softuni.xmlprocessingexercise.entities.User;
import bg.softuni.xmlprocessingexercise.repositories.UserRepository;
import bg.softuni.xmlprocessingexercise.services.interfaces.UserService;
import bg.softuni.xmlprocessingexercise.utils.ValidationUtil;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private static final String FILE_PATH = "src/main/resources/static/XML/users.xml";

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Override
    public void seedUsers() throws JAXBException {
        if (userRepository.count() > 0) return;

        JAXBContext jaxbContext = JAXBContext.newInstance(UserRootSeedDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        UserRootSeedDTO userRootSeedDTO = (UserRootSeedDTO) unmarshaller.unmarshal(new File(FILE_PATH));

        for (UserSeedDTO userSeedDTO : userRootSeedDTO.getUsers()) {
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
    public boolean exportToXMLUsersWithAtLeastOneSellingItemWithBuyer() {
       String EXPORT_RESULT_XML_FILE_PATH =
        "src/main/resources/static/resultXML/usersWithAtLeastOneSellingItemWithBuyer.xml";

        Set<User> usersWithSellingItems = userRepository
            .findAllBySoldProductsIsNotEmptyOrderByFirstNameAscLastNameAsc();

        try {
            UserRootQueryDTO userRootQueryDTO = new UserRootQueryDTO(
                usersWithSellingItems.stream()
                    .filter(user ->
                        !user.getSoldProducts().stream()
                            .filter(product -> product.getBuyer() != null)
                            .toList()
                            .isEmpty()
                    )
                    .map(user -> mapUserToUserQueryDTO(user))
                    .toList()
            );

            JAXBContext jaxbContext = JAXBContext.newInstance(UserRootQueryDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(userRootQueryDTO, new File(EXPORT_RESULT_XML_FILE_PATH));

            System.out.println("Successful operation!");
            return true;

         } catch (JAXBException e) {
            System.out.println("An Error occurred while Writing Or Parsing the Data!");
            return false;
        }
    }

    @Override
    public boolean exportToXMLUsersSellingItemsOrderedByNumOfProductsSoldDescLastNameAsc() {
        String EXPORT_RESULT_XML_FILE_PATH =
            "src/main/resources/static/resultXML/usersWithSoldProducts.xml";

        Set<User> users = userRepository
                .findAllBySoldProductsIsNotEmptyOrderBySoldProductsDescLastNameAsc();

        try {
            UserSecondRootQueryDTO userNextQueryDTO = new UserSecondRootQueryDTO(users
                .stream()
                .map(user ->
                    mapUserEntityToUserSecondQueryDTO(user)).toList()
            );

            JAXBContext jaxbContext = JAXBContext.newInstance(UserSecondRootQueryDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(userNextQueryDTO, new File(EXPORT_RESULT_XML_FILE_PATH));

            System.out.println("Successful operation!");
            return true;

         } catch (JAXBException e) {
            System.out.println("An Error occurred while Writing Or parsing the Data!");
            return false;
        }
    }

    private static UserQueryDTO mapUserToUserQueryDTO(User user) {
        return UserQueryDTO.builder()
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .soldProducts(
                new UserRootSoldProductQueryDTO(
                    mapProductsToUserSoldProductsQueryDTO(user.getSoldProducts())
                )
            )
            .build();
    }

    private static List<UserSoldProductQueryDTO> mapProductsToUserSoldProductsQueryDTO(Set<Product> soldProducts) {
        List<UserSoldProductQueryDTO> soldProductQueryDTOS = new ArrayList<>();
        for (Product soldProduct : soldProducts) {
            if (soldProduct.getBuyer() == null) continue; // if the product doesn't have a buyer we skip it

            soldProductQueryDTOS.add(
                UserSoldProductQueryDTO.builder()
                    .name(soldProduct.getName())
                    .price(soldProduct.getPrice())
                    .buyerFirstName(soldProduct.getBuyer().getFirstName())
                    .buyerLastName(soldProduct.getBuyer().getLastName())
                    .build());
        }

        return soldProductQueryDTOS;
    }

    private static UserSecondQueryDTO mapUserEntityToUserSecondQueryDTO(User user) {
        UserSecondSoldProductsQueryDTO soldProductsDTO = UserSecondSoldProductsQueryDTO.builder()
                .count(user.getSoldProducts().size())
                .products(mapProductsToUserSecondSoldProductQueryDTOs(user.getSoldProducts()))
                .build();

        return UserSecondQueryDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .soldProducts(soldProductsDTO)
                .build();
    }

    private static List<UserSecondSoldProductQueryDTO> mapProductsToUserSecondSoldProductQueryDTOs(Set<Product> products) {
        List<UserSecondSoldProductQueryDTO> productNextQueryDTOS = new ArrayList<>();
        for (Product product : products) {
            productNextQueryDTOS
                .add(UserSecondSoldProductQueryDTO.builder()
                    .name(product.getName())
                    .price(product.getPrice())
                    .build()
                );
        }
        return productNextQueryDTOS;
    }
}
