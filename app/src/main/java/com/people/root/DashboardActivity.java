package com.people.root;

import android.content.Context;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.people.R;
import com.people.TodoFragment;
import com.people.initials.ProfileActivity;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener{
    private Context mContext;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String CURRENTFRAGMENT = "";
    private Fragment fragment;
    public FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mContext = this;
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
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initUI();
        initListener();

        callSetupFragment(SCREENS.HOME, null);

    }


    public void initUI() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
    }

    public void initListener() {
floatingActionButton.setOnClickListener(this);
        ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, ProfileActivity.class));
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(getSupportFragmentManager().getBackStackEntryCount() > 1){
                super.onBackPressed();
            }else{
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            callSetupFragment(SCREENS.HOME, null);
            // Handle the camera action
        } else if (id == R.id.nav_search) {
            callSetupFragment(SCREENS.VOTERSEARCH, null);
        } else if (id == R.id.nav_history) {
            callSetupFragment(SCREENS.HISTORY,null);
        } else if (id == R.id.nav_support) {
            callSetupFragment(SCREENS.FEEDBACK,null);
        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "this will be a link to download the app from the google play store";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share app download link");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (id == R.id.nav_about) {

        }
        else if (id == R.id.nav_todo) {
            callSetupFragment(SCREENS.VOTERQUERYRAISING,"todo");
        }
        else if (id == R.id.nav_push) {
            callSetupFragment(DashboardActivity.SCREENS.PUSHUPDATES,null);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void callSetupFragment(SCREENS screens, Object data) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if(data == null) data = "";
        switch (screens) {
            case PUSHUPDATES:
                fragment = PushUpdatesFragment.newInstance("", "");
                CURRENTFRAGMENT = SCREENS.PUSHUPDATES.toString();
                break;

            case REQUEST:
                fragment = RequestFragment.newInstance(data.toString(), "");
                CURRENTFRAGMENT = SCREENS.REQUEST.toString();
                break;
            case VOTERQUERYRAISING:
                fragment = VoterQueryRaisingFragment.newInstance(data.toString(), "");
                CURRENTFRAGMENT = SCREENS.VOTERQUERYRAISING.toString();
                break;
            case HISTORY:
                fragment = HistoryFragment.newInstance("", "");
                CURRENTFRAGMENT = SCREENS.HISTORY.toString();
                break;

            case VOTERVIEWFEEDBACK:
                fragment = VoterViewFeedbackScreen.newInstance(data.toString(), "");
                CURRENTFRAGMENT = SCREENS.VOTERVIEWFEEDBACK.toString();
                break;

            case VOTERSEARCH:
                fragment = VoterSearchFragment.newInstance("", "");
                CURRENTFRAGMENT = SCREENS.VOTERSEARCH.toString();
                break;

            case HOME:
                fragment = PostFragment.newInstance("", "");
                CURRENTFRAGMENT = SCREENS.HOME.toString();
                break;

            case TODO:
                fragment = TodoFragment.newInstance("", "");
                CURRENTFRAGMENT = SCREENS.TODO.toString();
                break;

            case FEEDBACK:
                fragment = BlankFragment.newInstance("", "");
                CURRENTFRAGMENT = SCREENS.FEEDBACK.toString();
                break;


        }

        fragmentTransaction.replace(R.id.inner_frame, fragment, CURRENTFRAGMENT);
        fragmentTransaction.addToBackStack(CURRENTFRAGMENT);
        fragmentTransaction.commit();//AllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab:
                callSetupFragment(SCREENS.VOTERQUERYRAISING,null);
                break;
        }
    }


    public enum SCREENS {
        HOME,PUSHUPDATES,REQUEST,VOTERQUERYRAISING,HISTORY,VOTERVIEWFEEDBACK,VOTERSEARCH, TODO, FEEDBACK
    }



}
