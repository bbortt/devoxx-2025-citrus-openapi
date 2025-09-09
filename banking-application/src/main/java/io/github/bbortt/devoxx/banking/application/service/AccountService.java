package io.github.bbortt.devoxx.banking.application.service;

import io.github.bbortt.devoxx.banking.application.domain.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.util.Arrays.stream;

@Service
public class AccountService {

    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private static Map<String, Account> accountsTable = new HashMap<>();

    public AccountService() {
        initializeTable();
    }

    private void initializeTable() {
        var accountId1 = "CH685984389182Q70Y469";
        accountsTable.put(
                accountId1,
                new Account(accountId1, "Timon Borter", BigDecimal.valueOf(1234.56), "CHF")
        );

        var accountId2 = "CH077012473296D5049K3";
        accountsTable.put(
                accountId2,
                new Account(accountId2, "Thorsten Schlathoelter", BigDecimal.valueOf(9876.54), "EUR")
        );
    }

    public Optional<Account> findAccountById(String accountId) {
        try {
            lock.readLock().lock();
            return Optional.ofNullable(accountsTable.get(accountId));
        } finally {
            lock.readLock().unlock();
        }
    }

    public void updateTransactional(Account... accounts) {
        try {
            lock.writeLock().lock();

            replaceAccountTableWithUpdatedAccounts(accounts);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private static void replaceAccountTableWithUpdatedAccounts(Account... accounts) {
        var updatedAccounts = new HashMap<>(accountsTable);
        stream(accounts).forEach(account -> updatedAccounts.put(account.getAccountId(), account));

        accountsTable = updatedAccounts;
    }
}
