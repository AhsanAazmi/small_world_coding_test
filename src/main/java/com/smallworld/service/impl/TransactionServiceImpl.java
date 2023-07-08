package com.smallworld.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.data.Transaction;
import com.smallworld.exception.ServiceException;
import com.smallworld.service.TransactionService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private static List<Transaction> transactions;
    @Value("${datasource.json.file-location}")
    private String jsonFile;


    /**
     * Loading json file once and storing it in static variable.
     */
    @PostConstruct
    void initTransaction() {
        log.info("initTransaction : Loading and Storing Transactions.");
        getAllTransactions();
    }


    /**
     * To get all transactions.
     *
     * @return transactions
     */
    @Override
    public List<Transaction> getAllTransaction() {
        return transactions;
    }


    /**
     * It will read the json file from resources and map it to static transactions list.
     */
    private void getAllTransactions() {
        log.info("getAllTransactions : Reading file and mapping it to transactions list.");
        ObjectMapper mapper = new ObjectMapper();
        try {
            File initialFile = new File(jsonFile);
            transactions = mapper.readValue(initialFile, new TypeReference<List<Transaction>>() {
            });
        } catch (Exception e) {
            throw new ServiceException("Loading Transaction Failed.");
        }
    }
}
