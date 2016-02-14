package lcc.ishuhui.utils;

import com.google.gson.Gson;

/**
 * Created by lcc_luffy on 2016/1/25.
 */
public class JsonUtil {
    public static Gson gson;

    private JsonUtil(){};

    public static Gson getInstance()
    {
        if(gson == null)
        {
            synchronized (JsonUtil.class)
            {
                if(gson == null)
                {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }
}
