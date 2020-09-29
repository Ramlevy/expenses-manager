package il.ac.hit.expensesmanager;

import android.content.Context;

import il.ac.hit.expensesmanager.Exceptions.ExpensesManagerRuntimeException;

/***
 * Represents a message that returns from Java side to Javascript.
 * Indicate if the function failed or seceded and the message in case of fail
 */
public class JSMessageCenter {

    private JsonConverter jsonConverter;
    private Status status;
    private String msg;

    /**
     * Constructor of the class
     * @param context context of the class
     */
    public JSMessageCenter(Context context) {
        this.setJsonConverter(new JsonConverter(context));
        this.setStatus(Status.Not_Working);
    }

    /***
     * Returns the current status
     * @return current status
     */
    public Status getStatus() {
        return status;
    }

    /***
     * Sets the status
     * @param status current status
     */
    public void setStatus(Status status) {
        if (status == null)
        {
            throw new ExpensesManagerRuntimeException("status manager cannot be null in JSMessageCenter Class");
        }
        this.status = status;
    }

    /***
     * Returns current message
     * @return current message
     */
    public String getMsg() {
        return msg;
    }

    /***
     * Sets the current message
     * @param msg current message
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /***
     * Return object the responsible for managing json stuff
     * @return json converter
     */
    public JsonConverter getJsonConverter() {
        return jsonConverter;
    }

    /***
     * Sets json converter for this class
     * @param jsonConverter jason manager
     */
    public void setJsonConverter(JsonConverter jsonConverter) {
        if (jsonConverter == null)
        {
            throw new ExpensesManagerRuntimeException("jsonConverter cannot be null in JSMessageCenter Class");
        }
        this.jsonConverter = jsonConverter;
    }

    /***
     * converts the object to json format
     * @return json string foment of the object
     */
    @Override
    public String toString() {
        return jsonConverter.finalConversion(getStatus(), getMsg());
    }

    /**
     * Enum that represents if error occurred will trying to use the db
     * and the status is being checked at javascript and show proper message
     */
    public enum Status {
        Working(1),
        Not_Working(-1);

        private final int id;

        /**
         * creating foreach status Adequate numbers
         * @param id
         */
        Status(int id) {
            this.id = id;
        }

        public int getValue() {
            return id;
        }
    }
}

