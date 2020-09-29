package il.ac.hit.expensesmanager;


import java.sql.SQLException;

import il.ac.hit.expensesmanager.Exceptions.ManagerExpensesException;
import il.ac.hit.expensesmanager.Exceptions.DbException;

/**
 * Interface that represents the connection to the Database
 */
public interface IDatabaseManager {

    /**
     *  Insert object to db
     * @param object expenseItem or category
     * @throws DbException
     */
    void insertNewLine(Object object) throws DbException, ManagerExpensesException;

    /**
     * Update existing row in db
     * @param object expenseItem or category
     * @throws DbException
     */
    void updateLine(Object object) throws DbException, ManagerExpensesException;

    /**
     * Deleting existing row in db
     * @param id of expenseItem or category
     */
    void deleteLine(String id);

    /**
     * Opens the db for write and read
     * @throws SQLException
     */
    void openDB()throws SQLException;

    /**
     * Close the db
     * @throws SQLException
     */
    void closeDB()throws SQLException;

}