package com.framgia.movie06.ui.activity;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import com.framgia.movie06.R;

public class SearchActivity extends AppCompatActivity {
    public static final String SEARCH = "Search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        setTitle(SEARCH);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        return super.onCreateOptionsMenu(menu);
    }
}
