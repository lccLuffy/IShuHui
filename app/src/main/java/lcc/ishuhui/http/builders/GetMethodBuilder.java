package lcc.ishuhui.http.builders;

import lcc.ishuhui.http.request.HttpRequest;

/**
 * Created by lcc_luffy on 2016/1/25.
 */
public class GetMethodBuilder extends MethodBuild {
    @Override
    public HttpRequest build() {
        return new GetRequestBuilder(url,params,headers,cacheControl,tag).build();
    }
}
