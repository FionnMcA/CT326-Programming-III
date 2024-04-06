import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A bank class that stores the banks accounts in HashMap
 * @author Fionn McArdle
 * @studentID 21446484
 */
public class Bank {
    private Map<Integer, Account> accounts;
    private LinkedBlockingQueue<Transaction> transactionQueue;

    /**
     * Constructs a new Bank instance.
     * Initializes the accounts Hashmap and transaction queue.
     */
    public Bank() {
        accounts = new HashMap<>();
        transactionQueue = new LinkedBlockingQueue<>();
    }

    /**
     * Adds a new account to the accounts hashmap
     * using the accounts AccountNumber as the index
     *
     * @param account The account to be added.
     */

    public synchronized void addAccount(Account account){
        accounts.put(account.getAccountNumber(), account);
    }

    /**
     * Gets an account by its account number.
     *
     * @param accountNumber The account number.
     * @return The account that belongs to the provided accountNumber.
     */

    public synchronized Account getAccount(int accountNumber){
        return accounts.get(accountNumber);
    }

    /**
     * Submits a transaction to the bank's transaction queue.
     *
     * @param transaction The transaction to be submitted.
     */
    public void submitTransaction(Transaction transaction) {
        transactionQueue.offer(transaction); // Add transaction to the queue
    }

    /**
     * Retrieves and removes the next transaction from the transaction queue.
     *
     * @return The next transaction in the queue.
     * @throws InterruptedException If interrupted while waiting.
     */
    public Transaction getNextTransaction() throws InterruptedException {
        return transactionQueue.take();
    }

    /**
     * Prints accounts toString
     */
    public synchronized void printAccountDetails() {
        for (Map.Entry<Integer, Account> entry : accounts.entrySet()) {
            Integer accountNumber = entry.getKey();
            Account account = entry.getValue();
            System.out.println(account);
        }
    }

    /**
     * Retrieves a set of all account numbers in the bank.
     *
     * @return The set of account numbers.
     */
    public synchronized Set<Integer> getAccountNumbers() {
        return accounts.keySet();
    }
}
