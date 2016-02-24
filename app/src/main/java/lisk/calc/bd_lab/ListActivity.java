package lisk.calc.bd_lab;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import lisk.calc.R;


public class ListActivity extends Activity {

    ListView listView;
    Activity activity;

    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.list_activity);
        this.activity = this;
        listView = (ListView) findViewById(R.id.listView);

        update();
        Button btnAdd= (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialog dialog = new AddDialog(activity);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        update();
                    }
                });
                dialog.show();
            }
        });
    }
    private void update(){
        Dao<Task, Integer>  dao = null;
        try {
            dao = DataBaseFactory.getHelper().getTaskDAO();

            List<Task> list = dao.queryForAll();
            TaskAdapter adapter = new TaskAdapter(this, R.layout.list_item, list);
            listView.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
