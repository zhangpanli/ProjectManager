package com.self.projectmanager.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.self.projectmanager.R;
import com.self.projectmanager.db.SqliteHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 攀礼 on 2016/1/10.
 */
public class ProjectDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.project_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        long project_id = getActivity().getIntent().getLongExtra("project_id", -1);

        Cursor c = new SqliteHelper(getActivity(), "database.db", null, 1).getWritableDatabase()
                .rawQuery("select * from t_project where project_id = ?", new String[]{String.valueOf(project_id)});

        if (c.moveToNext()) {
            ((EditText) getActivity().findViewById(R.id.txt_project_name)).setText(c.getString(c.getColumnIndex("project_name")));

            long t = c.getLong(c.getColumnIndex("project_starttime"));

            ((EditText) getActivity().findViewById(R.id.txt_project_starttime)).setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(t)));

            ((EditText) getActivity().findViewById(R.id.txt_project_comment)).setText(c.getString(c.getColumnIndex("comment")));
        }
    }
}
