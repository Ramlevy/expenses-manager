package il.ac.hit.expensesmanager;
import java.util.Date;

import il.ac.hit.expensesmanager.Exceptions.ExpensesManagerRuntimeException;
import il.ac.hit.expensesmanager.Exceptions.ExpenseItemException;

/**
 * Represents one expenseItem
 */
public class ExpenseItem {
    private int id;
    private String name;
    private String comment;
    private float expenseValue;
    private int categoryID;
    private Date actionDate;

    /**
     * Constructor for this class that gets all the above parader's
     *
     * @param id expenseItem id
     * @param name expenseItem name
     * @param comment expenseItem comment if needed
     * @param expenseValue expenseItem price
     * @param categoryID category ID of the expenseItem
     * @param actionDate expenseItem date of parches
     * @throws ExpenseItemException
     */
    public ExpenseItem(int id, String name, String comment, float expenseValue, int categoryID, Date actionDate) throws ExpenseItemException{
        setId(id);
        setName(name);
        setComment(comment);
        setExpenseValue(expenseValue);
        setCategoryID(categoryID);
        setActionDate(actionDate);
    }

    /**
     * Returns expenseItem id
     * @return expenseItem id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets expenseItem id
     * @param id expenseItem id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns expenseItem name
     * @return expenseItem name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets expenseItem name
     * @param name expenseItem name
     */
    public void setName(String name) throws ExpenseItemException{
        if (name == null || name.trim().equals("")){
            throw new ExpenseItemException("Expense Item name cannot be empty");
        }
        this.name = name;
    }

    /**
     * Return comment of expenseItem
     * @return comment of expenseItem
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets expenseItem comment
     * @param comment of expenseItem
     */
    public void setComment(String comment) {
        if (comment == null){
            throw new ExpensesManagerRuntimeException("comment cannot be null in ExpenseItem Class");
        }
        this.comment = comment;
    }

    /**
     * Return expenseItem price value
     * @return expenseItem price value
     */
    public float getExpenseValue() {
        return expenseValue;
    }

    /**
     * Sets expenseItem price value
     * @param expenseValue expenseItem price value
     * @throws ExpenseItemException
     */
    public void setExpenseValue(float expenseValue) throws ExpenseItemException{
        if (expenseValue < 0) {
            throw new ExpenseItemException("Expense Item price cannot be less than zero");
        }
        this.expenseValue = expenseValue;
    }

    /**
     * Returns the relevant category id of this expenseItem
     * @return
     */
    public int getCategoryID() {
        return categoryID;
    }

    /**
     * Sets the relevant category id of this expenseItem
     * @param categoryID the relevant category id of this expenseItem
     */
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * Return expenseItem date of parches
     * @return expenseItem date of parches
     */
    public Date getActionDate() {
        return actionDate;
    }

    /**
     * Sets the expenseItem date of parches
     * @param actionDate expenseItem date of parches
     */
    public void setActionDate(Date actionDate) {
        if (actionDate == null) {
            throw new ExpensesManagerRuntimeException("actionDate cannot be null in ExpenseItem Class");
        }
        this.actionDate = actionDate;
    }
}
