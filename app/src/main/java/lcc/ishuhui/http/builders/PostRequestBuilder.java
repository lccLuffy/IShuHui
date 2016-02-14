package lcc.ishuhui.http.builders;

import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by lcc_luffy on 2016/1/25.
 */
public class PostRequestBuilder extends RequestBuilder{
    public PostRequestBuilder(String url, Map<String, String> params, Map<String, String> headers, CacheControl cacheControl,Object tag) {
        super(url, params, headers,cacheControl,tag);
    }

    @Override
    protected RequestBody buildRequestBody() {
        FormBody.Builder builder = new FormBody.Builder();
        if(params != null)
        {
            for(Map.Entry<String,String> entry : params.entrySet())
            {
                builder.add(entry.getKey(),entry.getValue());
            }
        }
        return builder.build();
    }

    @Override
    protected Request buildRequest(Request.Builder builder, RequestBody requestBody) {
        return builder.post(requestBody).build();
    }
}
