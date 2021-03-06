package com.password.manager;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.password.manager.core.User;
import com.password.manager.core.brut.force.prevention.LockService;
import com.password.manager.core.handler.PasswordListHandler;
import com.password.manager.gui.helper.AlertDialogHelper;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.accent_material_light));

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_layout_fragment_to_replace, new LoginFragment())
                .commit();

        Intent intent = new Intent(this, LockService.class);
        startService(intent);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            onOrientationChanged();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            onOrientationChanged();
        }
    }

    private void onOrientationChanged() {
        if (User.isLoggedIn() && PasswordListHandler.isLoggedIn()) // Both should ALWAYS be true
        {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_layout_fragment_to_replace, new PasswordListFragment())
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onBackPressed() {
        if (User.isLoggedIn() && PasswordListHandler.isLoggedIn()) /* Both should ALWAYS be true */ {
            logout();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        logout();
        super.onPause();
    }

    public void logout() {
        User.logout();
        PasswordListHandler.logout();

        AlertDialogHelper.close();

        getFragmentManager()
                .beginTransaction()
                        //.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .replace(R.id.main_layout_fragment_to_replace, new LoginFragment())
                .commit();
    }

}
