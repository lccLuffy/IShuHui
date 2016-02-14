package lcc.ishuhui.model;

import de.greenrobot.event.EventBus;
import lcc.ishuhui.constants.API;
import lcc.ishuhui.http.HttpUtil;
import lcc.ishuhui.http.callback.HttpCallBack;
import lcc.ishuhui.utils.JsonUtil;
import lcc.ishuhui.utils.PreferencesUtil;
import lcc.ishuhui.utils.SecurityUtil;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by lcc_luffy on 2016/1/28.
 */
public class User {
    public static final String COOKIE_KEY = "Cookie";
    private static final String USER_KEY_ID = "USER_KEY_ID";
    private static final String USER_KEY_EMAIL = "USER_KEY_EMAIL";
    private static final String USER_KEY_NICKNAME = "USER_KEY_NICKNAME";
    private static User instance;
    private String Set_Cookie;

    public String getId() {
        return id;
    }

    public String getNickName() {
        return NickName;
    }

    public String getEmail() {
        return email;
    }

    private String id;
    private String email;
    private String NickName;
    private User(String set_Cookie)
    {
        Set_Cookie = set_Cookie;
    }
    public static User getInstance()
    {
        if(instance == null)
        {
            synchronized (User.class)
            {
                if(instance == null) {
                    instance = new User(PreferencesUtil.getInstance().getString(COOKIE_KEY,null));
                    instance.email = PreferencesUtil.getInstance().getString(USER_KEY_EMAIL,null);
                    instance.id = PreferencesUtil.getInstance().getString(USER_KEY_ID,null);
                    instance.NickName = PreferencesUtil.getInstance().getString(USER_KEY_NICKNAME,null);
                }
            }
        }
        return instance;
    }
    public boolean isLogin()
    {
        return Set_Cookie != null;
    }

    public static void login(String email, String password, final LoginCallBack loginCallBack)
    {
        HttpUtil.post()
                .url(API.URL_USER_LOGIN)
                .addParam("Email",email)
                .addParam("Password", SecurityUtil.createMd5(password))
                .addParam("FromType",String.valueOf(2))
                .build()
                .execute(new HttpCallBack<LoginMessageModel>() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                        if(loginCallBack != null)
                            loginCallBack.onFailure(e.toString());
                    }

                    @Override
                    public LoginMessageModel parse(Response response) throws Exception {
                        return JsonUtil.getInstance().fromJson(response.body().string(),LoginMessageModel.class);
                    }

                    @Override
                    public void onSuccess(Call call, LoginMessageModel result) {
                        int ErrCode = -2;
                        try
                        {
                            ErrCode = Integer.parseInt(result.ErrCode);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        if(ErrCode == 0)
                        {
                            Headers headers =  response.headers();
                            PreferencesUtil.getInstance().start()
                                    .put(User.COOKIE_KEY,headers.get("Set-Cookie"))
                                    .put(USER_KEY_EMAIL,result.Return.Email)
                                    .put(USER_KEY_ID,result.Return.Id)
                                    .put(USER_KEY_NICKNAME,result.Return.NickName)
                                    .commit();
                            getInstance().notifyLogin();
                            if (loginCallBack != null)
                                loginCallBack.onSuccess();
                            EventBus.getDefault().post(getInstance());
                        }
                        else
                        {
                            if (loginCallBack != null)
                                loginCallBack.onFailure(result.ErrMsg);
                        }

                    }
                });
    }

    public String getCookie() {
        return Set_Cookie;
    }
    public void notifyLogin()
    {
        instance = null;
    }

    public interface LoginCallBack
    {
        void onSuccess();
        void onFailure(String msg);
    }
}
