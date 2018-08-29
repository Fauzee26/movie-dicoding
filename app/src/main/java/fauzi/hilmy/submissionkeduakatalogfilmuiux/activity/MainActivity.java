package fauzi.hilmy.submissionkeduakatalogfilmuiux.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.fragment.FavoriteFragment;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.fragment.HomeFragment;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.fragment.NowShowingFragment;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.fragment.UpcomingFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_button)
    ImageView drawerButton;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.language_button)
    ImageView languageButton;
    @BindView(R.id.frame_movies)
    FrameLayout frameMovies;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    FlowingDrawer drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupDrawer();
        title.setText(getString(R.string.home));
        drawerLayout.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);

        if (savedInstanceState == null) {
            HomeFragment home = new HomeFragment();
            home.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_movies, home, getString(R.string.search)).commit();
        } else {
            HomeFragment home = (HomeFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.search));
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_movies, home, getString(R.string.search)).commit();
        }

        remindRelease();
        notifReminder();

        addFragmentToTop(new HomeFragment());
    }

    private void notifReminder() {
//TODO set Reminder Notif
    }

    private void remindRelease() {
        //TODO set release reminder
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isMenuVisible()) {
            drawerLayout.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    private void setupDrawer() {
        navView.setNavigationItemSelectedListener(this);
    }

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            addFragmentToTop(new HomeFragment());
            title.setText(getString(R.string.home));
        } else if (id == R.id.nav_nowshowing) {
            addFragmentToTop(new NowShowingFragment());
            title.setText(getString(R.string.now_showing));
        } else if (id == R.id.nav_upcoming) {
            addFragmentToTop(new UpcomingFragment());
            title.setText(getString(R.string.upcoming));
//        } else if (id == R.id.nav_setting) {
//            addFragmentToTop(new SettingsFragment());

        } else if (id == R.id.nav_favorite) {
            addFragmentToTop(new FavoriteFragment());
            title.setText(R.string.favorite);
        }

        drawerLayout.closeMenu();
        return true;
    }

    private void addFragmentToTop(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_movies, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick({R.id.drawer_button, R.id.language_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.drawer_button:
                drawerLayout.openMenu();
                break;
            case R.id.language_button:
                PopupMenu popup = new PopupMenu(MainActivity.this, languageButton);
                popup.getMenuInflater()
                        .inflate(R.menu.main, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_language) {
                            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                        }
                        return true;
                    }
                });
                popup.show();
                break;
        }
    }
}
