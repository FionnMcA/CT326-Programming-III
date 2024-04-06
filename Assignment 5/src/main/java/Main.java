import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main class for running the bank transaction processing application.
 * This class sets up the bank, accounts, and threads for processing transactions.
 * @author Fionn McArdle
 * @studentID 21446484
 */
public class Main {
    /**
     * The main method to start the application.
     * It creates a bank, adds accounts, and starts threads for processing transactions.
     *
     * @param args The command line arguments.
     * @throws InterruptedException If the thread execution is interrupted.
     * @throws NegativeBalanceException If an operation causes a negative balance.
     */
    public static void main(String[] args) throws InterruptedException, NegativeBalanceException {
        // Initialize bank and accounts
        Bank bank = new Bank();
        bank.addAccount(new Account(1, Money.of(CurrencyUnit.EUR,420)));
        bank.addAccount(new Account(2,  Money.of(CurrencyUnit.EUR,999)));
        bank.addAccount(new Account(3,  Money.of(CurrencyUnit.EUR,7500)));

        // Set up an executor service with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Start transaction processors and a random transaction generator
        TransactionProcessor tp1 = new TransactionProcessor("TPT1", bank);
        TransactionProcessor tp2 = new TransactionProcessor("TPT2", bank);
        executor.execute(tp1);
        executor.execute(tp2);

        RandomTransactionGenerator rtg = new RandomTransactionGenerator(bank);
        executor.execute(rtg);

        // Wait 10 seconds before shutting down RandomTransactionGenerator
        Thread.sleep(10000);
        rtg.interrupt();

        // Shut down the executor service
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // Print out the details of the accounts
        bank.printAccountDetails();
    }
}
