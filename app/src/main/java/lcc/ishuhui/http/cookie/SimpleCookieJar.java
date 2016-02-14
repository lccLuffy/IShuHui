package lcc.ishuhui.http.cookie;

import java.util.ArrayList;
import java.util.List;

import lcc.ishuhui.utils.L;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by lcc_luffy on 2016/1/28.
 */
public class SimpleCookieJar implements CookieJar{
    private List<Cookie> cookieList;

    public SimpleCookieJar() {
        cookieList = new ArrayList<>();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieList.addAll(cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> result = new ArrayList<>();
        for (Cookie cookie : cookieList)
        {
            if (cookie.matches(url))
            {
                result.add(cookie);
            }
        }
        return result;
    }
}
