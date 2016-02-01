package com.self.projectmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.self.projectmanager.model.Identifiable;
import com.self.projectmanager.utils.BeanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 攀礼 on 2016/1/16.
 */
public class ListAdapter<T extends Identifiable> extends ArrayAdapter<T> {

    private LayoutInflater inflater;

    private Map<Object, Boolean> checkedMap;

    private String[] mFrom;
    private int[] mTo;
    private Object checkViewId;
    private int layout;

    public ListAdapter(Context context, int resource, List<T> objects,
                       List<? extends Object> defaultCheckedItems,
                       String[] from, int[] to, Object checkViewId) {
        super(context, resource, objects);

        inflater = LayoutInflater.from(context);

        checkedMap = new HashMap<>();

        for (T obj : objects) {
            checkedMap.put(obj.getId(), false);
        }

        if (defaultCheckedItems != null) {
            for (Object item : defaultCheckedItems) {
                checkedMap.put(item, true);
            }
        }

        this.layout = resource;
        this.mFrom = from;
        this.mTo = to;
        this.checkViewId = checkViewId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        T m = getItem(position);

        for (int i = 0; i < mFrom.length; i++) {
            String val = BeanUtils.getProperty(m, mFrom[i]);
            ((TextView) convertView.findViewById(mTo[i])).setText(val);
        }

        if (checkViewId != null) {
            CheckBox cb = (CheckBox) convertView.findViewById((Integer) checkViewId);
            cb.setChecked(isChecked(m.getId()));
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(getItem(position).getId().toString());
    }

    public boolean isChecked(Object itemId) {
        Boolean checked = checkedMap.get(itemId);

        return checked != null && checked;
    }

    public void check(Object itemId) {
        boolean checked = isChecked(itemId);

        checkedMap.put(itemId, !checked);

        notifyDataSetChanged();
    }

    public void checkAll() {
        for (Object id : checkedMap.keySet()) {
            checkedMap.put(id, true);
        }

        notifyDataSetChanged();
    }

    public void uncheck() {
        for (Object id : checkedMap.keySet()) {
            checkedMap.put(id, !checkedMap.get(id));
        }

        notifyDataSetChanged();
    }

    public void decheck() {
        for (Object id : checkedMap.keySet()) {
            checkedMap.put(id, false);
        }

        notifyDataSetChanged();
    }
}