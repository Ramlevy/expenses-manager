package il.ac.hit.expensesmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import il.ac.hit.expensesmanager.Exceptions.ExpensesManagerRuntimeException;

/**
 * Represents the DBHandler that creates two tables of category and expenseItems
 */
public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_EXPENSE_ITEM = "ExpenseItem";
    public static final String EXPENSE_ITEM_ID = BaseColumns._ID;
    public static final String EXPENSE_NAME = "name";
    public static final String EXPENSE_COMMENT = "comment";
    public static final String EXPENSE_VALUE = "value";
    public static final String EXPENSE_CATEGORY_ID = "categoryId";
    public static final String EXPENSE_ACTION_DATE = "actionDate";
    private static final String TABLE_EXPENSE_ITEM_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_EXPENSE_ITEM + " ( " + EXPENSE_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + EXPENSE_NAME + " TEXT, " + EXPENSE_COMMENT + " TEXT," + EXPENSE_VALUE + " REAL, " + EXPENSE_CATEGORY_ID + " INTEGER," + EXPENSE_ACTION_DATE + " TEXT)";

    public static final String TABLE_CATEGORY = "category";
    public static final String CATEGORY_ID = BaseColumns._ID;
    public static final String CATEGORY_NAME = "name";
    public static final String CATEGORY_ExpensesLimit = "expensesLimit";
    public static final String CATEGORY_ExpensePeriod = "expensePeriod";
    public static final String CATEGORY_StartExpensesDate = "startExpensesDate";
    private static final String TABLE_CATEGORY_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORY + " ( " + CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CATEGORY_NAME + " TEXT, " + CATEGORY_ExpensesLimit + " INTEGER, " + CATEGORY_ExpensePeriod + " TEXT, " + CATEGORY_StartExpensesDate + " TEXT)";
    private static final String DATABASE_NAME = "ItemDB"; // TO BE CHANGED !


    private static DBHandler instance = null;
    private SimpleDateFormat simpleDateFormat;

    /**
     * protected Constructor of the class
     * @param context context of the class
     */
    protected DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.setSimpleDateFormat(new SimpleDateFormat(context.getResources().getString(R.string.date_format)));
    }

    /**
     * Singleton Constructor
     * @param context context of the class
     * @return instance of the class
     */
    public static DBHandler getInstance(Context context) {
        if (DBHandler.instance == null) {
            setInstance(new DBHandler(context));
        }
        return instance;
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
            throw new ExpensesManagerRuntimeException("simpleDateFormat cannot be null in DataBaseManager Class");
        }
        this.simpleDateFormat = simpleDateFormat;
    }

    /**
     * Returns instance of the class
     * @return instance of the class
     */
    public static DBHandler getInstance() {
        return instance;
    }

    /**
     * Setting the instance of the class
     * @param instance of the class
     */
    private static void setInstance(DBHandler instance) {
        if (instance == null) {
            throw new ExpensesManagerRuntimeException("instance cannot be null in DataBaseManager Class");
        }
        DBHandler.instance = instance;
    }

    /**
     * Creating the two tables of expenseItems and category and putting some values to the category table
     * @param db the sqlitedb
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_EXPENSE_ITEM_CREATE);
        db.execSQL(TABLE_CATEGORY_CREATE);

        String DateOfToday = simpleDateFormat.format(Calendar.getInstance().getTime());
        ContentValues values = new ContentValues();
        values.put(DBHandler.CATEGORY_NAME, "Food");
        values.put(DBHandler.CATEGORY_ExpensesLimit, 100);
        values.put(DBHandler.CATEGORY_ExpensePeriod, Category.ExpensePeriod.Monthly.name());
        values.put(DBHandler.CATEGORY_StartExpensesDate, DateOfToday);
        db.insert(DBHandler.TABLE_CATEGORY, null, values);

        values.put(DBHandler.CATEGORY_NAME, "Clothing");
        values.put(DBHandler.CATEGORY_ExpensesLimit, 100);
        values.put(DBHandler.CATEGORY_ExpensePeriod, Category.ExpensePeriod.Monthly.name());
        values.put(DBHandler.CATEGORY_StartExpensesDate, DateOfToday);
        db.insert(DBHandler.TABLE_CATEGORY, null, values);

        values.put(DBHandler.CATEGORY_NAME, "Medical");
        values.put(DBHandler.CATEGORY_ExpensesLimit, 100);
        values.put(DBHandler.CATEGORY_ExpensePeriod, Category.ExpensePeriod.Monthly.name());
        values.put(DBHandler.CATEGORY_StartExpensesDate, DateOfToday);
        db.insert(DBHandler.TABLE_CATEGORY, null, values);

        values.put(DBHandler.CATEGORY_NAME, "Transportation");
        values.put(DBHandler.CATEGORY_ExpensesLimit, 100);
        values.put(DBHandler.CATEGORY_ExpensePeriod, Category.ExpensePeriod.Monthly.name());
        values.put(DBHandler.CATEGORY_StartExpensesDate, DateOfToday);
        db.insert(DBHandler.TABLE_CATEGORY, null, values);

        values.put(DBHandler.CATEGORY_NAME, "Utilities");
        values.put(DBHandler.CATEGORY_ExpensesLimit, 100);
        values.put(DBHandler.CATEGORY_ExpensePeriod, Category.ExpensePeriod.Monthly.name());
        values.put(DBHandler.CATEGORY_StartExpensesDate, DateOfToday);
        db.insert(DBHandler.TABLE_CATEGORY, null, values);

        values.put(DBHandler.CATEGORY_NAME, "Other");
        values.put(DBHandler.CATEGORY_ExpensesLimit, 100);
        values.put(DBHandler.CATEGORY_ExpensePeriod, Category.ExpensePeriod.Monthly.name());
        values.put(DBHandler.CATEGORY_StartExpensesDate, DateOfToday);
        db.insert(DBHandler.TABLE_CATEGORY, null, values);
    }

    /**
     * When Upgrade occurs
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
