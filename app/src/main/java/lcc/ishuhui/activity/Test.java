package lcc.ishuhui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lcc.stateLayout.StateLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import lcc.ishuhui.R;

/**
 * Created by lcc_luffy on 2016/1/30.
 */
public class Test extends AppCompatActivity {
    @Bind(R.id.stateLayout)
    StateLayout stateLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_test);
        ButterKnife.bind(this);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.icon);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        imageView.setLayoutParams(layoutParams);
        stateLayout.addView(imageView);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    boolean is = false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                is = !is;
                if(is)
                {
                    stateLayout.showEmptyView();
                }
                else
                {
                    stateLayout.showErrorView();
                }
                return true;
            case R.id.action_exit:
                stateLayout.showProgressView();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
