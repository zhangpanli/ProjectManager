package com.self.projectmanager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.self.projectmanager.R;

/**
 * Created by 攀礼 on 2016/1/9.
 */
public class ProjectMainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("", String.valueOf(savedInstanceState));
        return inflater.inflate(R.layout.project_main, container, false);
    }

    @Override
    public void onDestroyView() {

        FragmentManager fm = getChildFragmentManager();
        if (fm != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : fm.getFragments()) {
                ft.remove(f);
            }
            ft.commitAllowingStateLoss();
        }
        super.onDestroyView();
    }
}
