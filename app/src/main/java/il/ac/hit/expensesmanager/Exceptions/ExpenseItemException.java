package il.ac.hit.expensesmanager.Exceptions;

/**
 *  Represents exception that occurred on expenseItem class
 */
public class ExpenseItemException extends ManagerExpensesException {

    /**
     * Constructor for this class
     */
    public ExpenseItemException() {
        super();
    }

    /**
     * Constructor for this class
     * @param detailMessage
     */
    public ExpenseItemException(String detailMessage) {
        super(detailMessage);

    }

    /**
     * Constructor for this class
     * @param detailMessage
     * @param ex
     */
    public ExpenseItemException(String detailMessage, Exception ex)
    {
        super(detailMessage, ex);
    }

}
