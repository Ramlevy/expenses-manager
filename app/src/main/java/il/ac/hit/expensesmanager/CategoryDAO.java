package il.ac.hit.expensesmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import il.ac.hit.expensesmanager.Exceptions.ManagerExpensesException;
import il.ac.hit.expensesmanager.Exceptions.ExpensesManagerRuntimeException;
import il.ac.hit.expensesmanager.Exceptions.CategoryException;
import il.ac.hit.expensesmanager.Exceptions.ConverterJsonException;
import il.ac.hit.expensesmanager.Exceptions.DbException;

/**
 *  Represents one the connection of category to the db
 */
public class CategoryDAO implements ICategoryDAO {
    private SQLiteDatabase mDatabase;
    private DBHandler mDbManage;
    private Context mContext;
    private SimpleDateFormat simpleDateFormat;

    /**
     * Constructor of the class
     * @param context context of the class
     */
    public CategoryDAO(Context context) throws ManagerExpensesException {
        this.setmContext(context);
        this.setSimpleDateFormat(new SimpleDateFormat(context.getResources().getString(R.string.date_format)));
        this.setmDbManage(DBHandler.getInstance(context));
        try {
            openDB();
        } catch (SQLException e) {
            throw new ManagerExpensesException("Failed to open DB please contact admin", e);
        }
    }

    /**
     * Returns the sqlitedb
     * @return the sqlitedb
     */
    public SQLiteDatabase getmDatabase() {
        return mDatabase;
    }

    /**
     * Setting the  sqlitedb
     * @param mDatabase the sqlitedb
     */
    public void setmDatabase(SQLiteDatabase mDatabase) {
        if (mDatabase == null) {
            throw new ExpensesManagerRuntimeException("mDataBase cannot be null in class CategoryDAO");
        }
        this.mDatabase = mDatabase;
    }

    /**
     * Returns the DBHandler
     * @return the DBHandler
     */
    public DBHandler getmDbManage() {
        return mDbManage;
    }

    /**
     * Setting the  DBHandler
     * @param mDbManage the dbHandler
     */
    public void setmDbManage(DBHandler mDbManage) {
        if (mDbManage == null) {
            throw new ExpensesManagerRuntimeException("mDbManage cannot be null in class CategoryDAO");
        }
        this.mDbManage = mDbManage;
    }

    /**
     * Returns the context of the class
     * @return the context of the class
     */
    public Context getmContext() {
        return mContext;
    }

    /**
     * Setting the context of the class
     * @param mContext the context of the class
     */
    public void setmContext(Context mContext) {
        if (mContext == null) {
            throw new ExpensesManagerRuntimeException("mContext cannot be null in class CategoryDAO");
        }
        this.mContext = mContext;
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
        if (simpleDateFormat == null) {
            throw new ExpensesManagerRuntimeException("simpleDateFormat cannot be null in class CategoryDAO");
        }
        this.simpleDateFormat = simpleDateFormat;
    }

    /**
     *  Insert object to db
     * @param object expenseItem or category
     * @throws DbException
     */
    @Override
    public void insertNewLine(Object object) throws DbException {
        Category category = (Category) object;
        ContentValues values = new ContentValues();
        values.put(DBHandler.CATEGORY_NAME, category.getName());
        values.put(mDbManage.CATEGORY_ExpensesLimit, category.getExpensesLimit());
        values.put(mDbManage.CATEGORY_ExpensePeriod, category.getExpensePeriod().name());
        values.put(mDbManage.CATEGORY_StartExpensesDate, simpleDateFormat.format(category.getStartExpensesDate()));
        try {
        mDatabase.insert(DBHandler.TABLE_CATEGORY, null, values);
        } catch (SQLiteException e) {
            throw new DbException(/*"failed to update category in db"*/);
        }
    }

    /**
     * Update existing row in db
     * @param object expenseItem or category
     * @throws DbException
     */
    @Override
    public void updateLine(Object object) throws DbException {
        Category category = (Category) object;
        ContentValues values = new ContentValues();
        values.put(mDbManage.CATEGORY_ExpensesLimit, category.getExpensesLimit());
        values.put(mDbManage.CATEGORY_ExpensePeriod, category.getExpensePeriod().name());
        values.put(mDbManage.CATEGORY_StartExpensesDate, simpleDateFormat.format(category.getStartExpensesDate()));
        try {
            mDatabase.update(mDbManage.TABLE_CATEGORY, values, mDbManage.CATEGORY_ID + " = " + category.getId(), null);
        } catch (SQLiteException e) {
            throw new DbException(/*"failed to update category in db"*/);
        }
    }

    /**
     * Deleting existing row in db
     * @param categoryId category id
     */
    @Override
    public void deleteLine(String categoryId) {
        mDatabase.delete(mDbManage.TABLE_EXPENSE_ITEM, mDbManage.EXPENSE_ITEM_ID + " = " + categoryId, null);
    }

    /**
     * Returns Category by its ID from DB
     * @param id category id
     * @return Category by its ID from DB
     * @throws DbException
     * @throws ConverterJsonException
     * @throws CategoryException
     */
    @Override
    public Object getCategoryById(int id) throws DbException, ConverterJsonException, CategoryException {
        Cursor cursor = null;
        String name;
        Category.ExpensePeriod expensePeriod;
        int expensesLimit;
        Date startExpensesDate;
        Category category = null;
        try {
            cursor = mDatabase.rawQuery("SELECT * FROM " + mDbManage.TABLE_CATEGORY + " WHERE " + mDbManage.CATEGORY_ID + " = " + id, null);
            cursor.moveToFirst();
            name = cursor.getString(1);
            expensesLimit = cursor.getInt(2);
            expensePeriod = Enum.valueOf(Category.ExpensePeriod.class, cursor.getString(3));
            startExpensesDate = simpleDateFormat.parse(cursor.getString(4));

            category = new Category(id, name, expensesLimit, expensePeriod, startExpensesDate);
        } catch (SQLiteException e) {
            throw new DbException("failed to retrieve category from db",e);
        } catch (ParseException | CursorIndexOutOfBoundsException e) {
            throw new ConverterJsonException("failed to convert from db",e);
        } finally {
            cursor.close();
        }
        return category;
    }

    /**
     * opens the db for write and read
     * @throws SQLException
     */
    @Override
    public void openDB() throws SQLException {
        mDatabase = mDbManage.getWritableDatabase();
    }

    /**
     * close the db
     * @throws SQLException
     */
    //TODO: use this function
    @Override
    public void closeDB() throws SQLException {
        mDbManage.close();
    }

    /**
     * Returns all categories from DB
     * @return All categories from DB
     * @throws DbException
     * @throws ConverterJsonException
     * @throws CategoryException
     */
    @Override
    public ArrayList<Category> getCategories() throws DbException, ConverterJsonException, CategoryException {
        ArrayList<Category> listCategory = new ArrayList<Category>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery("SELECT * FROM " + mDbManage.TABLE_CATEGORY, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int expensesLimit = cursor.getInt(2);
                Category.ExpensePeriod expensePeriod = Enum.valueOf(Category.ExpensePeriod.class, cursor.getString(3));
                Date startExpensesDate = simpleDateFormat.parse(cursor.getString(4));
                Category category = new Category(id, name, expensesLimit, expensePeriod, startExpensesDate);
                listCategory.add(category);
                cursor.moveToNext();
            }
        } catch (SQLiteException e) {
            throw new DbException("failed to retrive category list",e);
        } catch (ParseException | CursorIndexOutOfBoundsException e) {
            throw new ConverterJsonException("failed to convert category from db",e);
        } finally {
            cursor.close();
        }
        return listCategory;
    }


}
