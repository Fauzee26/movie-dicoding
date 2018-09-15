package fauzi.hilmy.submissionkeduakatalogfilmuiux.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.R;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.fragment.FavoriteFragment;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.fragment.HomeFragment;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.fragment.NowShowingFragment;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.fragment.UpcomingFragment;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.release.ReleaseReceiver;
import fauzi.hilmy.submissionkeduakatalogfilmuiux.reminder.ReminderReceiver;

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
            HomeFragment search = new HomeFragment();
            search.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_movies, search, getString(R.string.search)).commit();
        } else {
            HomeFragment search = (HomeFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.search));
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_movies, search, getString(R.string.search)).commit();
        }
        setReminderAlarm();
        setRelease();
//        addFragmentToTop(new HomeFragment());
    }

    private void setRelease() {
        Intent alarmIntent = new Intent(MainActivity.this, ReleaseReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void setReminderAlarm() {
        Intent intent = new Intent(MainActivity.this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 86400000L, pendingIntent);

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            addFragmentToTop(new HomeFragment());
            title.setText(getString(R.string.home));
        } else if (id == R.id.nav_nowshowing) {
            Intent iNow = new Intent(MainActivity.this, NowActivity.class);
            startActivity(iNow);
//            addFragmentToTop(new NowShowingFragment());
//            title.setText(getString(R.string.now_showing));
        } else if (id == R.id.nav_upcoming) {
            Intent iUp = new Intent(MainActivity.this, UpcomingActivity.class);
            startActivity(iUp);
//            addFragmentToTop(new UpcomingFragment());
//            title.setText(getString(R.string.upcoming));
        } else if (id == R.id.nav_favorite) {
            Intent iFav = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(iFav);
//            addFragmentToTop(new FavoriteFragment());
//            title.setText(R.string.favorite);
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
