package io.github.bbortt.devoxx.banking.application.domain;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;

public class Account {

    private @Nonnull String accountId;
    private @Nonnull String owner;
    private @Nullable BigDecimal balance;
    private @Nonnull String currency;

    public Account(String accountId, String owner, BigDecimal balance, String currency) {
        this.accountId = accountId;
        this.owner = owner;
        this.balance = balance;
        this.currency = currency;
    }

    @Nonnull
    public String getAccountId() {
        return accountId;
    }

    @Nonnull
    public String getOwner() {
        return owner;
    }

    @Nullable
    public BigDecimal getBalance() {
        return balance;
    }

    @Nonnull
    public String getCurrency() {
        return currency;
    }

    public Account withBalance(BigDecimal newBalance) {
        return new Account(
                accountId,
                owner,
                newBalance,
                currency
        );
    }
}
