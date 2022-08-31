package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.ParseService;
import java.util.List;
import java.util.stream.Collectors;

public class ParserServiceImpl implements ParseService {
    private static final int INDEX_OF_OPERATION = 0;
    private static final int INDEX_OF_FRUIT = 1;
    private static final int INDEX_OF_QUANTITY = 2;
    private static final String SEPARATOR = ",";

    @Override
    public List<Transaction> parse(List<String> lines) {
        return lines.stream()
                .skip(1)
                .map(this::getTransaction)
                .collect(Collectors.toList());
    }

    private Transaction getTransaction(String line) {
        String[] fields = line.split(SEPARATOR);
        return new Transaction(fields[INDEX_OF_OPERATION],
                new Fruit(fields[INDEX_OF_FRUIT]),
                Integer.parseInt(fields[INDEX_OF_QUANTITY]));
    }
}