package bg.softuni.springdataintro;

import bg.softuni.springdataintro.services.interfaces.AccountService;
import bg.softuni.springdataintro.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@RequiredArgsConstructor
public class CommandLineEndpoint implements CommandLineRunner {
    private final UserService userService;
    private final AccountService accountService;

    @Override
    public void run(String... args) throws Exception {


    }
}
