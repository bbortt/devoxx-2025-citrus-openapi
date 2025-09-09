package io.github.bbortt.devoxx.banking.application.api;

import io.github.bbortt.devoxx.banking.application.api.dto.TransactionRequest;
import io.github.bbortt.devoxx.banking.application.api.dto.TransactionResponse;
import io.github.bbortt.devoxx.banking.application.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsResource implements TransactionsApi {

    private final TransactionService transactionService;

    public TransactionsResource(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public ResponseEntity<TransactionResponse> createTransaction(TransactionRequest transactionRequest) {
        try {
            var transactionId = transactionService.createAccountTransfer(
                    transactionRequest.getFromAccount(),
                    transactionRequest.getToAccount(),
                    transactionRequest.getAmount(),
                    transactionRequest.getCurrency()
            );

            return ResponseEntity.ok(new TransactionResponse().transactionId(transactionId));
        } catch (TransactionService.AccountNotFoundException e) {
            return ResponseEntity.badRequest().build();
        } catch (TransactionService.InsufficientFundsException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
}
