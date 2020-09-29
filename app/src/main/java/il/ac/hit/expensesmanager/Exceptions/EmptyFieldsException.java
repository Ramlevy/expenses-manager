package il.ac.hit.expensesmanager.Exceptions;

/**
 *  Represents exception that occurred when the text fields empty
 */
public class EmptyFieldsException extends ManagerExpensesException {

    /**
     * Constructor for this class
     */
    public EmptyFieldsException() {
        super();
    }

    /**
     * Constructor for this class
     * @param detailMessage
     */
    public EmptyFieldsException(String detailMessage) {
        super(detailMessage);

    }

    /**
     * Constructor for this class
     * @param detailMessage
     * @param ex
     */
    public EmptyFieldsException(String detailMessage, Exception ex)
    {
        super(detailMessage, ex);
    }
}