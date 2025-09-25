package io.github.bbortt.devoxx.banking.client.service;

import io.github.bbortt.devoxx.banking.client.api.AccountsApi;
import io.github.bbortt.devoxx.banking.client.api.dto.Account;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.lang.System.exit;

@Service
public class AccountService {

    private final AccountsApi accountsApi;

    public AccountService(AccountsApi accountsApi) {
        this.accountsApi = accountsApi;
    }

    public void fetchAndPrintAccountDetails(String[] args) {
        if (args.length != 1) {
            printHowToFetchAccountInformation();
        }

        var accountDetails = fetchAccountDetails(args[0]);
        System.out.printf("Account-Details: %s%n", accountDetails);
    }

    Account fetchAccountDetails(String accountId) {
        return accountsApi.getAccountDetails(accountId, UUID.randomUUID());
    }

    private void printHowToFetchAccountInformation() {
        System.err.println("Invalid command usage.");
        System.err.println("Fetching account details:");
        System.err.println("Usage: java -jar banking-client.jar fetch-account <account-id>");
        exit(2);
    }
}
