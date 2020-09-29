package il.ac.hit.expensesmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;


import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import il.ac.hit.expensesmanager.Exceptions.ManagerExpensesException;
import il.ac.hit.expensesmanager.Exceptions.ExpensesManagerRuntimeException;
import il.ac.hit.expensesmanager.Exceptions.CategoryException;
import il.ac.hit.expensesmanager.Exceptions.ConverterJsonException;
import il.ac.hit.expensesmanager.Exceptions.ExpenseItemException;
import il.ac.hit.expensesmanager.Exceptions.DbException;
import il.ac.hit.expensesmanager.Exceptions.EmptyFieldsException;

/***
 * Represents Main BridgeJavaJS
 */
public class BridgeJavaJS implements IBridgeJavaJS {

    private SimpleDateFormat simpleDateFormat;
    private IExpenseItemDAO expenseItemDAO;
    private ICategoryDAO categoryDAO;
    private JsonConverter jsonConverter;
    private JSMessageCenter jsMessageCenter;
    private Activity activity;
    private Context context;

    /**
     * Constructor of the class
     *
     * @param context  this class context
     * @param activity the activity that presented now
     */
    public BridgeJavaJS(Context context, Activity activity) throws ManagerExpensesException {
        this.setActivity(activity);
        this.setCategoryDAO(new CategoryDAO(context));
        this.setExpenseItemDAO(new ExpenseItemDAO(context));
        this.setJsonConverter(new JsonConverter(context));
        this.setJSMessageCenter(new JSMessageCenter(context));
        this.setContext(context);
        this.setSimpleDateFormat(new SimpleDateFormat(context.getResources().getString(R.string.date_format)));
    }

    /**
     * Returns IExpenseItemDAO object
     *
     * @return IExpenseItemDAO object
     */
    public IExpenseItemDAO getExpenseItemDAO() {
        return expenseItemDAO;
    }

    /**
     * Setts IExpenseItemDAO object
     *
     * @param expenseItemDAO IExpenseItemDAO object
     */
    private void setExpenseItemDAO(IExpenseItemDAO expenseItemDAO) {
        if (expenseItemDAO == null) {
            throw new ExpensesManagerRuntimeException("expenseItemDAO cannot be null in class BridgeJavaJS");
        }
        this.expenseItemDAO = expenseItemDAO;
    }

    /**
     * Returns ICategoryDAO object
     *
     * @return ICategoryDAO object
     */
    public ICategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    /**
     * Setts categoryDAO
     *
     * @param categoryDAO
     */
    private void setCategoryDAO(ICategoryDAO categoryDAO) {
        if (categoryDAO == null) {
            throw new ExpensesManagerRuntimeException("categoryDAO cannot be null in class BridgeJavaJS");
        }
        this.categoryDAO = categoryDAO;
    }

    /**
     * Returns Json converter object
     *
     * @return Json converter object
     */
    public JsonConverter getJsonConverter() {
        return jsonConverter;
    }

    /**
     * Setts Json converter object
     *
     * @param jsonConverter Json converter object
     */
    private void setJsonConverter(JsonConverter jsonConverter) {
        if (jsonConverter == null) {
            throw new ExpensesManagerRuntimeException("jsonConverter cannot be null in class BridgeJavaJS");
        }
        this.jsonConverter = jsonConverter;
    }

    /**
     * Return this context of the class
     *
     * @return this context of the class
     */
    public Context getContext() {
        return context;
    }

    /**
     * Setting the context of the class
     *
     * @param context this context of the class
     */
    public void setContext(Context context) {
        if (context == null) {
            throw new ExpensesManagerRuntimeException("context cannot be null in class BridgeJavaJS");
        }
        this.context = context;
    }

    /**
     * Return simpleDateFormat
     *
     * @return simpleDateFormat
     */
    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    /**
     * Setting the simpleDateFormat
     *
     * @param simpleDateFormat
     */
    private void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        if (simpleDateFormat == null) {
            throw new ExpensesManagerRuntimeException("simpleDateFormat cannot be null in class BridgeJavaJS");
        }
        this.simpleDateFormat = simpleDateFormat;
    }

    /**
     * Returns JSMessageCenter object
     *
     * @return JSMessageCenter object
     */
    public JSMessageCenter getJSMessageCenter() {
        return jsMessageCenter;
    }

    /**
     * Setts JSMessageCenter object
     *
     * @param jsMessageCenter JSMessageCenter object, Represents a message that returns from Java side to Javascript
     */
    private void setJSMessageCenter(JSMessageCenter jsMessageCenter) {
        this.jsMessageCenter = jsMessageCenter;
    }

    /**
     * Returns the current activity
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Setting the current activity
     *
     * @param activity
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * Adding expenseItem to the db
     *
     * @param expenseItem the expenseItem
     * @return If succeeded or not
     */
    @Override
    @android.webkit.JavascriptInterface
    public String addExpenseItem(String expenseItem) {
        try {
            JSONObject jsonObject = jsonConverter.parseIntoJsonObject(expenseItem);
            ExpenseItem expenseItemObj = jsonConverter.convertJsonToExpenseItem(jsonObject);
            expenseItemDAO.insertNewLine(expenseItemObj);
            jsMessageCenter.setStatus(JSMessageCenter.Status.Working);
            jsMessageCenter.setMsg("succeed adding expenseItem");
        } catch (ExpenseItemException cie) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg(cie.getMessage());
        } catch (DbException | ConverterJsonException exception) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg("didn't succeed adding expenseItem to db, please fill the text fields correctly");
        } catch (ManagerExpensesException cte) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg(cte.getMessage());
        }

        return jsMessageCenter.toString();
    }

    /**
     * Updating expenseItem in db
     *
     * @param expenseItem the expenseItem
     * @return If succeeded or not
     */
    @Override
    @android.webkit.JavascriptInterface
    public String updateExpenseItem(String expenseItem) {
        try {
            JSONObject jsonObject = jsonConverter.parseIntoJsonObject(expenseItem);
            ExpenseItem expenseItemObj = jsonConverter.convertJsonToExpenseItem(jsonObject);
            expenseItemDAO.updateLine(expenseItemObj);
            jsMessageCenter.setStatus(JSMessageCenter.Status.Working);
            jsMessageCenter.setMsg("succeed update expenseItem");
        } catch (ExpenseItemException cie) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg(cie.getMessage());
        } catch (DbException | ConverterJsonException exception) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg("didn't succeed update expenseItem, please fill the text fields correctly");
        } catch (ManagerExpensesException cte) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg(cte.getMessage());
        }
        return jsMessageCenter.toString();
    }

    /**
     * Delete expenseItem from db
     *
     * @param expenseItemId the expenseItem id
     * @return If succeeded or not
     */
    @Override
    @android.webkit.JavascriptInterface
    public String deleteExpenseItem(String expenseItemId) {
        try {
            expenseItemDAO.deleteLine(expenseItemId);
            jsMessageCenter.setStatus(JSMessageCenter.Status.Working);
            jsMessageCenter.setMsg("succeed deleting expenseItem");
        } catch (SQLiteException e) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg("didn't succeed deleting expenseItem");
        }
        return jsMessageCenter.toString();
    }

    /**
     * Returns expenseItem
     *
     * @param expenseItemId the expenseItem expenseItemId
     * @return If succeeded returns the expenseItem if not an error message
     */
    @Override
    @android.webkit.JavascriptInterface
    public String getExpenseItemByID(int expenseItemId) {
        try {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Working);
            jsMessageCenter.setMsg(jsonConverter.convertExpenseItemToJson((ExpenseItem) expenseItemDAO.getExpenseItemById(expenseItemId)).toString());
        } catch (ExpenseItemException cie) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg(cie.getMessage());
        } catch (DbException | ConverterJsonException exception) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg("didn't succeed getting expenseItem by this id");
        }
        return jsMessageCenter.toString();
    }

    /**
     * Return expenseItems list by category
     *
     * @param categoryID category id
     * @return If succeeded returns the expenseItems list if not an error message
     */
    @Override
    @android.webkit.JavascriptInterface
    public String getExpenseItemsByCategory(int categoryID) {
        String expenseItemsInStringFormat;
        try {
            ArrayList<ExpenseItem> expenseItemsList = expenseItemDAO.getExpenseItemsByCategory(categoryID);
            expenseItemsInStringFormat = jsonConverter.convertObjectsListToJson(expenseItemsList);
            jsMessageCenter.setStatus(JSMessageCenter.Status.Working);
            jsMessageCenter.setMsg(expenseItemsInStringFormat);
        } catch (ExpenseItemException cie) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg(cie.getMessage());
        } catch (DbException | ConverterJsonException exception) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg("didn't succeed getting expenseItems list with that category id");
        }
        return jsMessageCenter.toString();
    }

    /**
     * Returns expenseItems list by dates
     *
     * @param from start date of period
     * @param to   end date of period
     * @return If succeeded returns the expenseItems list if not an error message
     */
    @Override
    @android.webkit.JavascriptInterface
    public String getExpenseItemsBetween(String from, String to) {
        String expenseItemsInStringFormat;
        try {
            ArrayList<ExpenseItem> expenseItemsList = expenseItemDAO.getExpenseItemsBetweenDates(from, to);
            expenseItemsInStringFormat = jsonConverter.convertObjectsListToJson(expenseItemsList);
            jsMessageCenter.setStatus(JSMessageCenter.Status.Working);
            jsMessageCenter.setMsg(expenseItemsInStringFormat);
        } catch (ExpenseItemException cie) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg(cie.getMessage());
        } catch (DbException | ConverterJsonException | EmptyFieldsException exception) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg("didn't succeed getting expenseItems list between the entered dates, please fill the text fields correctly");
        }

        return jsMessageCenter.toString();
    }

    /**
     * Returns If succeeded returns the expenseItems list that in between dates from and to at the category id, if not an error message
     *
     * @param from       start date of period
     * @param to         end date of period
     * @param categoryId category id
     * @return If succeeded returns the expenseItems list that in between dates from and to at the category id, if not an error message
     */
    @Override
    @android.webkit.JavascriptInterface
    public String getCategoryExpenseItemsBetween(String from, String to, String categoryId) {
        String expenseItemsInStringFormat = "";
        try {
            ArrayList<ExpenseItem> expenseItemsList = expenseItemDAO.getExpenseItemsBetweenDatesInSpecificCategory(from, to, categoryId);
            expenseItemsInStringFormat = jsonConverter.convertObjectsListToJson(expenseItemsList);
            jsMessageCenter.setStatus(JSMessageCenter.Status.Working);
            jsMessageCenter.setMsg(expenseItemsInStringFormat);
        } catch (ExpenseItemException cie) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg(cie.getMessage());
        } catch (DbException | ConverterJsonException | EmptyFieldsException exception) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg("didn't succeed getting category expenseItems between the entered dates, please fill the text fields correctly");
        }
        return jsMessageCenter.toString();
    }

    /**
     * Return categories list
     *
     * @return If succeeded returns the categories list if not an error message
     */
    @Override
    @android.webkit.JavascriptInterface
    public String getCategories() {
        String categoryInStringFormat;
        try {
            ArrayList<Category> categories = categoryDAO.getCategories();
            categoryInStringFormat = jsonConverter.convertObjectsListToJson(categories);
            jsMessageCenter.setStatus(JSMessageCenter.Status.Working);
            jsMessageCenter.setMsg(categoryInStringFormat);
        } catch (CategoryException ce) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg(ce.getMessage());
        } catch (DbException | ConverterJsonException exception) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg("didn't succeed getting categories list");
        }
        return jsMessageCenter.toString();
    }

    /**
     * Exit the activity
     *
     * @return If succeeded or not
     */
    @Override
    @android.webkit.JavascriptInterface
    public String exitActivity() {
        try {
            activity.finish();
            jsMessageCenter.setStatus(JSMessageCenter.Status.Working);
            jsMessageCenter.setMsg("succeed exit the activity");
        } catch (NullPointerException e) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg("didn't succeed exit the activity");
        }
        return jsMessageCenter.toString();
    }

    /**
     * Returns If succeeded returns the category balance value that in between dates from and to at the category id, if not an error message
     *
     * @param dateFrom   start date of period
     * @param dateTo     end date of period
     * @param categoryID category id
     * @return If succeeded returns the category balance value that in between dates from and to at the category id, if not an error message
     */
    @Override
    @android.webkit.JavascriptInterface
    public String getCategoryPriceBetweenDates(String dateFrom, String dateTo, int categoryID) {
        String price;
        try {
            price = String.valueOf(expenseItemDAO.getExpenseItemsPriceBetweenDates(categoryID, dateFrom, dateTo, -1));
            jsMessageCenter.setStatus(JSMessageCenter.Status.Working);
            jsMessageCenter.setMsg(price);
        } catch (DbException | EmptyFieldsException e) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg("didn't succeed getting the category price between the dates, please fill the text fields correctly");
        }
        return jsMessageCenter.toString();
    }

    /**
     * Returns one category balance value
     *
     * @param categoryID
     * @return If succeeded returns the balance value of one category if not an error message
     */
    @Override
    @android.webkit.JavascriptInterface
    public String getCategoryPrice(int categoryID) {
        String price;
        try {
            price = String.valueOf(expenseItemDAO.getExpenseItemsPriceInSpecificCategory(categoryID));
            jsMessageCenter.setStatus(JSMessageCenter.Status.Working);
            jsMessageCenter.setMsg(price);
        } catch (DbException e) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg("didnt succeed getting category outcome value");
        }
        return jsMessageCenter.toString();
    }

    /**
     * Returns all category's balance value
     *
     * @return If succeeded returns the balance value of all the category's if not an error message
     */
    @Override
    @android.webkit.JavascriptInterface
    public String getAllExpenseItemsPrice() {
        String price;
        try {
            price = String.valueOf(expenseItemDAO.getAllExpenseItemsPrice());
            jsMessageCenter.setStatus(JSMessageCenter.Status.Working);
            jsMessageCenter.setMsg(price);
        } catch (DbException e) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg("didnt succeed gettting All ExpenseItems Price");
        }
        return jsMessageCenter.toString();
    }

    /**
     * Opens settings activity
     * @return If succeeded or not
     */
    @Override
    @android.webkit.JavascriptInterface
    public String openSettingsActivity() {
        try {
            Intent i = new Intent(context, SettingsActivity.class);
            this.context.startActivity(i);
            jsMessageCenter.setStatus(JSMessageCenter.Status.Working);
            jsMessageCenter.setMsg("succeed open Settings Activity");
        } catch (NullPointerException e) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg("didn't succeed open Settings Activity");
        }
        return jsMessageCenter.toString();
    }

    /**
     * Returns If succeeded returns true or false if expenseItems price in expenses period,if not succeeded returns an error message
     * @param expenseItemDateStr  expenseItem Date of parches
     * @param expenseItemNewValue expenseItem new price value
     * @param expenseItemId       expenseItem Id
     * @param categoryId       category Id
     * @return If succeeded returns true or false if expenseItems price in expenses period,if not succeeded returns an error message
     */
    @Override
    @android.webkit.JavascriptInterface
    public String isAboveTheCategoryLimits(String expenseItemDateStr, String categoryId, float expenseItemNewValue, int expenseItemId) {
        boolean isAboveTheCategoryLimits;
        try {
            Category category = (Category) categoryDAO.getCategoryById(Integer.valueOf(categoryId));
            // calculate the start date of the period which includes the expenseItem
            Calendar fromDate = ItemsInDateRange.startOfItemsPeriod(simpleDateFormat.parse(expenseItemDateStr), category.getExpensePeriod(), category.getStartExpensesDate());
            Calendar toDate = Calendar.getInstance();
            toDate.setTime(fromDate.getTime());
            int expensePeriod = 0;
            switch (category.getExpensePeriod()) {
                case Monthly: {
                    expensePeriod = Calendar.MONTH;
                    break;
                }
                case Weekly: {
                    expensePeriod = Calendar.WEEK_OF_MONTH;
                    break;
                }
                case Daily: {
                    expensePeriod = Calendar.DAY_OF_MONTH;
                    break;
                }
            }
            toDate.add(expensePeriod, 1);
            toDate.add(Calendar.DAY_OF_MONTH, -1);
            float expenseItemsPriceBetweenDates = expenseItemDAO.getExpenseItemsPriceBetweenDates(Integer.valueOf(categoryId), simpleDateFormat.format(fromDate.getTime()), simpleDateFormat.format(toDate.getTime()), expenseItemId);

            if (category.getExpensesLimit() < expenseItemsPriceBetweenDates + expenseItemNewValue) {
                isAboveTheCategoryLimits = true;
            } else {
                isAboveTheCategoryLimits = false;
            }
            jsMessageCenter.setStatus(JSMessageCenter.Status.Working);
            jsMessageCenter.setMsg(String.valueOf(isAboveTheCategoryLimits));
        } catch (EmptyFieldsException | ParseException | CategoryException | ConverterJsonException | DbException e) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg("Wrong Date, please fill the text fields correctly");
        }
        return jsMessageCenter.toString();
    }
}
