package com.smallworld;

import com.smallworld.data.Transaction;
import com.smallworld.service.TransactionService;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class TransactionDataFetcher {

    private final TransactionService transactionService;

    /**
     * Returns the sum of the amounts of all transactions
     */

    public double getTotalTransactionAmount() {
        List<Transaction> transactions = transactionService.getAllTransaction();
        return transactions.isEmpty() ? 0.0 : transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        List<Transaction> transactions = transactionService.getAllTransaction();
        return transactions.isEmpty() ? 0.0 : transactions.stream()
                .filter(transaction -> transaction.getSenderFullName().equals(senderFullName))
                .mapToDouble(Transaction::getAmount).sum();
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {
        List<Transaction> transactions = transactionService.getAllTransaction();
        return transactions.isEmpty() ? 0.0 : transactions.stream()
                .max(Comparator.comparingDouble(Transaction::getAmount))
                .get().getAmount();
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {
        List<Transaction> transactions = transactionService.getAllTransaction();
        return transactions.isEmpty() ? 0 : transactions.stream()
                .flatMap(t -> Stream.of(t.getSenderFullName(), t.getBeneficiaryFullName()))
                .distinct().count();
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        List<Transaction> transactions = transactionService.getAllTransaction();
        return transactions.isEmpty() ? Boolean.FALSE : transactions.stream()
                .anyMatch(t -> (t.getSenderFullName().equals(clientFullName) ||
                        t.getBeneficiaryFullName().equals(clientFullName)) &&
                        t.getIssueSolved().equals(Boolean.FALSE));
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, List<Transaction>> getTransactionsByBeneficiaryName() {
        List<Transaction> transactions = transactionService.getAllTransaction();
        return transactions.isEmpty() ? Collections.emptyMap() :
                transactions.stream().collect(Collectors.groupingBy(Transaction::getBeneficiaryFullName));
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {
        List<Transaction> transactions = transactionService.getAllTransaction();
        return transactions.isEmpty() ? Collections.emptySet() :
                transactions.stream()
                        .filter(transaction -> transaction.getIssueSolved().equals(Boolean.FALSE))
                        .map(Transaction::getIssueId).collect(Collectors.toSet());
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the 3 transactions with the highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the senderFullName of the sender with the most total sent amount
     */
    public Optional<String> getTopSender() {
        throw new UnsupportedOperationException();
    }

}
