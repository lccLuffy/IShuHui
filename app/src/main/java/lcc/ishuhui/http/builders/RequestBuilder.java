package lcc.ishuhui.http.builders;

import java.util.Map;

import lcc.ishuhui.http.request.HttpRequest;
import okhttp3.CacheControl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by lcc_luffy on 2016/1/25.
 */
public abstract class RequestBuilder {
    protected Object tag;
    protected String url;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    protected Request.Builder builder = new Request.Builder();
    protected CacheControl cacheControl;
    public RequestBuilder(String url, Map<String, String> params, Map<String, String> headers,CacheControl cacheControl,Object tag)
    {
        if(url == null)
        {
            throw new IllegalArgumentException("url can never be null !!");
        }
        this.url = url;
        this.params = params;
        this.headers = headers;
        this.cacheControl = cacheControl;
        this.tag = tag;
    }

    protected abstract RequestBody buildRequestBody();

    protected abstract Request buildRequest(Request.Builder builder,RequestBody requestBody);

    public Request generateRequest()
    {
        if(tag != null)
        {
            builder.tag(tag);
        }
        builder.url(url);
        if(headers != null)
        {
            for(Map.Entry<String,String> entry : headers.entrySet())
            {
                builder.addHeader(entry.getKey(),entry.getValue());
            }
        }

        if(cacheControl != null)
        {
            builder.cacheControl(cacheControl);
        }

        return buildRequest(builder,buildRequestBody());
    }

    public HttpRequest build()
    {
        return new HttpRequest(this);
    }
}
