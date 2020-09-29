package il.ac.hit.expensesmanager;

import java.util.Date;

import il.ac.hit.expensesmanager.Exceptions.ExpensesManagerRuntimeException;
import il.ac.hit.expensesmanager.Exceptions.CategoryException;

/***
 * Represents one category
 */
public class Category {

    private int id;
    private String name;
    private int expensesLimit;
    private ExpensePeriod expensePeriod;
    private Date startExpensesDate;

    /***
     * Constructor for this class that gets all the above parader's
     *
     * @param id                category id
     * @param expensesLimit     expense limit of the current category
     * @param expensePeriod     expense period of the current category (month\week\day)
     * @param startExpensesDate start date of the current expense period
     * @throws CategoryException in case of illegal category inputs
     */
    public Category(int id, int expensesLimit, ExpensePeriod expensePeriod, Date startExpensesDate) throws CategoryException {
        setId(id);
        setExpensesLimit(expensesLimit);
        setExpensePeriod(expensePeriod);
        setStartExpensesDate(startExpensesDate);
    }

    /***
     * Constructor for this class that gets all the above parader's
     *
     * @param id                category id
     * @param name              category name
     * @param expensesLimit     expense limit of the current category
     * @param expensePeriod     expense period of the current category (month\week\day)
     * @param startExpensesDate start date of the current expense period
     * @throws CategoryException in case of illegal category inputs
     */
    public Category(int id, String name, int expensesLimit, ExpensePeriod expensePeriod, Date startExpensesDate) throws CategoryException {
        this(id, expensesLimit, expensePeriod, startExpensesDate);
        setName(name);
    }

    /**
     * /***
     * Constructor for this class that gets all the above parader's
     *
     * @param id   category id
     * @param name category name
     */
    public Category(int id, String name) {
        setId(id);
        setName(name);
    }

    /**
     * Returns category id
     * @return category id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets category id
     * @param id category id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns category name
     * @return category name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets category name
     * @param name category name
     */
    public void setName(String name) {
        if (name == null) {
            throw new ExpensesManagerRuntimeException("category name cannot be null in Category Class");
        }
        this.name = name;
    }

    /**
     * Returns category expense limit
     *
     * @return category expense limit
     */
    public int getExpensesLimit() {
        return expensesLimit;
    }

    /**
     * sets category expense limit
     *
     * @param expensesLimit category expense limit
     * @throws CategoryException in case of negative value
     */
    public void setExpensesLimit(int expensesLimit) throws CategoryException {
        if (expensesLimit < 0) {
            throw new CategoryException("Expenses limit cannot be less than zero");
        }
        this.expensesLimit = expensesLimit;
    }

    /**
     * Returns category start expense date
     *
     * @return category start expense date
     */
    public Date getStartExpensesDate() {
        return startExpensesDate;
    }

    /**
     * Sets category start expense date
     *
     * @param startExpensesDate category start expense date
     */
    public void setStartExpensesDate(Date startExpensesDate) {
        if (startExpensesDate == null) {
            throw new ExpensesManagerRuntimeException("startExpensesDate cannot be null in Category Class");
        }
        this.startExpensesDate = startExpensesDate;
    }

    /**
     * sets category expense period (month\week\day)
     * @param expensePeriod category expense period (month\week\day)
     */
    public void setExpensePeriod(ExpensePeriod expensePeriod) {
        if (expensePeriod == null) {
            throw new ExpensesManagerRuntimeException("expensePeriod cannot be null in Category Class");
        }
        this.expensePeriod = expensePeriod;
    }

    /**
     * Returns category expense period (month\week\day)
     * @return category expense period (month\week\day)
     */
    public ExpensePeriod getExpensePeriod() {
        return expensePeriod;
    }

    /***
     * Enum that represents possible expense period of time
     */
    public enum ExpensePeriod {
        Daily,
        Monthly,
        Weekly
    }
}
