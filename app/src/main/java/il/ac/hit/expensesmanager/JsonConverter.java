package il.ac.hit.expensesmanager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import il.ac.hit.expensesmanager.Exceptions.ManagerExpensesException;
import il.ac.hit.expensesmanager.Exceptions.ExpensesManagerRuntimeException;
import il.ac.hit.expensesmanager.Exceptions.ConverterJsonException;
import il.ac.hit.expensesmanager.Exceptions.ExpenseItemException;

/**
 * Represents Json converter
 */
public class JsonConverter {

    SimpleDateFormat simpleDateFormat;
    Context context;

    /**
     * Constructor of the class
     * @param context context of the class
     */
    public JsonConverter(Context context) {
        setContext(context);
        setSimpleDateFormat(new SimpleDateFormat(context.getResources().getString(R.string.date_format)));
    }

    /**
     * Return simpleDateFormat
     * @return simpleDateFormat
     */
    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    /**
     * Setting the simpleDateFormat
     * @param simpleDateFormat
     */
    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        if (simpleDateFormat == null){
            throw new ExpensesManagerRuntimeException("simpleDateFormat cannot be null in JsonConverter Class");
        }
        this.simpleDateFormat = simpleDateFormat;
    }

    /**
     * Returns the context of the class
     * @return the context of the class
     */
    public Context getContext() {
        return context;
    }

    /**
     * Setting the context of the class
     * @param context the context of the class
     */
    public void setContext(Context context) {
        if (context == null) {
            throw new ExpensesManagerRuntimeException("content cannot be null in JsonConverter Class");
        }
        this.context = context;
    }

    /**
     * Convert Json To ExpenseItem
     * @param jsonObject
     * @return ExpenseItem
     * @throws ConverterJsonException
     * @throws ExpenseItemException
     */
    public ExpenseItem convertJsonToExpenseItem(JSONObject jsonObject) throws ManagerExpensesException {
        ExpenseItem expenseItem;
        try {
            expenseItem = new ExpenseItem(jsonObject.getInt(getContext().getResources().getString(R.string.json_id)),
                    jsonObject.getString(getContext().getResources().getString(R.string.json_name)),
                    jsonObject.getString(getContext().getResources().getString(R.string.json_comment)),
                            Float.parseFloat(jsonObject.getString(getContext().getResources().getString(R.string.json_EXPENSE_VALUE))),
                            Integer.parseInt(jsonObject.getString(getContext().getResources().getString(R.string.json_category_id))),
                                    simpleDateFormat.parse(jsonObject.getString(getContext().getResources().getString(R.string.json_date))));
        } catch (JSONException | ParseException | NumberFormatException e) {
            throw new ConverterJsonException("didn't succeed convert Json To ExpenseItem", e);
        }
        return expenseItem;
    }

    /**
     * Convert Json To Category
     * @param jsonObject
     * @return Category
     * @throws ManagerExpensesException
     */
    public Category convertJsonToCategory(JSONObject jsonObject) throws ManagerExpensesException {
        Category category;
        try {
            //TODO: fix, change name of strings in jason to match the form
            category = new Category(jsonObject.getInt(getContext().getResources().getString(R.string.json_id)),
                    jsonObject.getInt(getContext().getResources().getString(R.string.json_expensesLimit)),
                    Enum.valueOf(Category.ExpensePeriod.class, jsonObject.getString(getContext().getResources().getString(R.string.json_expensePeriod))),
                    simpleDateFormat.parse(jsonObject.getString(getContext().getResources().getString(R.string.json_startExpensesDate))));
        } catch (JSONException | ParseException | NumberFormatException e) {
            throw new ConverterJsonException(e);
        }
        return category;
    }

    /**
     * Convert Objects List To Json
     * @param object
     * @return convert Objects List To Json
     * @throws ConverterJsonException
     */
    public String convertObjectsListToJson(Object object) throws ConverterJsonException {
        ArrayList<Object> objects = (ArrayList<Object>) object;
        JSONArray jsonArray = new JSONArray();
        for (Object obj : objects) {
            JSONObject objInJson = convertObjectToJson(obj);
            jsonArray.put(objInJson);
        }
        return jsonArray.toString();
    }

    /**
     * Convert Object To Json
     * @param obj object
     * @return JsonObj
     * @throws ConverterJsonException
     */
    private JSONObject convertObjectToJson(Object obj) throws ConverterJsonException {

        JSONObject jsonObject = null;
        if (obj instanceof ExpenseItem) {
            jsonObject = convertExpenseItemToJson((ExpenseItem) obj);
        }

        if (obj instanceof Category) {
            jsonObject = convertCategoryToJson((Category) obj);
        }
        return jsonObject;
    }

    /**
     * Convert ExpenseItem To Json
     * @param expenseItem
     * @return expenseItem as Json
     * @throws ConverterJsonException
     */
    public JSONObject convertExpenseItemToJson(ExpenseItem expenseItem) throws ConverterJsonException {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject();
            jsonObject.put(DBHandler.EXPENSE_ITEM_ID, expenseItem.getId());
            jsonObject.put(DBHandler.EXPENSE_NAME, expenseItem.getName());
            jsonObject.put(DBHandler.EXPENSE_COMMENT, expenseItem.getComment());
            jsonObject.put(DBHandler.EXPENSE_VALUE, expenseItem.getExpenseValue());
            jsonObject.put(DBHandler.EXPENSE_CATEGORY_ID, expenseItem.getCategoryID());
            jsonObject.put(DBHandler.EXPENSE_ACTION_DATE, expenseItem.getActionDate());
        } catch (JSONException e) {
            throw new ConverterJsonException("didnt succeed convert ExpenseItem To  Json",e);
        }

        return jsonObject;
    }

    /**
     * Convert Category To Json
     * @param category
     * @return category as Json
     * @throws ConverterJsonException
     */
    public JSONObject convertCategoryToJson(Category category) throws ConverterJsonException {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject();
            jsonObject.put(DBHandler.CATEGORY_ID, category.getId());
            jsonObject.put(DBHandler.CATEGORY_NAME, category.getName());
            jsonObject.put(DBHandler.CATEGORY_ExpensesLimit, category.getExpensesLimit());
            jsonObject.put(DBHandler.CATEGORY_ExpensePeriod, category.getExpensePeriod());
            jsonObject.put(DBHandler.CATEGORY_StartExpensesDate, category.getStartExpensesDate());
        } catch (JSONException e) {
            throw new ConverterJsonException("didnt succeed convert Category To Json",e);
        }

        return jsonObject;
    }

    /**
     * Parsing Into JsonObject
     * @param item
     * @return If succeeded returns JsonObject if not an error message
     * @throws ConverterJsonException
     */
    public JSONObject parseIntoJsonObject(String item) throws ConverterJsonException {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(item);
        } catch (JSONException e) {
            throw new ConverterJsonException("didnt succeed parse into Json",e);
        }
        return jsonObject;
    }

    /**
     * Return final conversion ready to send to javascript
     * @param status status  to javascript
     * @param msg message to javascript
     * @returnfinal conversion ready to send to javascript
     */
    public String finalConversion(JSMessageCenter.Status status, String msg) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status", status.getValue());
            jsonObject.put("message", msg);
        } catch (JSONException e) {
            return "{\"status\" : \"-1\", \"message\" : \"failed to create json object\"}";
        }
        return jsonObject.toString();
    }
}
