import java.time.LocalDate;
import org.joda.money.Money;

/**
 * Represents an employee expense claim system.
 * An expense has a date, description, type of expenses, cost, and needs approval.
 *
 * @author Fionn McArdle
 */
public class Expense {
    private LocalDate date;
    private String description;
    private Expenses expenses;
    private Money cost;
    private boolean approved;

    /**
     * @param date        the date when the expense was
     * @param description a description of the expense
     * @param expenses    the type of expense
     * @param cost      the cost of the expense
     */
    Expense(LocalDate date, String description, Expenses expenses, Money cost){
        this.date = date;
        this.description = description;
        this.expenses = expenses;
        this.cost = cost;
        this.approved = false; //false by default
    }

    /**
     * method to approve an expense
     */
    public void approved() {
        approved = true;
    }

    /**
     * returns the date of the expense
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * returns the description of the expense
     */
    public String getDescription() {
        return description;
    }

    /**
     * returns the type of the expense
     */
    public Expenses getExpenses() {
        return expenses;
    }

    /**
     * returns the cost of the expense
     */
    public Money getCost() {
        return cost;
    }

    /**
     * Returns a string representation of the expense in the form "date: description - expenses - cost"
     */
    @Override
    public String toString(){
        return date + ": " + description + " - " + expenses + " - " + cost;
    }
}

