package lcc.ishuhui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import lcc.ishuhui.R;
import lcc.ishuhui.adapter.CategoryFragmentAdapter;
import lcc.ishuhui.model.User;
import lcc.ishuhui.utils.PreferencesUtil;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener
        , View.OnClickListener {

    @Bind(R.id.fab)
    FloatingActionButton actionButton;

    @Bind(R.id.viewPage)
    ViewPager viewPager;

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.navigation_view)
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        EventBus.getDefault().register(this);
        onEventMainThread(null);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void init() {

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionButton.setOnClickListener(this);

        CategoryFragmentAdapter adapter = new CategoryFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        actionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                boolean see = PreferencesUtil.getInstance().getInt("latest_see_id", -1) > 0;
                if (see) {
                    toast(PreferencesUtil.getInstance().getString("latest_see_title", "未知漫画"));
                } else {
                    toast("最近没看漫画");
                }
                return true;
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isDisplayHomeAsUpEnabled() {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, Test.class));
                return true;
            case R.id.action_exit:
                Snackbar.make(toolbar, "确定退出吗?", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        })
                        .show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.action_picture:
                startActivity(new Intent(this,PicturesActivity.class));
                break;*/
        }
        item.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                int id;
                if ((id = PreferencesUtil.getInstance().getInt("latest_see_id", -1)) > 0) {
                    Intent intent = new Intent(this, ChapterListActivity.class);
                    intent.putExtra(ChapterListActivity.ID, id + "");
                    intent.putExtra(ChapterListActivity.TITLE, PreferencesUtil.getInstance().getString("latest_see_title", "未知漫画"));
                    startActivity(intent);
                } else {
                    toast("最近没看漫画");
                }
                break;
        }
    }

    public void onEventMainThread(@Nullable User user) {
        if (User.getInstance().isLogin()) {
            if (user == null)
                user = User.getInstance();
            View header = navigationView.getHeaderView(0);
            TextView email = (TextView) header.findViewById(R.id.email);
            TextView username = (TextView) header.findViewById(R.id.username);
            email.setText(user.getEmail());
            username.setText("(ID:" + user.getId() + ")");
        }
    }
}
