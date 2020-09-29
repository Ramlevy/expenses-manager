package il.ac.hit.expensesmanager.Exceptions;

/**
 *  Represents exception that occurred when trying to convert json
 */
public class ConverterJsonException extends ManagerExpensesException {

    /**
     * Constructor for this class
     */
    public ConverterJsonException() {
        super();
    }

    /**
     * Constructor for this class
     * @param e
     */
    public ConverterJsonException(Exception e) {
        super(e);

    }

    /**
     * Constructor for this class 
     * @param detailMessage
     */
    public ConverterJsonException(String detailMessage) {
        super(detailMessage);

    }

    /**
     *
     * @param detailMessage
     * @param ex
     */
    public ConverterJsonException(String detailMessage, Exception ex)
    {
        super(detailMessage, ex);
    }
}