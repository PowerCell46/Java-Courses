package bg.softuni.springdataintro.services.interfaces;

import bg.softuni.springdataintro.data.entities.Account;

import java.math.BigDecimal;

public interface AccountService {

    Account addAccount(Account account);

    boolean withdrawMoney(BigDecimal amount, Long id);

    boolean depositMoney(BigDecimal amount, Long id);
}
