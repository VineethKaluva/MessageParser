package edu.inspire.messageparser;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    Toolbar bar;
    TabLayout tabs;
    String[] tabNames = {"Emergency", "Happy", "Emotional"};
    ViewPager pager;
    TabsAdapter adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bar = (Toolbar) findViewById(R.id.toolbar);
        tabs = (TabLayout) findViewById(R.id.tabs);
        setSupportActionBar(bar);
        pager = (ViewPager) findViewById(R.id.pager);
        adp = new TabsAdapter(getSupportFragmentManager(), tabNames);


    }

    @Override
    protected void onResume() {
        super.onResume();


        pager.setAdapter(adp);

        tabs.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }
}
