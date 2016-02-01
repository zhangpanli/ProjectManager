package com.self.projectmanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by 攀礼 on 2016/1/9.
 */
public class PopupActivity extends Activity {

    private Fragment fragment;

    public PopupActivity(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            //getFragmentManager().beginTransaction()
            //        .add(android.R.id.content, details).commit();
        }
    }
}
