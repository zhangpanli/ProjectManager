package com.self.projectmanager.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.self.projectmanager.R;
import com.self.projectmanager.activity.MemberDetailActivity;
import com.self.projectmanager.db.SqliteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 攀礼 on 2016/1/7.
 */
public class AdvanceRecordListFragment extends ListFragment {

    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long project_id = getActivity().getIntent().getLongExtra("project_id", -1);
        long member_id = getActivity().getIntent().getLongExtra("member_id", -1);

        List<String> args = new ArrayList<>();

        db = new SqliteHelper(getActivity(), "database.db", null, 1).getWritableDatabase();

        String sql = "select advance_id _id, advance_date, balance, comment from t_advance_record t where status = '01'";

        if (project_id != -1) {
            sql += "  and project_id = ?";
            args.add(project_id + "");
        }

        if (member_id != -1) {
            sql += "  and member_id = ?";
            args.add(member_id + "");
        }

        Cursor cursor = db.rawQuery(sql, new String[]{});

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this.getActivity(), R.layout.advance_item, cursor,
                new String[]{"advance_date", "balance", "comment"},
                new int[]{R.id.item_advance_date, R.id.item_balance, R.id.item_comment},
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
        lv.setBackgroundColor(Color.WHITE);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AdvanceRecordListFragment.this.getContext(), MemberDetailActivity.class);

                intent.putExtra("id", id);

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
