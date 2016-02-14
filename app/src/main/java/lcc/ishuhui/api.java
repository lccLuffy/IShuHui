package lcc.ishuhui;

/**
 * Created by lcc_luffy on 2016/1/27.
 */
public class api {

    public static final String APP_HTTP = "http://www.ishuhui.net";
    public static final String GET_ALL_BOOK = "http://www.ishuhui.net/ComicBooks/GetSubscribe";
    public static final String GET_NEW_BOOK = "http://www.ishuhui.net/ComicBooks/GetLastChapterForBookIds?idJson=[1,2,3]";
    public static final String URL_CATEGORY_DATA = "http://www.ishuhui.net/ComicBooks/GetAllBook";
    public static final String URL_COMIC_BOOK_DATA = "http://www.ishuhui.net/ComicBooks/GetChapterList";
    public static final String URL_IMG_CHAPTER = "http://www.ishuhui.net/ReadComicBooksToIso/";
    public static final String URL_RECOMMEND = "http://www.ishuhui.net/ComicBooks/GetAllBook";
    public static final String URL_SEARCH_DATA = "http://www.ishuhui.net/ComicBooks/GetAllBook";
    public static final String URL_SLIDE_DATA = "http://two.ishuhui.com/imgs.html";
    public static final String URL_SUBCRIBE_USER = "http://www.ishuhui.net/ComicBooks/GetAllBook";
    public static final String URL_USER_LOGIN = "http://www.ishuhui.net/UserCenter/Login";
    public static final String URL_USER_REGISTER = "http://www.ishuhui.net/UserCenter/Regedit";
    public static final String URL_USER_SUBSCRIBE = "http://www.ishuhui.net/Subscribe";
    public static final String URL_WEEK_SEVEN = "http://www.ishuhui.net/ComicBooks/GetAllBook";

    /*public static abstract interface ServiceInterface
    {
        @POST("/ComicBooks/GetChapterList")
        public abstract com.ishuhui.comic.model.result.chapterList.ChapterList chapterList(@Query("id") long paramLong, @Query("PageIndex") int paramInt);

        @POST("/ComicBooks/GetAllBook")
        public abstract BookList getBookByCategory(@Query("ClassifyId") String paramString, @Query("Size") int paramInt1, @Query("PageIndex") int paramInt2);

        @POST("/ComicBooks/GetChapter")
        public abstract ComicImages getImages(@Query("id") String paramString);

        @GET("/ComicBooks/GetLanternSlide")
        public abstract SliderList getSilderList();

        @POST("/ComicBooks/GetHotKeyword")
        public abstract List<String> hotwords();

        @POST("/UserCenter/Login")
        public abstract LoginMessage login(@Query("Email") String paramString1, @Query("Password") String paramString2, @Query("FromType") int paramInt);

        @POST("/ComicBooks/GetAllBook")
        public abstract BookList recent(@Header("Cookie") String paramString, @Query("Days") int paramInt1, @Query("Subscribe") int paramInt2);

        @POST("/ComicBooks/GetAllBook")
        public abstract BookList recommend(@Header("Cookie") String paramString, @Query("Recommended") int paramInt1, @Query("Subscribe") int paramInt2);

        @POST("/ComicBooks/GetLastChapterForBookIds")
        public abstract com.ishuhui.comic.model.result.refresh.ChapterList refreshList(@Query("idJson") String paramString);

        @POST("/UserCenter/Regedit")
        public abstract RegisterMessage register(@Query("Email") String paramString1, @Query("Password") String paramString2, @Query("FromType") int paramInt);

        @POST("/ComicBooks/GetAllBook")
        public abstract SearchResult search(@Query("Title") String paramString);

        @POST("/ComicBooks/Subscribe")
        public abstract Response subscribe(@Header("Cookie") String paramString, @Query("isSubscribe") boolean paramBoolean, @Query("bookId") long paramLong, @Query("fromtype") int paramInt);

        @POST("/ComicBooks/GetSubscribe")
        public abstract BookList subscribeList(@Header("Cookie") String paramString, @Query("fromtype") int paramInt);
    }*/
}
