package com.people.root;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.people.R;
import com.people.TodoFragment;
import com.people.initials.ProfileActivity;
import com.people.utils.AppConstants;
import com.people.utils.PreferencesManager;

import java.util.Locale;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public FloatingActionButton floatingActionButton;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        PreferencesManager manager = PreferencesManager.getInstance(this);
        if (manager.getInt(AppConstants.Preference.PREF_ROLE) == AppConstants.VOTER) {
            menu.findItem(R.id.nav_push).setVisible(false);
            menu.findItem(R.id.nav_report).setVisible(false);
            menu.findItem(R.id.nav_todo).setVisible(false);
        }
        navigationView.setNavigationItemSelectedListener(this);
        initUI();
        initListener();

        callSetupFragment(AppConstants.Screens.HOME, null);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                super.onBackPressed();
            } else {
                finish();
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            switchLanguage();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                callSetupFragment(AppConstants.Screens.VOTERQUERYRAISING, null);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            callSetupFragment(AppConstants.Screens.HOME, null);
            // Handle the camera action
        } else if (id == R.id.nav_search) {
            callSetupFragment(AppConstants.Screens.VOTERSEARCH, null);
        } else if (id == R.id.nav_history) {
            callSetupFragment(AppConstants.Screens.HISTORY, null);
        } else if (id == R.id.nav_support) {
            callSetupFragment(AppConstants.Screens.FEEDBACK, null);
        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "this will be a link to download the app from the google play store";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share app download link");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (id == R.id.nav_report) {
            callSetupFragment(AppConstants.Screens.REPORT, null);
        } else if (id == R.id.nav_todo) {
            callSetupFragment(AppConstants.Screens.VOTERQUERYRAISING, "todo");
        } else if (id == R.id.nav_push) {
            callSetupFragment(AppConstants.Screens.PUSHUPDATES, null);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initUI() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
    }

    public void initListener() {
        floatingActionButton.setOnClickListener(this);
        ((NavigationView) findViewById(R.id.nav_view)).
                getHeaderView(0).findViewById(R.id.imageView).
                setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,
                        ProfileActivity.class));
            }
        });
    }

    private void switchLanguage() {
        String lang = "hi";
        Locale current = getResources().getConfiguration().locale;
        if(current!= null){
            if(current.getDisplayLanguage().equalsIgnoreCase("english") ||
                    current.getDisplayLanguage().equalsIgnoreCase("en_in")){
                lang = "hi";
            }else{
                lang = "en_IN";
            }
        }
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(lang);
        res.updateConfiguration(conf, dm);
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void callSetupFragment(int screens, Object data) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if (data == null) data = "";
        switch (screens) {
            case AppConstants.Screens.PUSHUPDATES:
                setTitle(getString(R.string.push_updates_menu));
                fragment = PushUpdatesFragment.newInstance("", "");
                break;

            case AppConstants.Screens.REQUEST:
                setTitle(getString(R.string.request));
                fragment = RequestFragment.newInstance(data.toString(), "");
                break;
            case AppConstants.Screens.VOTERQUERYRAISING:
                setTitle(getString(R.string.raise_the_qry));
                fragment = VoterQueryRaisingFragment.newInstance(data.toString(), "");
                break;
            case AppConstants.Screens.HISTORY:
                setTitle(getString(R.string.history_menu));
                fragment = HistoryFragment.newInstance("", "");
                break;

            case AppConstants.Screens.VOTERVIEWFEEDBACK:
                setTitle(getString(R.string.feedback));
                fragment = VoterViewFeedbackScreen.newInstance(data.toString(), "");
                break;

            case AppConstants.Screens.VOTERSEARCH:
                setTitle(getString(R.string.search_lead));
                fragment = VoterSearchFragment.newInstance("", "");
                break;

            case AppConstants.Screens.HOME:
                setTitle(getString(R.string.app_name));
                fragment = PostFragment.newInstance("", "");
                break;

            case AppConstants.Screens.TODO:
                setTitle(getString(R.string.todo_menu));
                fragment = TodoFragment.newInstance("", "");
                break;

            case AppConstants.Screens.FEEDBACK:
                setTitle(getString(R.string.feedback));
                fragment = BlankFragment.newInstance("", "");
                break;

            case AppConstants.Screens.REPORT:
                setTitle(getString(R.string.report_menu));
                fragment = ReportFragment.newInstance("", "");
                break;
            case AppConstants.Screens.PRIORITIES:
                setTitle(getString(R.string.priorities));
                fragment = PrioritiesFragment.newInstance("", "");
                break;
            case AppConstants.Screens.PIECHART:
                setTitle(getString(R.string.statistics));
                fragment = BarChartFragment.newInstance("", "");
                break;

        }

        fragmentTransaction.replace(R.id.inner_frame, fragment, screens+"");
        fragmentTransaction.addToBackStack(screens+"");
        fragmentTransaction.commit();//AllowingStateLoss();
    }

}
