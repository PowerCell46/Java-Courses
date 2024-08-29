package bg.softuni.xmlprocessingexercise.services.interfaces;


import bg.softuni.xmlprocessingexercise.entities.User;
import jakarta.xml.bind.JAXBException;

import java.io.IOException;

public interface UserService {

    void seedUsers() throws JAXBException;

    long getLastPossibleId();

    User findById(long id);

    boolean exportToXMLUsersWithAtLeastOneSellingItemWithBuyer();

    boolean exportToXMLUsersSellingItemsOrderedByNumOfProductsSoldDescLastNameAsc();
}
