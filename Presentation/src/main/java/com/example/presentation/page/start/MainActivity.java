package com.example.presentation.page.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.presentation.R;
import com.example.presentation.base.view.BaseActivity;
import com.example.presentation.userlist.UserListActivity;

/**
 * Main application screen. This is the app entry point.
 */
public class MainActivity extends BaseActivity {

    private Button btn_LoadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mapGUI();
    }

    /**
     * Maps the graphical user interface controls.
     */
    private void mapGUI() {
        btn_LoadData = (Button) findViewById(R.id.btn_LoadData);
        btn_LoadData.setOnClickListener(loadDataOnClickListener);
    }

    /**
     * Goes to the user list screen.
     */
    private void navigateToUserList() {
        Intent intentToLaunch = UserListActivity.getCallingIntent(this);
        startActivity(intentToLaunch);
    }

    private final View.OnClickListener loadDataOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            navigateToUserList();
        }
    };
}
