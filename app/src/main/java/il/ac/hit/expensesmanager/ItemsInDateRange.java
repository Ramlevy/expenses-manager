package il.ac.hit.expensesmanager;

import java.util.Calendar;
import java.util.Date;

/**
 * This Class Have Some
 */
public class ItemsInDateRange {

    /**
     * Returns the start date of period which the expenseItem include in
     * @param expenseItemDate date of the Expense Item you want to get
     * @param categoryExpensePeriod  category Expense Period
     * @param startExpensesDate start Expenses Date
     * @return start date of the period which the expenseItem include in
     */
    public static Calendar startOfItemsPeriod(Date expenseItemDate, Category.ExpensePeriod categoryExpensePeriod,Date startExpensesDate) {
        //today date
        Calendar startingDate = Calendar.getInstance();
        startingDate.setTime(startExpensesDate);

        //finds the current period type
        int expensesPeriodType = Calendar.MONTH;
        switch (categoryExpensePeriod) {
            case Monthly: {
                expensesPeriodType = Calendar.MONTH;
                break;
            }
            case Weekly: {
                expensesPeriodType = Calendar.WEEK_OF_MONTH;
                break;
            }
            case Daily: {
                expensesPeriodType = Calendar.DAY_OF_MONTH;
            }
        }

        //looks for the nearest date to the Expense Item date
        while (!(startingDate.getTime().after(expenseItemDate))) {
            startingDate.add(expensesPeriodType, 1);
        }
        while (startingDate.getTime().after(expenseItemDate)) {
            startingDate.add(expensesPeriodType, -1);
        }
        return startingDate;
    }
}
