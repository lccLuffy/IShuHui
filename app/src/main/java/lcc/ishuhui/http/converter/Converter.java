package lcc.ishuhui.http.converter;

import okhttp3.Response;

/**
 * Created by lcc_luffy on 2016/1/30.
 */
public interface Converter<T> {
    T convert(Response response);
}
