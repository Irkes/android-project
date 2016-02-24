package lisk.calc.bd_lab;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lisk.calc.R;


public class TaskAdapter extends ArrayAdapter<Task> {

    private List<Task> list = new ArrayList<>();
    private Context mContext;

    public TaskAdapter(Context context, int resource, List<Task> list) {
        super(context, resource);
        this.list = list;
        mContext = context;
    }

    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate( R.layout.list_item, null );
        }
        Button btnEdit = (Button) convertView.findViewById(R.id.edit);

        btnEdit.setTag(list.get(position));
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialog dialog = new EditDialog(mContext, (Task)v.getTag());
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        update();
                    }
                });
            }
        });

        Button btnDelete = (Button) convertView.findViewById(R.id.delete);
        btnDelete.setTag(list.get(position));
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dao<Task, Integer> dao = DataBaseFactory.getHelper().getTaskDAO();

                    dao.delete((Task)v.getTag());
                    update();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        TextView tvTask = (TextView) convertView.findViewById(R.id.tvTask);
        tvTask.setText(list.get(position).getName());

        TextView tvNote = (TextView) convertView.findViewById(R.id.tvNote);
        tvNote.setText(list.get(position).getNotes());

        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
        tvDescription.setText(list.get(position).getDescription());

        final LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.linear);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
        return convertView;
    }

    private void update(){
        Dao<Task, Integer> dao = null;
        try {
            dao = DataBaseFactory.getHelper().getTaskDAO();

            list = dao.queryForAll();
            notifyDataSetChanged();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
