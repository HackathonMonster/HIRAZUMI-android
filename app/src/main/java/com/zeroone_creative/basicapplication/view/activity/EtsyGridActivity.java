package com.zeroone_creative.basicapplication.view.activity;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import com.etsy.android.grid.StaggeredGridView;
import com.zeroone_creative.basicapplication.R;
import com.zeroone_creative.basicapplication.view.adapter.BasicAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_etsy_grid)
public class EtsyGridActivity extends Activity {

    @ViewById(R.id.etsy_stagger_gridview)
    StaggeredGridView mStaggerGridView;

    @AfterViews
    void onAfterView() {
        List<String> strList = new ArrayList<>();
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            strBuilder.append("Item" + String.valueOf(i));
            strBuilder.append(strBuilder.toString());
            strList.add(strBuilder.toString());
        }
        BasicAdapter adapter = new BasicAdapter(this);
        adapter.setContent(strList);
        mStaggerGridView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_etsy_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        */
        return super.onOptionsItemSelected(item);
    }
}
