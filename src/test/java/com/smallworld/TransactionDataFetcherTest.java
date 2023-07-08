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
import org.unitils.reflectionassert.ReflectionAssert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        Assertions.assertEquals(745.86, transactionDataFetcher.getTotalTransactionAmount());
    }


    /**
     * Unit Test to test getAllTransactions while transaction list in empty.
     */
    @Test
    void getTotalTransactionAmountWhenTransactionNotExist() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getEmptyTransactions());
        Assertions.assertEquals(0.0, transactionDataFetcher.getTotalTransactionAmount());
    }


    /**
     * Unit test to test getTotalTransactionAmountSentBy where transaction against the user exist.
     */
    @Test
    void getTotalTransactionAmountSentByWhenTransactionExist() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getTransactions());
        Assertions.assertEquals(678.06, transactionDataFetcher.getTotalTransactionAmountSentBy("Tom Shelby"));
    }


    /**
     * Unit test to test getTotalTransactionAmountSentBy where transaction doesn't exist against such sender.
     */
    @Test
    void getTotalTransactionAmountSentByWhenNoTransactionExistAgainstSender() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getTransactions());
        double totalTransactionAmountSentBy = transactionDataFetcher.getTotalTransactionAmountSentBy("Alfie Solomons");
        Assertions.assertEquals(0.0, totalTransactionAmountSentBy);
    }


    /**
     * Unit test to test getTotalTransactionAmountSentBy where transaction is empty.
     */
    @Test
    void getTotalTransactionAmountSentByWhenTransactionNotExist() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getEmptyTransactions());
        double totalTransactionAmountSentBy = transactionDataFetcher.getTotalTransactionAmountSentBy("Tom Shelby");
        Assertions.assertEquals(0.0, totalTransactionAmountSentBy);
    }


    /**
     * Unit test to test getMaxTransactionAmount when transaction list exits.
     */
    @Test
    void getMaxTransactionAmountWhenTransactionExist() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getTransactions());
        Assertions.assertEquals(430.2, transactionDataFetcher.getMaxTransactionAmount());
    }


    /**
     * Unit test to test getMaxTransactionAmount when transaction list is empty.
     */
    @Test
    void getMaxTransactionAmountWhenTransactionNotExist() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getEmptyTransactions());
        Assertions.assertEquals(0.0, transactionDataFetcher.getMaxTransactionAmount());
    }


    /**
     * Unit test to test countUniqueClients when transaction list exits.
     */
    @Test
    void countUniqueClientsWhenTransactionExist() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getTransactions());
        Assertions.assertEquals(6, transactionDataFetcher.countUniqueClients());
    }


    /**
     * Unit test to test countUniqueClients when transaction list is empty.
     */
    @Test
    void countUniqueClientsWhenTransactionNotExist() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getEmptyTransactions());
        Assertions.assertEquals(0, transactionDataFetcher.countUniqueClients());
    }


    /**
     * Unit test to test hasOpenComplianceIssues when user has Compliance Issues which isn't resolved.
     */
    @Test
    void hasOpenComplianceIssuesWhenClientHasUnresolvedIssues() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getTransactions());
        Assertions.assertTrue(transactionDataFetcher.hasOpenComplianceIssues("Tom Shelby"));
    }


    /**
     * Unit test to test hasOpenComplianceIssues when user has Compliance Issues which isn't resolved.
     */
    @Test
    void hasOpenComplianceIssuesWhenClientHasNoUnresolvedIssues() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getTransactions());
        Assertions.assertFalse(transactionDataFetcher.hasOpenComplianceIssues("Ben Younger"));
    }


    /**
     * Unit test to test hasOpenComplianceIssues when transaction list is empty.
     */
    @Test
    void hasOpenComplianceIssuesWhenTransactionNotExist() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getEmptyTransactions());
        Assertions.assertFalse(transactionDataFetcher.hasOpenComplianceIssues("Tom Shelby"));
    }


    /**
     * Unit test to test getTransactionsByBeneficiaryName when transaction list exits.
     */
    @Test
    void getTransactionsByBeneficiaryNameWhenTransactionExist() {
        List<Transaction> transactions = getTransactions();
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getTransactions());
        Map<String, List<Transaction>> actualResult = transactionDataFetcher.getTransactionsByBeneficiaryName();

        Map<String, List<Transaction>> expectedResult = new HashMap<>();

        expectedResult.put("Alfie Solomons", new ArrayList<>(List.of(transactions.get(0))));
        expectedResult.put("Arthur Shelby", new ArrayList<>(List.of(transactions.get(1))));
        expectedResult.put("Aberama Gold", new ArrayList<>(List.of(transactions.get(2))));
        expectedResult.put("Oswald Mosley", new ArrayList<>(List.of(transactions.get(3))));

        for (Map.Entry<String, List<Transaction>> entry : actualResult.entrySet()) {
            ReflectionAssert.assertReflectionEquals(entry.getValue(), expectedResult.get(entry.getKey()));
        }
    }


    /**
     * Unit test to test getTransactionsByBeneficiaryName when transaction list is empty.
     */
    @Test
    void getTransactionsByBeneficiaryNameWhenTransactionNotExist() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getEmptyTransactions());
        Assertions.assertEquals(Collections.emptyMap(), transactionDataFetcher.getTransactionsByBeneficiaryName());
    }


    @Test
    void getUnsolvedIssueIdsWhenTransactionExist() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getTransactions());
        Set<Integer> actualResult = transactionDataFetcher.getUnsolvedIssueIds();
        Set<Integer> expectedResult = new HashSet<>(List.of(1));
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetUnsolvedIssueIdsWhenTransactionNotExist() {
        Mockito.when(transactionService.getAllTransaction()).thenReturn(getEmptyTransactions());
        Set<Integer> actualIssueIds = transactionDataFetcher.getUnsolvedIssueIds();
        Set<Integer> expectedIssueIds = new HashSet<>();
        Assertions.assertEquals(expectedIssueIds, actualIssueIds);
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
        transactions.add(
                Transaction.builder()
                        .mtn(1284564)
                        .amount(150.2)
                        .senderFullName("Tom Shelby")
                        .senderAge(22)
                        .beneficiaryFullName("Arthur Shelby")
                        .beneficiaryAge(60)
                        .issueId(2)
                        .issueSolved(true)
                        .issueMessage("Never gonna give you up").build()
        );
        transactions.add(
                Transaction.builder()
                        .mtn(96132456)
                        .amount(67.8)
                        .senderFullName("Aunt Polly")
                        .senderAge(34)
                        .beneficiaryFullName("Aberama Gold")
                        .beneficiaryAge(58)
                        .issueId(null)
                        .issueSolved(true)
                        .issueMessage(null).build()
        );
        transactions.add(
                Transaction.builder()
                        .mtn(1651665)
                        .amount(97.66)
                        .senderFullName("Tom Shelby")
                        .senderAge(22)
                        .beneficiaryFullName("Oswald Mosley")
                        .beneficiaryAge(37)
                        .issueId(65)
                        .issueSolved(true)
                        .issueMessage("Never gonna let you down").build()
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