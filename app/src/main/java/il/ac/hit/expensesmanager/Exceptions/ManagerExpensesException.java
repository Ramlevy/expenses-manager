package il.ac.hit.expensesmanager.Exceptions;

/**
 *  Represents global exception of the project
 */
public class ManagerExpensesException extends Exception {

    /**
     * Constructor for this class
     */
    public ManagerExpensesException() {
        super();
    }

    /**
     * Constructor for this class
     * @param e
     */
    public ManagerExpensesException(Exception e) {
        super(e);
    }

    /**
     * Constructor for this class
     * @param detailMessage
     */
    public ManagerExpensesException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructor for this class
     * @param detailMessage
     * @param ex
     */
    public ManagerExpensesException(String detailMessage, Exception ex)
    {
        super(detailMessage, ex);
    }

}
