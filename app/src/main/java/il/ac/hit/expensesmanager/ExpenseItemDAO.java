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

import il.ac.hit.expensesmanager.Exceptions.ManagerExpensesException;
import il.ac.hit.expensesmanager.Exceptions.ExpensesManagerRuntimeException;
import il.ac.hit.expensesmanager.Exceptions.ConverterJsonException;
import il.ac.hit.expensesmanager.Exceptions.ExpenseItemException;
import il.ac.hit.expensesmanager.Exceptions.DbException;
import il.ac.hit.expensesmanager.Exceptions.EmptyFieldsException;

/**
 *  Represents one the connection of expenseItem to the db
 */
public class ExpenseItemDAO implements IExpenseItemDAO {
    private SQLiteDatabase mDatabase;
    private DBHandler mDbManage;
    private Context mContext;
    private SimpleDateFormat simpleDateFormat;

    /**
     * Constructor of the class
     * @param context context of the class
     */
    public ExpenseItemDAO(Context context) throws ManagerExpensesException {
        this.mContext = context;
        this.simpleDateFormat = new SimpleDateFormat(context.getResources().getString(R.string.date_format));
        mDbManage = DBHandler.getInstance(context);
        try {
            openDB();
        } catch (SQLException e) {
            throw new ManagerExpensesException(/*"Cannot open the DB*/);
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
            throw new ExpensesManagerRuntimeException("mDataBase cannot be null in class ExpenseItemDAO");
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
            throw new ExpensesManagerRuntimeException("mDbManage cannot be null in class ExpenseItemDAO");
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
            throw new ExpensesManagerRuntimeException("mContext cannot be null in class ExpenseItemDAO");
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
            throw new ExpensesManagerRuntimeException("simpleDateFormat cannot be null in class ExpenseItemDAO");
        }
        this.simpleDateFormat = simpleDateFormat;
    }

    /**
     * Opens the db for write and read
     * @throws SQLException
     */
    @Override
    public void openDB() throws SQLException {
        mDatabase = mDbManage.getWritableDatabase();
    }

    /**
     * Close the db
     * @throws SQLException
     */
    @Override
    public void closeDB() throws SQLException {
        mDbManage.close();
    }

    /**
     * Insert object to db
     * @param object expenseItem or category
     * @throws DbException
     */
    @Override
    public void insertNewLine(Object object) throws DbException {
        ExpenseItem expenseItem = (ExpenseItem) object;
        ContentValues values = new ContentValues();
        values.put(mDbManage.EXPENSE_NAME, expenseItem.getName());
        values.put(mDbManage.EXPENSE_COMMENT, expenseItem.getComment());
        values.put(mDbManage.EXPENSE_VALUE, expenseItem.getExpenseValue());
        values.put(mDbManage.EXPENSE_CATEGORY_ID, expenseItem.getCategoryID());
        values.put(mDbManage.EXPENSE_ACTION_DATE, simpleDateFormat.format(expenseItem.getActionDate()));
        try {
            mDatabase.insert(mDbManage.TABLE_EXPENSE_ITEM, null, values);
        }
            catch (SQLiteException e)
            {
                throw new DbException("failed to insert expenseItem in db",e);
            }
    }

    /**
     * Update existing row in db
     * @param object expenseItem or category
     * @throws DbException
     */
    @Override
    public void updateLine(Object object) throws DbException {
        ExpenseItem expenseItem = (ExpenseItem) object;
        ContentValues values = new ContentValues();
        values.put(mDbManage.EXPENSE_NAME, expenseItem.getName());
        values.put(mDbManage.EXPENSE_COMMENT, expenseItem.getComment());
        values.put(mDbManage.EXPENSE_VALUE, expenseItem.getExpenseValue());
        values.put(mDbManage.EXPENSE_CATEGORY_ID, expenseItem.getCategoryID());
        values.put(mDbManage.EXPENSE_ACTION_DATE, simpleDateFormat.format(expenseItem.getActionDate()));
        try {
            mDatabase.update(mDbManage.TABLE_EXPENSE_ITEM, values, mDbManage.EXPENSE_ITEM_ID + " = " + expenseItem.getId(), null);
        }
        catch (SQLiteException e)
        {
            throw new DbException("failed to update expenseItem in db",e);
        }
    }

    /**
     * Deleting existing row in db
     * @param expenseItemId expenseItem Id
     */
    @Override
    public void deleteLine(String expenseItemId) {
        mDatabase.delete(mDbManage.TABLE_EXPENSE_ITEM, mDbManage.EXPENSE_ITEM_ID + " = " + expenseItemId, null);
    }

    /**
     * Returns Expense Item by its ID from DB
     * @param id Expense Item id
     * @return Expense Item by its ID from DB
     * @throws DbException
     * @throws ConverterJsonException
     * @throws ExpenseItemException
     */
    @Override
    public Object getExpenseItemById(int id) throws DbException, ConverterJsonException, ExpenseItemException {
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + mDbManage.TABLE_EXPENSE_ITEM + " WHERE " + mDbManage.EXPENSE_ITEM_ID + " = " + id, null);
        cursor.moveToFirst();
        ExpenseItem expenseItem = null;
        try {
            expenseItem = new ExpenseItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getFloat(3), cursor.getInt(4), simpleDateFormat.parse(cursor.getString(5)));
        }catch (SQLiteException e) {
            throw new DbException("failed to retrieve expenseItem from db",e);
        }catch (ParseException | CursorIndexOutOfBoundsException e){
            throw new ConverterJsonException("failed to convert from db",e);
        }
        finally{
            cursor.close();
        }
        return expenseItem;
    }

    /**
     * Returns Expense Items with date between dates
     * @param dateFrom from this date
     * @param dateTo  to this date
     * @return Expense Items with date between dates
     * @throws DbException
     * @throws ConverterJsonException
     * @throws ExpenseItemException
     */
    @Override
    public ArrayList<ExpenseItem> getExpenseItemsBetweenDates(String dateFrom, String dateTo) throws DbException, ConverterJsonException, ExpenseItemException, EmptyFieldsException {
        ArrayList<ExpenseItem> listExpenseItems = new ArrayList<ExpenseItem>();
        Cursor cursor = null;
        if(dateFrom.equals("")|| dateTo.equals(""))
        {
            throw new EmptyFieldsException();
        }
        try {
            cursor = mDatabase.rawQuery("SELECT * FROM " + mDbManage.TABLE_EXPENSE_ITEM + " WHERE " + mDbManage.EXPENSE_ACTION_DATE + " BETWEEN  '" + dateFrom + "'"
                    + " AND '" + dateTo + "'", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ExpenseItem expenseItem = new ExpenseItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getFloat(3), cursor.getInt(4), simpleDateFormat.parse(cursor.getString(5)));
                listExpenseItems.add(expenseItem);
                cursor.moveToNext();
            }

        } catch (SQLiteException e) {
            throw new DbException("failed to retrive ExpenseItems Between Dates list",e);
        }catch (ParseException | CursorIndexOutOfBoundsException e){
            throw new ConverterJsonException("failed to convert from db",e);
        }
        finally{
            cursor.close();
        }
        return listExpenseItems;
    }

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
    @Override
    public ArrayList<ExpenseItem> getExpenseItemsBetweenDatesInSpecificCategory(String dateFrom, String dateTo, String categoryId) throws DbException, ConverterJsonException, ExpenseItemException, EmptyFieldsException {
        ArrayList<ExpenseItem> listExpenseItems = new ArrayList<ExpenseItem>();
        if(dateFrom.equals("")|| dateTo.equals(""))
        {
            throw new EmptyFieldsException();
        }
        Cursor cursor = null;
        try {

            cursor = mDatabase.rawQuery("SELECT * FROM " + mDbManage.TABLE_EXPENSE_ITEM + " WHERE " + mDbManage.EXPENSE_CATEGORY_ID + " = " + categoryId + " AND " +
            mDbManage.EXPENSE_ACTION_DATE + " BETWEEN  '" + dateFrom + "'" + " AND '" + dateTo + "'", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ExpenseItem expenseItem = new ExpenseItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getFloat(3), cursor.getInt(4), simpleDateFormat.parse(cursor.getString(5)));
                listExpenseItems.add(expenseItem);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            throw new DbException("failed to retrive ExpenseItems list Between Dates in specific Category ",e);
        }catch (ParseException | CursorIndexOutOfBoundsException e){
            throw new ConverterJsonException("failed to convert from db",e);
        }
        finally{
            cursor.close();
        }
        return listExpenseItems;
    }

    /**
     * Returns Expense Items of specific category
     * @param categoryId specific category id
     * @return Expense Items of specific category
     * @throws DbException
     * @throws ConverterJsonException
     * @throws ExpenseItemException
     */
    @Override
    public ArrayList<ExpenseItem> getExpenseItemsByCategory(int categoryId) throws DbException, ConverterJsonException, ExpenseItemException{
        ArrayList<ExpenseItem> listExpenseItems = new ArrayList<ExpenseItem>();
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery("SELECT * FROM " + mDbManage.TABLE_EXPENSE_ITEM + " WHERE " + mDbManage.EXPENSE_CATEGORY_ID + " = " + categoryId, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ExpenseItem expenseItem = new ExpenseItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getFloat(3), cursor.getInt(4), simpleDateFormat.parse(cursor.getString(5)));
                listExpenseItems.add(expenseItem);

                cursor.moveToNext();
            }
        }
        catch (SQLiteException  e) {
            throw new DbException("failed to retrive ExpenseItems in specific Category",e);
        }catch (ParseException | CursorIndexOutOfBoundsException e){
            throw new ConverterJsonException("failed to convert from db",e);
        }
        finally{
            cursor.close();
        }
        return listExpenseItems;
    }

    /**
     * Returns sum of Expense Items price of specific category between dates excluding a specific Expense Item price
     * @param categoryID specific category id
     * @param dateFrom from this date
     * @param dateTo to this date
     * @param expenseItemId specific Expense Item id
     * @return sum of Expense Items price of specific category between dates
     * @throws DbException
     */
    @Override
    public float getExpenseItemsPriceBetweenDates(int categoryID, String dateFrom, String dateTo, int expenseItemId) throws DbException, EmptyFieldsException {
        float price = 0;
        if(dateFrom.equals("")|| dateTo.equals(""))
        {
            throw new EmptyFieldsException();
        }
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery("SELECT SUM(" + mDbManage.EXPENSE_VALUE + ") FROM " + mDbManage.TABLE_EXPENSE_ITEM +
                    " WHERE " + mDbManage.EXPENSE_CATEGORY_ID + " = " + categoryID +
                    " AND " + mDbManage.EXPENSE_ACTION_DATE + " BETWEEN  '" + dateFrom + "'" + " AND '" + dateTo + "'" +
                    " AND " + mDbManage.EXPENSE_ITEM_ID + " IS NOT " + expenseItemId , null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                price = cursor.getFloat(0);
                cursor.moveToNext();
            }
        }catch (SQLiteException | CursorIndexOutOfBoundsException e){
            throw new DbException("failed to retrive ExpenseItems Price Between Dates in specific Category",e);
        }
        finally{
            cursor.close();
        }
        return price;
    }

    /**
     * Returns sum of Expense Items price of specific category
     * @param categoryID specific category id
     * @return Sum of Expense Items price of specific category
     * @throws DbException
     */
    @Override
    public float getExpenseItemsPriceInSpecificCategory(int categoryID) throws DbException {
        float price = 0;
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery("SELECT sum(" + DBHandler.EXPENSE_VALUE + ") FROM " + mDbManage.TABLE_EXPENSE_ITEM +
                    " WHERE " + DBHandler.EXPENSE_CATEGORY_ID + " = " + categoryID, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                price = cursor.getFloat(0);

                cursor.moveToNext();
            }
        }
        catch (SQLiteException | CursorIndexOutOfBoundsException e){
            throw new DbException("failed to retrive ExpenseItems Price in specific Category",e);
        }
        finally{
            cursor.close();
        }
        return price;
    }

    /**
     * Returns total sum price of all Expense Items
     * @return total sum price of all Expense Items
     * @throws DbException
     */
    @Override
    public float getAllExpenseItemsPrice() throws DbException {
        float price = 0;
        Cursor cursor = null;
        try {
            cursor = mDatabase.rawQuery("SELECT SUM(" + DBHandler.EXPENSE_VALUE + ") FROM " + mDbManage.TABLE_EXPENSE_ITEM, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                price = cursor.getFloat(0);

                cursor.moveToNext();
            }
        }catch (SQLiteException | CursorIndexOutOfBoundsException e){
            throw new DbException("failed to retrive all ExpenseItems Price",e);
        }
        finally{
            cursor.close();
        }
        return price;
    }



}
