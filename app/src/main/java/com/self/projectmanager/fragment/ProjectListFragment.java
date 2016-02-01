package com.self.projectmanager.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.self.projectmanager.R;
import com.self.projectmanager.activity.ProjectDetailActivity;
import com.self.projectmanager.db.SqliteHelper;

/**
 * Created by 攀礼 on 2016/1/7.
 */
public class ProjectListFragment extends ListFragment {

    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            return;
        }

        db = new SqliteHelper(getActivity(), "database.db", null, 1).getWritableDatabase();

        String sql = "select project_id _id, project_name, comment, project_starttime, (select count(*) from t_project_member where project_id = t.project_id) count from t_project t where status = '01'";

        Cursor cursor = db.rawQuery(sql, new String[]{});

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this.getActivity(), R.layout.project_item, cursor,
                new String[]{"project_name", "comment", "project_starttime", "count"},
                new int[]{R.id.item_project_name, R.id.item_project_comment, R.id.item_project_starttime, R.id.item_project_member_count},
                0);

        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProjectListFragment.this.getContext(), ProjectDetailActivity.class);

                intent.putExtra("project_id", id);

                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }
}
