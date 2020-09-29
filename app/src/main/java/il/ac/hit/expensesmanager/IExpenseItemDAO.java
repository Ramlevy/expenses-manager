package il.ac.hit.expensesmanager;
import java.util.ArrayList;

import il.ac.hit.expensesmanager.Exceptions.ConverterJsonException;
import il.ac.hit.expensesmanager.Exceptions.ExpenseItemException;
import il.ac.hit.expensesmanager.Exceptions.DbException;
import il.ac.hit.expensesmanager.Exceptions.EmptyFieldsException;

/**
 * Interface that represents Expense Item data access object (connection to Expense Item table)
 */
public interface IExpenseItemDAO extends IDatabaseManager {

    /**
     * Returns Expense Item by its ID from DB
     * @param id Expense Item id
     * @return Expense Item by its ID from DB
     * @throws DbException
     * @throws ConverterJsonException
     * @throws ExpenseItemException
     */
    Object getExpenseItemById(int id) throws DbException, ConverterJsonException, ExpenseItemException;

    /**
     * Returns Expense Items with date between dates
     * @param dateFrom from this date
     * @param dateTo  to this date
     * @return Expense Items with date between dates
     * @throws DbException
     * @throws ConverterJsonException
     * @throws ExpenseItemException
     */
    ArrayList<ExpenseItem> getExpenseItemsBetweenDates(String dateFrom, String dateTo) throws DbException, ConverterJsonException, ExpenseItemException, EmptyFieldsException;

    /**
     * Returns Expense Items of specific category between date
     * @param dateFrom from this date
     * @param dateTo to this date
     * @param categoryId specific category id
     * @return Expense Items of specific category between date
     * @throws DbException
     * @throws ConverterJsonException
     * @throws ExpenseItemException
     */
    ArrayList<ExpenseItem> getExpenseItemsBetweenDatesInSpecificCategory(String dateFrom, String dateTo, String categoryId) throws DbException, ConverterJsonException, ExpenseItemException, EmptyFieldsException;

    /**
     * Returns Expense Items of specific category
     * @param categoryId specific category id
     * @return Expense Items of specific category
     * @throws DbException
     * @throws ConverterJsonException
     * @throws ExpenseItemException
     */
    ArrayList<ExpenseItem> getExpenseItemsByCategory(int categoryId) throws DbException, ConverterJsonException, ExpenseItemException;

    /**
     * Returns sum of Expense Items price of specific category between dates excluding a specific Expense Item price
     * @param categoryID specific category id
     * @param dateFrom from this date
     * @param dateTo to this date
     * @param expenseItemId specific Expense Item id
     * @return sum of Expense Items price of specific category between dates
     * @throws DbException
     */
    float getExpenseItemsPriceBetweenDates(int categoryID, String dateFrom, String dateTo, int expenseItemId) throws DbException, EmptyFieldsException;

    /**
     * Returns sum of Expense Items price of specific category
     * @param categoryID specific category id
     * @return Sum of Expense Items price of specific category
     * @throws DbException
     */
    float getExpenseItemsPriceInSpecificCategory(int categoryID) throws DbException;

    /**
     * Returns total sum price of all Expense Items
     * @return total sum price of all Expense Items
     * @throws DbException
     */
    float getAllExpenseItemsPrice() throws DbException;

}
