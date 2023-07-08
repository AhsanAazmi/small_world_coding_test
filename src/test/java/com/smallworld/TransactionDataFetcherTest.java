package com.smallworld;

import com.smallworld.data.Transaction;
import com.smallworld.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionDataFetcherTest {
    @Mock
    TransactionService transactionService;
    @InjectMocks
    private TransactionDataFetcher transactionDataFetcher;


    /**
     * Unit Test to test getAllTransactions while transaction list in exit.
     */
    @Test
    void getTotalTransactionAmountWhenTransactionExist() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getTransactions());
        Assertions.assertEquals(430.2, transactionDataFetcher.getTotalTransactionAmount());
    }


    /**
     * Unit Test to test getAllTransactions while transaction list in empty.
     */
    @Test
    void getTotalTransactionAmountWhenTransactionDoNotExist() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getEmptyTransactions());
        Assertions.assertEquals(0.0, transactionDataFetcher.getTotalTransactionAmount());
    }

    /**
     * To Get Mock List;
     *
     * @return transactions
     */
    private List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(
                Transaction.builder()
                        .mtn(663458)
                        .amount(430.2)
                        .senderFullName("Tom Shelby")
                        .senderAge(22)
                        .beneficiaryFullName("Alfie Solomons")
                        .beneficiaryAge(33)
                        .issueId(1)
                        .issueSolved(false)
                        .issueMessage("Looks like money laundering").build()
        );
        return transactions;
    }

    /**
     * To Get Mock Empty List;
     *
     * @return transactions
     */
    private List<Transaction> getEmptyTransactions() {
        return Collections.emptyList();
    }
}