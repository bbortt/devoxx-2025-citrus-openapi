package io.github.bbortt.devoxx.banking.client.service;

import io.github.bbortt.devoxx.banking.client.api.TransactionsApi;
import io.github.bbortt.devoxx.banking.client.api.dto.TransactionRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.lang.Float.parseFloat;
import static java.lang.System.exit;

@Service
public class TransactionService {

    private final AccountService accountService;
    private final TransactionsApi transactionsApi;

    public TransactionService(AccountService accountService, TransactionsApi transactionsApi) {
        this.accountService = accountService;
        this.transactionsApi = transactionsApi;
    }

    public void createTransaction(String[] args) {
        if (args.length != 3) {
            printHowToCreateATransaction();
        }

        var fromAccountId = args[0];
        var sourceAccount = accountService.fetchAccountDetails(fromAccountId);

        var transaction = transactionsApi.createTransaction(
                UUID.randomUUID(),
                new TransactionRequest()
                        .fromAccount(fromAccountId)
                        .toAccount(args[1])
                        .amount(parseFloat(args[2]))
                        .currency(sourceAccount.getCurrency())
                        .description("Transaction generated from CLI.")
        );

        System.out.printf("Transaction '%s' successfully created\n", transaction.getTransactionId());
    }

    private void printHowToCreateATransaction() {
        System.err.println("Invalid command usage.");
        System.err.println("Creating a new transaction...");
        System.err.println("Usage: java -jar banking-client.jar create-transaction <from-account-id> <to-account-id> <amount>");
        exit(3);
    }
}
