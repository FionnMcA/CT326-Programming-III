import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.Set;

/**
 * Generates random withdrawals and deposits and submits them for processing.
 * @author Fionn McArdle
 * @studentID 21446484
 */
public class RandomTransactionGenerator extends Thread {

    private final Bank bank;
    private final Random random = new Random();

    /**
     * Constructs a RandomTransactionGenerator for the specified bank.
     *
     * @param bank The bank associated with this transaction processor.
     */
    public RandomTransactionGenerator(Bank bank) {
        this.bank = bank;
    }

    /**
     * Continuously generates and submits both deposits and withdrawals to the bank.
     */
    @Override
    public void run() {
        while (true) {
            int accountNumber = getRandomAccountNumber(); //Get a random account number

            Money randomDepositOrWithdrawal = createRandomDepositOrWithdrawal(); //create a random deposit or withdrawal
            Transaction transaction = new Transaction(accountNumber, randomDepositOrWithdrawal); //create a new transaction with the random account and random deposit or withdrawal
            bank.submitTransaction(transaction); //submit the transaction to the bank

            try {
                Thread.sleep((int) (Math.random() * 1000)); //sleeps for a random amount of time between 0 and 1 seconds
            } catch (InterruptedException e) {
                // Insert a poison pill transaction to indicate closure
                Transaction poisonPill = new Transaction(-1, Money.zero(CurrencyUnit.EUR));
                bank.submitTransaction(poisonPill);
                break;
            }
        }
    }
    /**
     * Selects a random account number from the bank's list of accounts.
     *
     * @return A randomly selected account number.
     * @throws IllegalStateException If unable to randomly select an account.
     */
    private int getRandomAccountNumber() {
        Set<Integer> accountNumbers = bank.getAccountNumbers(); //a set of all the banks account numbers
        int size = accountNumbers.size();
        int randomIndex = (int) (Math.random() * size); //generates a random number from 0 to the num of accountNumbers in the bank
        int currentIndex = 0;
        //loop through the account numbers and return the account at the index that matches the randomIndex that was generated
        for (Integer accountNumber : accountNumbers) {
            if (currentIndex == randomIndex) {
                return accountNumber;
            }
            currentIndex++;
        }
        throw new IllegalStateException("Couldn't randomly select an account");
    }
    /**
     * Creates a random Money amount representing either a deposit or a withdrawal.
     *
     * @return A Money instance with a random amount in EUR currency.
     */
    private Money createRandomDepositOrWithdrawal() {
        double randomNumber = -10000 + 20000 * random.nextDouble(); //randomly generate an amount from -10000 to 10000 (if its negative it will be a withdrawal if its positive it will be a deposit
        // Convert to Money instance with EUR currency and round up the amount to 2 decimal places
        Money randomDepositOrWithdrawal = Money.of(CurrencyUnit.EUR, BigDecimal.valueOf(randomNumber).setScale(2, RoundingMode.HALF_UP));
        return randomDepositOrWithdrawal;
    }
}

