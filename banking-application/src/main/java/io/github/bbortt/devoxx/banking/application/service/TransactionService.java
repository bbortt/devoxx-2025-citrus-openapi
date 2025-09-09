package io.github.bbortt.devoxx.banking.application.service;

import io.github.bbortt.devoxx.banking.application.domain.Account;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.lang.String.format;
import static java.math.RoundingMode.HALF_UP;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Service
public class TransactionService {

    private final AccountService accountService;

    public TransactionService(AccountService accountService) {
        this.accountService = accountService;
    }

    public String createAccountTransfer(@Nonnull String fromAccount, @Nonnull String toAccount, @Nonnull Float amount, @Nonnull String currency) throws AccountNotFoundException, InsufficientFundsException {
        var debitor = accountService.findAccountById(fromAccount)
                .orElseThrow(() -> new AccountNotFoundException(fromAccount));

        var exactAmount = BigDecimal.valueOf(amount);
        assertThatDebitorHasSufficientAmounts(fromAccount, exactAmount, currency, debitor);

        var creditor = accountService.findAccountById(toAccount)
                .orElseThrow(() -> new AccountNotFoundException(toAccount));

        debitor = debitor.withBalance(
                requireNonNull(debitor.getBalance())
                        .subtract(exactAmount)
        );

        creditor = creditor.withBalance(
                requireNonNull(creditor.getBalance())
                        .add(exactAmount)
        );

        accountService.updateTransactional(debitor, creditor);

        return "TX" + RandomStringUtils.secure().nextAlphabetic(9);
    }

    private static void assertThatDebitorHasSufficientAmounts(String fromAccount, BigDecimal amount, String currency, Account debitor) throws InsufficientFundsException {
        if (isNull(debitor.getBalance())
                || debitor.getBalance().compareTo(amount) <= 0) {
            throw new InsufficientFundsException(fromAccount, amount, currency);
        }
    }

    public static class AccountNotFoundException extends Throwable {

        public AccountNotFoundException(@Nonnull String accountId) {
            super(format("Account by ID '%s' not found", accountId));
        }
    }

    public static class InsufficientFundsException extends Throwable {

        public InsufficientFundsException(@Nonnull String accountId, @Nonnull BigDecimal amount, @Nonnull String currency) {
            super(format("Account ID '%s' has less than %s %s", accountId, amount.setScale(2, HALF_UP).toPlainString(), currency));
        }
    }
}
