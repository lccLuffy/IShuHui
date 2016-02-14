package lcc.ishuhui.http.builders;

import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by lcc_luffy on 2016/1/25.
 */
public class GetRequestBuilder extends RequestBuilder{
    public GetRequestBuilder(String url, Map<String, String> params, Map<String, String> headers, CacheControl cacheControl,Object tag) {
        super(url, params, headers,cacheControl,tag);
    }
    private void appendParams()
    {
        if(params == null || params.isEmpty())
            return;
        StringBuilder stringBuilder = new StringBuilder(url);
        stringBuilder.append('?');
        for (Map.Entry<String,String> entry : params.entrySet())
        {
            stringBuilder.append(entry.getKey())
                    .append('=')
                    .append(entry.getValue())
                    .append('&');
        }
        url = stringBuilder.deleteCharAt(stringBuilder.length()-1).toString();
    }
    @Override
    protected RequestBody buildRequestBody() {
        appendParams();
        return null;
    }

    @Override
    protected Request buildRequest(Request.Builder builder, RequestBody requestBody) {
        return builder.get().build();
    }
}
