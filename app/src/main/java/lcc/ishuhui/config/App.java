package lcc.ishuhui.config;

import android.util.TypedValue;

import com.orm.SugarApp;

import lcc.ishuhui.R;

/**
 * Created by lcc_luffy on 2016/1/25.
 */
public class App extends SugarApp{
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public static App getInstance()
    {
        return instance;
    }
    public int getColorPrimary(){
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}
