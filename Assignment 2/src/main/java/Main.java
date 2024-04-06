
import java.time.LocalDate;
import java.util.List;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

/**
 * Main class for testing the functionality of the ExpensesPortal.
 *
 * @author Fionn McArdle
 */

public class Main {

    public static void main(String[] args) {
        ExpensesPortal portal = new ExpensesPortal();

        // Submitting Expenses
        portal.submitExpense(new Expense(LocalDate.of(2022, 9, 23), "Flight to Glasgow", Expenses.TRAVEL_AND_SUBSISTENCE, Money.of(CurrencyUnit.EUR, 270.59)));
        portal.submitExpense(new Expense(LocalDate.of(2022, 9, 20), "Dell 17-inch monitor", Expenses.EQUIPMENT, Money.of(CurrencyUnit.USD, 540.00)));
        portal.submitExpense(new Expense(LocalDate.of(2022, 9, 21), "Java for Dummies", Expenses.OTHER, Money.of(CurrencyUnit.EUR, 17.99)));

        // Using Lambda Expression to print the expense
        portal.printExpenses(expenses -> {
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        });

        // Using Anonymous Inner Class to Print Summary of expesnes
        portal.printExpenses(new ExpensePrinter() {
            @Override
            public void print(List<Expense> expenses) {
                Money total = ExpensesPortal.sumExpenses(expenses);
                System.out.println("There are " + expenses.size() + " expenses in the system totalling to a value of " + total + ".");
            }
        });

        //call the printer expenses method using printerbylabel as the parameter
        portal.printExpenses(new PrinterByLabel());
    }
}


