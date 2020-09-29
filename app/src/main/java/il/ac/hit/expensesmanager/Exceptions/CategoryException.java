package il.ac.hit.expensesmanager.Exceptions;

/**
 *  Represents exception that occurred in category class
 */
public class CategoryException extends ManagerExpensesException {
    /**
     * Constructor for this class
     */
    public CategoryException() {
        super();
    }

    /**
     * Constructor for this class
     * @param detailMessage
     */
    public CategoryException(String detailMessage) {
        super(detailMessage);

    }

    /**
     * Constructor for this class
     * @param detailMessage
     * @param ex
     */
    public CategoryException(String detailMessage, Exception ex)
    {
        super(detailMessage, ex);
    }

}
