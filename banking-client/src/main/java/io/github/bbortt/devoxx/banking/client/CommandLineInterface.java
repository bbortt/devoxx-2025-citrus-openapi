package io.github.bbortt.devoxx.banking.client;

import io.github.bbortt.devoxx.banking.client.service.AccountService;
import io.github.bbortt.devoxx.banking.client.service.TransactionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static java.lang.System.exit;
import static java.util.Arrays.copyOfRange;

@Component
public class CommandLineInterface implements CommandLineRunner {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public CommandLineInterface(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Override
    public void run(String... args) {
        if (args.length < 1) {
            exitWithUsageHints();
        }

        var commandArguments = copyOfRange(args, 1, args.length);

        switch (args[0]) {
            case "fetch-account":
                accountService.fetchAndPrintAccountDetails(commandArguments);
                break;
            case "create-transaction":
                transactionService.createTransaction(commandArguments);
                break;
            default:
                exitWithUsageHints();
        }
    }

    private void exitWithUsageHints() {
        System.err.println("Invalid command usage.");
        System.err.println("Usage: java -jar banking-client.jar <command>");
        System.err.println("Available commands:");
        System.err.println("\tfetch-account: fetch specific account information");
        System.err.println("\tcreate-transaction: create a transaction from account to account");
        exit(1);
    }
}
