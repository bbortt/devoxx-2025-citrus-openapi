package io.github.bbortt.devoxx.banking.application.api;

import io.github.bbortt.devoxx.banking.application.api.dto.Account;
import io.github.bbortt.devoxx.banking.application.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

@RestController
public class AccountsResource implements AccountsApi {

    private final AccountService accountService;

    public AccountsResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<Account> getAccountDetails(String accountId) {
        return accountService.findAccountById(accountId)
                .map(account -> ResponseEntity.ok(toDto(account)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Account toDto(io.github.bbortt.devoxx.banking.application.domain.Account account) {
        return Account.builder()
                .accountId(account.getAccountId())
                .owner(account.getOwner())
                .balance(
                        Optional.ofNullable(account.getBalance())
                                .map(balance -> balance.setScale(2, HALF_UP))
                                .orElse(ZERO)
                                .floatValue()
                )
                .currency(account.getCurrency())
                .build();
    }
}
