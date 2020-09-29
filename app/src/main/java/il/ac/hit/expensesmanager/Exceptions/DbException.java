package il.ac.hit.expensesmanager.Exceptions;

/**
 *  Represents exception that occurred when using the database
 */
public class DbException extends ManagerExpensesException {

    /**
     * Constructor for this class
     */
    public DbException() {
        super();
    }

    /**
     * Constructor for this class
     * @param detailMessage
     */
    public DbException(String detailMessage) {
        super(detailMessage);

    }

    /**
     * Constructor for this class 
     * @param detailMessage
     * @param ex
     */
    public DbException(String detailMessage, Exception ex)
    {
        super(detailMessage, ex);
    }
}