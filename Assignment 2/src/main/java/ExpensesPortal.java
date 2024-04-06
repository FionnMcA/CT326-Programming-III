import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that contains a list of the expenses and allows users to submit expenses
 *
 * @author Fionn McArdle
 */

public class ExpensesPortal {

    /** A list to hold the expenses. */
    private List<Expense> expenses = new ArrayList<>();

    /** A portal where the users can submit an expense.
     *  @param expense The expense to be added.
     */
    public void submitExpense(Expense expense) {
        expenses.add(expense);
    }

    /** printer to print the expenses
     *  @param printer Specifies how the expenses will be displayed
     */
    public void printExpenses(ExpensePrinter printer) {
        printer.print(expenses);
    }

    /**
     * Calculates the total amount of submitted expenses. and converts dollar expenses to euros if neccessary
     *
     * @param expenses The list of expenses.
     * @return The total cost expenses in euro.
     */
    public static Money sumExpenses(List<Expense> expenses) {
        Money total = Money.zero(CurrencyUnit.EUR); // setting total to zero Euros.
        BigDecimal conversionRate = BigDecimal.valueOf(0.95); // conversion rate from USD to EUR

        //loop through the expenses getting each expesense's cost
        for (Expense expense : expenses) {
            Money cost = expense.getCost();
            //if the expesnse is in dollars convert it to euro
            if (cost.getCurrencyUnit().equals(CurrencyUnit.USD)) {
                cost = cost.convertedTo(CurrencyUnit.EUR, conversionRate, RoundingMode.HALF_UP);
            }
            //add the expense cost to the total
            total = total.plus(cost);
        }
        return total; //return the total
    }
}

