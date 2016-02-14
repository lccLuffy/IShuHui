package lcc.ishuhui.model;

import com.orm.SugarRecord;

/**
 * Created by lcc_luffy on 2016/2/1.
 */
public class Sugar extends SugarRecord {

    String title;


    public Sugar(String title) {
        this.title = title;
    }


    public Sugar() {
    }
}
