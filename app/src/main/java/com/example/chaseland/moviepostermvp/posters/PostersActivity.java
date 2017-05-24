package com.example.chaseland.moviepostermvp.posters;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.chaseland.moviepostermvp.Injection;
import com.example.chaseland.moviepostermvp.R;
import com.example.chaseland.moviepostermvp.Util.ActivityUtils;

public class PostersActivity extends AppCompatActivity {

    private DrawerLayout DrawerLayout;

    private PostersPresenter PosterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posters);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        DrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);

        PostersFragment postersFragment =
                (PostersFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if(postersFragment == null){
            postersFragment = PostersFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), postersFragment, R.id.contentFrame, null);
        }

        this.PosterPresenter = new PostersPresenter(Injection.provideRepository(this), postersFragment);

    }

    public void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {

                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        DrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
}
