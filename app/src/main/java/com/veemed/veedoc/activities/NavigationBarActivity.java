package com.veemed.veedoc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.veemed.veedoc.R;
import com.veemed.veedoc.fragments.ChangePasswordFragment;
import com.veemed.veedoc.fragments.endpoints.EndpointsFragment;
import com.veemed.veedoc.fragments.MessagesFragment;
import com.veemed.veedoc.fragments.MoreFragment;
import com.veemed.veedoc.fragments.MyOffDaysFragment;
import com.veemed.veedoc.fragments.ProfileManagementFragment;
import com.veemed.veedoc.fragments.ScheduleFragment;
import com.veemed.veedoc.fragments.sessions.SessionFragment;
import com.veemed.veedoc.fragments.StartConversationFragment;
import com.veemed.veedoc.viewmodels.NavigationActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationBarActivity extends BaseActivity {
    private Toolbar toolbarToOpen;
    private TextView toolBarCenterTextView;
    private MenuItem signOutIcon;
    private BottomNavigationView navView;
    private FragmentManager fm = getSupportFragmentManager();
    private androidx.fragment.app.Fragment currentFragment = fm.findFragmentById(R.id.fragment_box);

    private NavigationActivityViewModel navigationActivityViewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            androidx.fragment.app.Fragment fragmentToOpen = null;

            switch (item.getItemId()) {
                case R.id.session_option:
                    // open a new session fragment
                    fragmentToOpen = SessionFragment.newInstance();
                    break;

                case R.id.messages_option:
                    // open a new MessagesFragment
                    fragmentToOpen = MessagesFragment.newInstance();
                    break;

                case R.id.schedule_option:
                    // open a new schedule fragment;
                    fragmentToOpen = ScheduleFragment.newInstance();
                    break;

                case R.id.endpoints_option:
                    // open a new endpoints fragment
                    fragmentToOpen = EndpointsFragment.newInstance();
                    break;

                case R.id.more_option:
                    // open a new endpoints fragment
                    fragmentToOpen = MoreFragment.newInstance();
                    break;
            }

            // need to replace the current fragment every time
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_box, fragmentToOpen, "OPENED_FRAG").commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // initialize widgets
        initWidgets();

        // set up view model
        navigationActivityViewModel = ViewModelProviders.of(this).get(NavigationActivityViewModel.class);

        // set up observers
        setUpObservers();

        // listen for fragment changes
        setBackStackListener();

        // Setting default view
        setUpDefaultFragmentView();

    }

    private void initWidgets() {
        toolbarToOpen = findViewById(R.id.toolbar);
        // defining textView
        toolBarCenterTextView = findViewById(R.id.toolbarCenterTextView);
        // define sign out icon
        signOutIcon = findViewById(R.id.signOut_icon);

    }

    private void setUpDefaultFragmentView() {
        navView.setSelectedItemId(R.id.session_option);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate menu
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        // assign sign out icon when menu first create (onCreate only called once)
        signOutIcon = menu.findItem(R.id.signOut_icon);
        if (!isMoreFragment()) {
            signOutIcon.setVisible(true);
        } else {
            signOutIcon.setVisible(false);
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.signOut_icon:
                goBackToLogin();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        /* refer to  refer to https://stackoverflow.com/questions/10579333/how-can-i-get-the-options-menu-of-my-activity for method of updating visibility of icons
        & https://stackoverflow.com/questions/4199753/how-can-i-alter-a-menuitem-on-the-options-menu-on-android &
        https://developer.android.com/reference/android/app/Activity.html#onPrepareOptionsMenu(android.view.Menu) for method applied
         */

        signOutIcon = menu.findItem(R.id.signOut_icon);
        if (!isMoreFragment()) {
            signOutIcon.setVisible(true);
        } else {
            signOutIcon.setVisible(false);
        }
        super.onPrepareOptionsMenu(menu);
        return true;


    }

    private void setBackClicker() {
        toolbarToOpen.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void setUpObservers() {
        navigationActivityViewModel.getCenterTextViewVisible().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean visible) {
                if (visible){
                    // set textView to VISIBLE as that's what we want to show (text in middle)
                    toolBarCenterTextView.setVisibility(View.VISIBLE);
                } else {
                    // set textView to INVISIBLE as that's what we don't want text in middle
                    toolBarCenterTextView.setVisibility(View.INVISIBLE);
                }
            }
        });

        navigationActivityViewModel.getToolbarTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String title) {
                if (navigationActivityViewModel.getCenterTextViewVisible().getValue()){
                    toolBarCenterTextView.setText(title);
                    toolbarToOpen.setTitle(null);
                    setSupportActionBar(toolbarToOpen);
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                } else {
                    toolbarToOpen.setTitle(title);
                    toolBarCenterTextView.setText(null);
                    //getSupportActionBar().setDisplayShowTitleEnabled(true);
                    setSupportActionBar(toolbarToOpen);
                }
            }
        });

        navigationActivityViewModel.getToolbarSubtitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String subtitle) {
                toolbarToOpen.setSubtitle(subtitle);
            }
        });

    }

    private void setBackStackListener() {
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                currentFragment = fm.findFragmentById(R.id.fragment_box);
                // IF THERE IS A FRAGMENT TO BE LOADED...
                if (currentFragment != null) {
                    // set navigation bar item
                    setNavigationBarItem();
                    // set custom back button
                    setCustomBackButton();
                } else {  // IF THERE IS NOT FRAGMENT TO BE LOADED, GO BACK TO THE LOGIN PAGE
                    goBackToLogin();
                }
            }
        });

    }

    private void setNavigationBarItem(){
        if (isSessionFragment()) {
            navView.getMenu().getItem(0).setChecked(true);
        } else if (isMessagesFragment() || isStartConversationFragment()) {
            navView.getMenu().getItem(1).setChecked(true);
        } else if (isScheduleFragment()) {
            navView.getMenu().getItem(2).setChecked(true);
        } else if (isEndpointsFragment()) {
            navView.getMenu().getItem(3).setChecked(true);
        } else if (isMoreFragment() || isProfileManagementFragment() || isChangePasswordFragment() || isOffDaysFragment()) {
            navView.getMenu().getItem(4).setChecked(true);
        }
    }

    private void setCustomBackButton(){
        if (isStartConversationFragment() || isProfileManagementFragment() || isOffDaysFragment() || isChangePasswordFragment()) {
            toolbarToOpen.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            setBackClicker();
        } else {
            if (toolbarToOpen.getNavigationIcon() != null) {
                toolbarToOpen.setNavigationIcon(null);
            }
        }
    }

    private void goBackToLogin(){
        Intent intent = new Intent(NavigationBarActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean isSessionFragment() {
        return currentFragment instanceof SessionFragment;
    }

    private boolean isMessagesFragment() {
        return currentFragment instanceof MessagesFragment;
    }

    private boolean isScheduleFragment(){
        return currentFragment instanceof ScheduleFragment;
    }

    private boolean isEndpointsFragment(){
        return currentFragment instanceof EndpointsFragment;
    }

    private boolean isMoreFragment(){
        return currentFragment instanceof MoreFragment;
    }

    private boolean isProfileManagementFragment(){
        return currentFragment instanceof ProfileManagementFragment;
    }

    private boolean isOffDaysFragment(){
        return currentFragment instanceof MyOffDaysFragment;
    }

    private boolean isChangePasswordFragment(){
        return currentFragment instanceof ChangePasswordFragment;
    }

    private boolean isStartConversationFragment(){
        return currentFragment instanceof StartConversationFragment;
    }

}
