package com.zeroone_creative.basicapplication.view.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.zeroone_creative.basicapplication.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_login)
public class LoginActivity extends ActionBarActivity {

    @Click(R.id.login_button_github)
    void selectGitHub() {
        PagerActivity_.intent(this).start();
    }

    @Click(R.id.login_button_twitter)
    void selectTwitter() {
        PagerActivity_.intent(this).start();
    }

    @Click(R.id.login_button_dribble)
    void selectDribble() {
        PagerActivity_.intent(this).start();
    }

}
