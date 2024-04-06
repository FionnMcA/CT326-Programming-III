import java.util.List;

/**
 * An implementation of the ExpensePrinter interface that organizes expenses by their labels before printing.
 *
 * @author Fionn McArdle
 */
public class PrinterByLabel implements ExpensePrinter {

    @Override
    public void print(List<Expense> expenses) {
        // Printing expenses organized by their label
        //loops over the types of expenses and prints them
        for (Expenses type : Expenses.values()) {
            System.out.println(type);

            //loop over the expenses
            for (Expense expense : expenses) {
                // Check if the current expense matches the current type so we print all expense types together
                if (expense.getExpenses() == type) {
                    //print the expenses toString
                    System.out.println(expense);
                }
            }
            System.out.println();
        }
    }
}
