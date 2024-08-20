package bg.softuni.springdataintro.services.impl;

import bg.softuni.springdataintro.data.entities.Account;
import bg.softuni.springdataintro.data.repositories.AccountRepository;
import bg.softuni.springdataintro.services.interfaces.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public boolean withdrawMoney(BigDecimal amount, Long id) {
        Optional<Account> account = accountRepository.findById(id);

        if (account.isPresent()) {
            Account acc = account.get();
            if (acc.getBalance().compareTo(amount) >= 0) {
                acc.setBalance(acc.getBalance().subtract(amount));
                accountRepository.save(acc);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean depositMoney(BigDecimal amount, Long id) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Invalid deposit amount!");
        Optional<Account> account = accountRepository.findById(id);

        if (account.isPresent()) {
            Account acc = account.get();
            acc.setBalance(acc.getBalance().add(amount));
            accountRepository.save(acc);
            return true;
        }
        return false;

    }
}
