package il.ac.hit.expensesmanager.Exceptions;

/**
 *  Represents exception that occurred on runtime
 */
public class ExpensesManagerRuntimeException extends RuntimeException {
    public ExpensesManagerRuntimeException() {
        super();
    }

    /**
     * Constructor for this class
     * @param detailMessage
     */
    public ExpensesManagerRuntimeException(String detailMessage) {
        super(detailMessage);

    }

    /**
     * Constructor for this class
     * @param detailMessage
     * @param ex
     */
    public ExpensesManagerRuntimeException(String detailMessage, Exception ex) {
        super(detailMessage, ex);
    }

}
