package il.ac.hit.expensesmanager;

/**
 * Interface that represents a settings bridge between JavaScript and Java page
 */
public interface ISettingsBridgeJavaJS {

    /**
     * Updating category in db
     * @param category the category
     * @return If succeeded or not
     */
    String updateCategory(String category);

    /**
     * Return category
     * @param categoryId the category id
     * @return If succeeded the category and if not error message
     */
    String getCategoryById(int categoryId);
}
