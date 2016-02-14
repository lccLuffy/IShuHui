package lcc.ishuhui.http.builders;

import lcc.ishuhui.http.request.HttpRequest;

/**
 * Created by lcc_luffy on 2016/1/25.
 */
public class PostMethodBuilder extends MethodBuild {
    @Override
    public HttpRequest build() {
        return new PostRequestBuilder(url,params,headers,cacheControl,tag).build();
    }
}
