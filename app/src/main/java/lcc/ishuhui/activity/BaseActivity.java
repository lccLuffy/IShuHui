package lcc.ishuhui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import lcc.ishuhui.R;

/**
 * Created by lcc_luffy on 2016/1/23.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Nullable
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        if(toolbar != null)
            setSupportActionBar(toolbar);
        if (isDisplayHomeAsUpEnabled() && getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public abstract int getLayoutId();

    /**
     *
     */
    private Toast toast;

    public void toast(CharSequence msg) {
        if (toast == null)
            toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.setText(msg);
        toast.show();
    }

    protected boolean isDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
