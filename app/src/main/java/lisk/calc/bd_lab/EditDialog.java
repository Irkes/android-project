package lisk.calc.bd_lab;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import lisk.calc.R;
import lisk.calc.bd_lab.DataBaseFactory;
import lisk.calc.bd_lab.Task;


public class EditDialog extends Dialog {

    private EditText etTask;
    private EditText etDescription;
    private EditText etNote;

    public EditDialog(Context context, final Task task) {
        super(context);
        setContentView(R.layout.dialog_add);
        Button btnSave = (Button) findViewById(R.id.save);
        etTask = (EditText) findViewById(R.id.etTask);
        etDescription = (EditText) findViewById(R.id.etDescriptio);
        etNote = (EditText) findViewById(R.id.etNote);

        etTask.setText(task.getName());
        etNote.setText(task.getNotes());
        etDescription.setText(task.getDescription());

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
                        task.setName(etTask.getText().toString());
                        task.setDescription(etDescription.getText().toString());
                        task.setNotes(etNote.getText().toString());

                        dao.createOrUpdate(task);
                        dismiss();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}
