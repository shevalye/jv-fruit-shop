package core.basesyntax;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.ReaderService;
import core.basesyntax.service.impl.ParserServiceImpl;
import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE_NAME = "src/main/resources/input.txt";
    private static final String OUTPUT_FILE_NAME = "src/main/resources/output.txt";

    public static void main(String[] args) {
        Map<String, OperationHandler> map = new HashMap<>();
        initializeMap(map);
        OperationStrategy strategy = new OperationStrategy(map);
        ReaderService readerService = new ReaderServiceImpl();
        List<String> lines = readerService.readFromFile(INPUT_FILE_NAME);
        List<Transaction> transactions = new ParserServiceImpl().parse(lines);
        for (Transaction transaction : transactions) {
            OperationHandler handler = strategy.getByOperation(transaction.getOperation());
            handler.apply(transaction);
        }
        String report = new ReportServiceImpl().getReport();
        new WriterServiceImpl().writeToFile(OUTPUT_FILE_NAME, report);
    }

    private static void initializeMap(Map<String, OperationHandler> map) {
        map.put("b", new BalanceOperationHandler());
        map.put("s", new SupplyOperationHandler());
        map.put("p", new PurchaseOperationHandler());
        map.put("r", new ReturnOperationHandler());
    }
}