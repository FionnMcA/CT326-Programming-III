import org.joda.money.Money;

/**
 * This class handles both deposits and withdrawals.
 * @author Fionn McArdle
 * @studentID 21446484
 */
public class TransactionProcessor extends Thread {
    private final Bank bank;
    private int depositTally = 0;
    private int withdrawalTally = 0;
    private final String name;

    /**
     * Constructs a TransactionProcessor with a specified name and associated bank.
     *
     * @param name The name of the transaction processor.
     * @param bank The bank associated with this transaction processor.
     */
    public TransactionProcessor(String name, Bank bank) {
        this.name = name;
        this.bank = bank;
    }

    /**
     * Continuously processes transactions from the bank's transaction queue.
     * Transactions include both deposits and withdrawals.
     */
    public void run() {
        while (true) {
            try {
                Transaction transaction = bank.getNextTransaction(); //get the transaction
                if (transaction != null) {
                    Account account = bank.getAccount(transaction.getAccountNumber()); //get the account Number associated with the transaction
                    Money transactionAmount = transaction.getAmount();

                        if (transactionAmount.isPositive()) { //if the transaction is a positive number it must be a deposit
                            account.makeDeposit(transactionAmount); //make the deposit
                            depositTally++; //increment deposit tally
                            System.out.println(name + " is processing a deposit of " + transactionAmount.getAmount() + " from " + transaction.getAccountNumber()); //print info of the processing statement

                        } else { //if the amount isn't positive it is negative so it's a withdrawal
                            Money withdrawal = transactionAmount.negated();
                            account.makeWithdrawal(withdrawal);
                            withdrawalTally++;
                            System.out.println(name + " is processing a withdrawal of " + transactionAmount.getAmount() + " from " + transaction.getAccountNumber());

                    }
                }
                Thread.sleep((int) (Math.random() * 1000));//sleeps for a random amount of time between 0 and 1 seconds
            } catch (InterruptedException e) {
                break; // Exit the loop on interruption
            } catch (InsufficientFundsException e) {
                // Handle the insufficient funds scenario
            }
        }
        // Calculate the total number of transactions processed
        int transactionsTally = depositTally + withdrawalTally;
        System.out.println(name + " finished processing " + transactionsTally + " transactions, including " + depositTally + " deposits, and " + withdrawalTally + " withdrawals processed");
    }
}
