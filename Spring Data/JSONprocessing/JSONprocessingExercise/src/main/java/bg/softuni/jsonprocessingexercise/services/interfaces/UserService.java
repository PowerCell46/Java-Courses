package bg.softuni.jsonprocessingexercise.services.interfaces;


import bg.softuni.jsonprocessingexercise.entities.User;

import java.io.IOException;

public interface UserService {

    void seedUsers() throws IOException;

    long getLastPossibleId();

    User findById(long id);

    boolean exportToJSONUsersWithAtLeastOneSellingItemWithBuyer();
}
