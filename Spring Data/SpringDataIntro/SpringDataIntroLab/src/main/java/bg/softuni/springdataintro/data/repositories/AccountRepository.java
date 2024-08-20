package bg.softuni.springdataintro.data.repositories;

import bg.softuni.springdataintro.data.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Set;


public interface AccountRepository extends JpaRepository<Account, Long> {

    Set<Account> findAllByBalanceGreaterThan(BigDecimal balance);
}
