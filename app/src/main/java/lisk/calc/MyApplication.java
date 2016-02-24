package lisk.calc;

import android.app.Application;

import lisk.calc.bd_lab.DataBaseFactory;


public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        DataBaseFactory.setHelper(getApplicationContext());
    }
    @Override
    public void onTerminate() {
        DataBaseFactory.releaseHelper();
        super.onTerminate();
    }
}