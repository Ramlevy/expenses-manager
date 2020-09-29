package il.ac.hit.expensesmanager;
import android.content.Context;

import org.json.JSONObject;

import il.ac.hit.expensesmanager.Exceptions.ManagerExpensesException;
import il.ac.hit.expensesmanager.Exceptions.ExpensesManagerRuntimeException;
import il.ac.hit.expensesmanager.Exceptions.CategoryException;
import il.ac.hit.expensesmanager.Exceptions.ConverterJsonException;
import il.ac.hit.expensesmanager.Exceptions.DbException;

/**
 * Represents SettingsBridgeJavaJS
 */
public class SettingsBridgeJavaJS implements ISettingsBridgeJavaJS {
    private Context context;
    private ICategoryDAO categoryDAO;
    private JsonConverter jsonConverter;
    private JSMessageCenter jsMessageCenter;

    /**
     * Constructor of the class
     * @param context this class context
     * @throws ManagerExpensesException
     */
    public SettingsBridgeJavaJS(Context context) throws ManagerExpensesException {
        setContext(context);
        setCategoryDAO(new CategoryDAO(context));
        setJsonConverter(new JsonConverter(context));
        setJSMessageCenter(new JSMessageCenter(context));
    }

    /**
     * Setts categoryDAO
     * @param categoryDAO
     */
    private void setExpenseItemDAO(ICategoryDAO categoryDAO) {
        if (categoryDAO == null) {
            throw new ExpensesManagerRuntimeException("expenseItemDAO cannot be null in class BridgeJavaJS");
        }
        this.categoryDAO = categoryDAO;
    }

    /**
     * Returns ICategoryDAO object
     * @return ICategoryDAO object
     */
    public ICategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    /**
     * Setts ICategoryDAO object
     * @param categoryDAO the connection of category to the db
     */
    private void setCategoryDAO(CategoryDAO categoryDAO) {
        if (categoryDAO == null) {
            throw new ExpensesManagerRuntimeException("categoryDAO cannot be null in class BridgeJavaJS");
        }
        this.categoryDAO = categoryDAO;
    }

    /**
     * Returns Json converter object
     * @return Json converter object
     */
    public JsonConverter getJsonConverter() {
        return jsonConverter;
    }

    /**
     * Setts Json converter object
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
     * @return this context of the class
     */
    public Context getContext() {
        return context;
    }

    /**
     * Setting the context of the class
     * @param context this context of the class
     */
    public void setContext(Context context) {
        if (context == null) {
            throw new ExpensesManagerRuntimeException("context cannot be null in class BridgeJavaJS");
        }
        this.context = context;
    }

    /**
     * Returns JSMessageCenter object
     * @return JSMessageCenter object
     */
    public JSMessageCenter getJSMessageCenter() {
        return jsMessageCenter;
    }

    /**
     * Setts JSMessageCenter object
     * @param jsMessageCenter JSMessageCenter object, Represents a message that returns from Java side to Javascript
     */
    private void setJSMessageCenter(JSMessageCenter jsMessageCenter) {
        this.jsMessageCenter = jsMessageCenter;
    }

    /**
     * Updating category in db
     * @param category the category
     * @return If succeeded or not
     */
    @Override
    @android.webkit.JavascriptInterface
    public String updateCategory(String category) {
        StringBuilder message = new StringBuilder();
        JSMessageCenter.Status status;
        try {
            JSONObject jsonObject = jsonConverter.parseIntoJsonObject(category);
            Category categoryObj = jsonConverter.convertJsonToCategory(jsonObject);
            categoryDAO.updateLine(categoryObj);
            status = JSMessageCenter.Status.Working;
            message.insert(0,"succeed update Category");
        } catch (CategoryException ce) {
            status = JSMessageCenter.Status.Not_Working;
            message.insert(0,ce.getMessage());
        } catch (DbException | ConverterJsonException exception) {
            status = JSMessageCenter.Status.Not_Working;
            message.insert(0,"didn't succeed update Category, please fill the text fields correctly");
        } catch (ManagerExpensesException cte) {
            status = JSMessageCenter.Status.Not_Working;
            message.insert(0,cte.getMessage());
        }
        jsMessageCenter.setStatus(status);
        jsMessageCenter.setMsg(message.toString());
        return jsMessageCenter.toString();
    }

    /**
     * Return category
     * @param categoryId the category id
     * @return If succeeded the category and if not error message
     */
    @Override
    @android.webkit.JavascriptInterface
    public String getCategoryById(int categoryId) {
        try {
            String categoryStr;
            Category category = (Category) categoryDAO.getCategoryById(categoryId);
            JSONObject categoryObj = jsonConverter.convertCategoryToJson(category);
            categoryStr = categoryObj.toString();
            jsMessageCenter.setStatus(JSMessageCenter.Status.Working);
            jsMessageCenter.setMsg(categoryStr);
        } catch (CategoryException ce) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg(ce.getMessage());
        } catch (DbException | ConverterJsonException exception) {
            jsMessageCenter.setStatus(JSMessageCenter.Status.Not_Working);
            jsMessageCenter.setMsg("didnt succeed getting category by id");
        }
        return jsMessageCenter.toString();
    }


}
