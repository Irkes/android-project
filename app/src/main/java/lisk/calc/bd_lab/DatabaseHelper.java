package lisk.calc.bd_lab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper  extends OrmLiteSqliteOpenHelper{

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME ="myappname.db";

    private static final int DATABASE_VERSION = 1;

    private Dao<Task, Integer> taskDao= null;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try
        {
            TableUtils.createTable(connectionSource, Task.class);
        }
        catch (SQLException e){}
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer){
        try{
            TableUtils.dropTable(connectionSource, Task.class, true);
            onCreate(db, connectionSource);
        }
        catch (SQLException e){
        }
    }

    public Dao<Task, Integer> getTaskDAO() throws SQLException{
        if(taskDao == null){
            taskDao = getDao(Task.class);
        }
        return taskDao;
    }

    @Override
    public void close(){
        super.close();
        taskDao = null;
    }
}