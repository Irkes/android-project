package lisk.calc.bd_lab;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import lisk.calc.R;


public class AddDialog extends Dialog {

    private EditText etTask;
    private EditText etDescription;
    private EditText etNote;

    public AddDialog(Context context) {
        super(context);
        setContentView(R.layout.dialog_add);
        Button btnSave = (Button) findViewById(R.id.save);
        etTask = (EditText) findViewById(R.id.etTask);
        etDescription = (EditText) findViewById(R.id.etDescriptio);
        etNote = (EditText) findViewById(R.id.etNote);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean canStart  = true;
                if (etDescription.getText().toString().isEmpty()) {
                    etDescription.setError("Empty!");
                    canStart = false;
                }
                if (etNote.getText().toString().isEmpty()) {
                    etNote.setError("Empty!");
                    canStart = false;
                }
                if (etTask.getText().toString().isEmpty()) {
                    etTask.setError("Empty!");
                    canStart = false;
                }
                if (canStart) {
                    try {
                        Dao<Task, Integer> dao = DataBaseFactory.getHelper().getTaskDAO();
                        Task task = new Task(etTask.getText().toString(), etDescription.getText().toString(),
                                etNote.getText().toString());

                        dao.create(task);
                        dismiss();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}
