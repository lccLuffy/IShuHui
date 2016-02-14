package lcc.ishuhui.model;

/**
 * Created by lcc_luffy on 2016/1/23.
 */

import java.util.ArrayList;

/**
 * Id : 21
 * Title : 境界触发者
 * FrontCover : http://img01.ishuhui.com/mhcover/wt.jpg
 * RefreshTime : /Date(1453463406000)/
 * RefreshTimeStr : 2016-01-22 19:50:06
 * Explain : 事情都有千万种解决方法，相反，有时也会无法可解
 * SerializedState : 未定义
 * Author : 苇原大介
 * LastChapterNo : 0
 * ClassifyId : 3
 * LastChapter : {"Id":4415,"Title":"伽罗普拉⑦","Sort":128,"FrontCover":"http://img01.ishuhui.com/wt/duan128/wts.png","Images":null,"RefreshTime":"/Date(1453463406000)/","RefreshTimeStr":"2016-01-22","Book":null,"PostUser":"发布者","ChapterNo":128,"Reel":0,"BookId":21,"ChapterType":0,"DownLoadUrl":null,"Copyright":null,"Tencent":null,"ExceptionChapter":null,"CreateTime":"/Date(-59011459200000)/"}
 * Chapters : null
 * MoreUrl : null
 * Recommend : false
 * Copyright : 0
 * Sort : 13
 */
public class BookModel {
    public ResultSet Return;
    public class ResultSet
    {
        public ArrayList<Book> List;
        public class Book
        {
            public String Id;
            public String Title;
            public String FrontCover;
            public String RefreshTime;
            public String Explain;
            public String SerializedState;
            public String Author;
            public String LastChapterNo;
            public String ClassifyId;
            public LastChapterEntity LastChapter;
            public class LastChapterEntity {
                public String Id;
                public String Title;
                public String FrontCover;
                public String Sort;
                public String Images;
                public String RefreshTimeStr;
                public String ChapterNo;
                public String LastChapterNo;
                public String BookId;
            }
        }
    }
}
