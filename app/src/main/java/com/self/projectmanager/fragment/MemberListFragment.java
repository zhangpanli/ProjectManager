package com.self.projectmanager.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

/**
 * Created by 攀礼 on 2016/1/7.
 */
public class MemberListFragment extends ListFragment {

    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new SqliteHelper(getActivity(), "database.db", null, 1).getWritableDatabase();

        String sql = "select member_id _id, member_name, '('||member_name_pinyin||')' member_name_pinyin, comment, (select count(*) from t_project_member where member_id = t.member_id) count from t_member t where status = '01'";
        Cursor cursor = db.rawQuery(sql, new String[]{});

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this.getActivity(), R.layout.member_item, cursor,
                new String[]{"member_name", "member_name_pinyin", "comment", "count"},
                new int[]{R.id.item_member_name, R.id.item_member_name_pinyin, R.id.item_member_comment, R.id.item_member_project_count},
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
                Intent intent = new Intent(MemberListFragment.this.getContext(), MemberDetailActivity.class);

                intent.putExtra("member_id", id);

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
