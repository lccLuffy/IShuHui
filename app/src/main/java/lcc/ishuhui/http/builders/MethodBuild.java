package lcc.ishuhui.http.builders;

import java.util.LinkedHashMap;
import java.util.Map;

import lcc.ishuhui.http.request.HttpRequest;
import okhttp3.CacheControl;

/**
 * Created by lcc_luffy on 2016/1/25.
 */
public abstract class MethodBuild {
    protected Object tag;
    protected String url;
    protected Map<String,String> params;
    protected Map<String,String> headers;

    protected CacheControl cacheControl;
    public MethodBuild url(String url)
    {
        this.url = url;
        return this;
    }
    public MethodBuild tag(Object tag)
    {
        this.tag = tag;
        return this;
    }
    public MethodBuild addParam(String key,String value)
    {
        if(params == null)
            params = new LinkedHashMap<>();
        params.put(key,value);
        return this;
    }
    public MethodBuild params(Map<String,String> params)
    {
        if(this.params == null)
            this.params = new LinkedHashMap<>();
        this.params.putAll(params);
        return this;
    }

    public MethodBuild addHeader(String key,String value)
    {
        if(headers == null)
            headers = new LinkedHashMap<>();
        headers.put(key,value);
        return this;
    }
    public MethodBuild headers(Map<String,String> headers)
    {
        this.headers = headers;
        return this;
    }
    public MethodBuild cacheControl(CacheControl cacheControl)
    {
        this.cacheControl = cacheControl;
        return this;
    }
    public abstract HttpRequest build();
}
