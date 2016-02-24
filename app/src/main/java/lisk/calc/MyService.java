package lisk.calc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MyService extends Service
{
    private static int dollar = 15000;

    public static boolean isStop() {
        return stop;
    }

    private static boolean stop = false;
    private static DataSetChanged mListener;
    final String LOG_TAG = "myLogs";


    public static int getDollar()
    {
        return dollar;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    public static void setListener(DataSetChanged listener){
        mListener = listener;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(LOG_TAG, "onStartCommand");
        stop = false;
        update();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        stop = true;
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(LOG_TAG, "onBind");
        return null;
    }

    void update()
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                while(!stop)
                {
                    if (dollar <= 20000) {
                        dollar = dollar + (int) (Math.random() * 5001);
                    } else {
                        dollar = dollar - (int) (Math.random() * 1001);
                    }
                    Log.d(LOG_TAG, "dollar = " + dollar);
                    if (mListener != null) {
                        mListener.onDataChange(dollar);
                    }
                    try
                    {
                        TimeUnit.SECONDS.sleep(2);
                    }
                    catch (InterruptedException ignored) {}
                }
            }
        }).start();
    }



















    public static int getQuotes(){
        return dollar;
    }
}






