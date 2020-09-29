package il.ac.hit.expensesmanager;

/**
 *Interface that represents a bridge between JavaScript and Java page
 */
public interface IBridgeJavaJS {

    /**
     * Adding expenseItem to the db
     * @param expenseItem the expenseItem
     * @return If succeeded or not
     */
    String addExpenseItem(String expenseItem);

    /**
     * Updating expenseItem in db
     * @param expenseItem the expenseItem
     * @return If succeeded or not
     */
    String updateExpenseItem(String expenseItem);

    /**
     * Opens settings activity
     * @return If succeeded or not
     */
    String openSettingsActivity();

    /**
     * Delete expenseItem from db
     * @param expenseItemId the expenseItem id
     * @return If succeeded or not
     */
    String deleteExpenseItem(String expenseItemId);

    /**
     * Returns expenseItem
     * @param expenseItemId the expenseItem expenseItemId
     * @return If succeeded returns the expenseItem if not an error message
     */
    String getExpenseItemByID(int expenseItemId);

    /**
     * Return expenseItems list by category
     * @param categoryID category id
     * @return If succeeded returns the expenseItems list if not an error message
     */
    String getExpenseItemsByCategory(int categoryID);

    /**
     * Returns expenseItems list by dates
     * @param from start date of period
     * @param to end date of period
     * @return If succeeded returns the expenseItems list if not an error message
     */
    String getExpenseItemsBetween(String from, String to);

    /**
     * Return categories list
     * @return If succeeded returns the categories list if not an error message
     */
    String getCategories();

    /**
     * Returns If succeeded returns the expenseItems list that in between dates from and to at the category id, if not an error message
     * @param from start date of period
     * @param to end date of period
     * @param categoryId category id
     * @return If succeeded returns the expenseItems list that in between dates from and to at the category id, if not an error message
     */
    String getCategoryExpenseItemsBetween(String from, String to, String categoryId);

    /**
     *  Exit the activity
     * @return If succeeded or not
     */
    String exitActivity();

    /**
     * Returns If succeeded returns the category balance value that in between dates from and to at the category id, if not an error message
     * @param dateFrom start date of period
     * @param dateTo end date of period
     * @param categoryID category id
     * @return If succeeded returns the category balance value that in between dates from and to at the category id, if not an error message
     */
    String getCategoryPriceBetweenDates(String dateFrom, String dateTo, int categoryID);

    /**
     * Returns one category balance value
     * @param categoryID
     * @return If succeeded returns the balance value of one category if not an error message
     */
    String getCategoryPrice(int categoryID);

    /**
     * Returns all category's balance value
     * @return If succeeded returns the balance value of all the category's if not an error message
     */
    String getAllExpenseItemsPrice();

    /**
     * Returns If succeeded returns true or false if expenseItems price in expenses period,if not succeeded returns an error message
     * @param expenseItemDate expenseItem Date
     * @param categoryId category Id
     * @param expenseItemNewValue expenseItem new price value
     * @param expenseItemId expenseItem Id
     * @return If succeeded returns true or false if expenseItems price in expenses period,if not succeeded returns an error message
     */
    String isAboveTheCategoryLimits(String expenseItemDate, String categoryId, float expenseItemNewValue, int expenseItemId);
}
