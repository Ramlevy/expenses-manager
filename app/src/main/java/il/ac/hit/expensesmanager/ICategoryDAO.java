package il.ac.hit.expensesmanager;
import java.util.ArrayList;

import il.ac.hit.expensesmanager.Exceptions.CategoryException;
import il.ac.hit.expensesmanager.Exceptions.ConverterJsonException;
import il.ac.hit.expensesmanager.Exceptions.DbException;

/**
 * Interface that represents category data access object (connection to category table)
 */
public interface ICategoryDAO extends IDatabaseManager {

    /**
     * Returns Category by its ID from DB
     * @param id category id
     * @return Category by its ID from DB
     * @throws DbException
     * @throws ConverterJsonException
     * @throws CategoryException
     */
    Object getCategoryById(int id) throws DbException, ConverterJsonException, CategoryException;

    /**
     * Returns all categories from DB
     * @return All categories from DB
     * @throws DbException
     * @throws ConverterJsonException
     * @throws CategoryException
     */
    ArrayList<Category> getCategories() throws DbException, ConverterJsonException, CategoryException;
}
