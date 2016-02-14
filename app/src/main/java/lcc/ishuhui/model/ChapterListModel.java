package lcc.ishuhui.model;

/**
 * Created by lcc_luffy on 2016/1/23.
 */
public class ChapterListModel {
    public String ErrCode;
    public String ErrMsg;

    public ReturnEntity Return;
    public String Costtime;
    public boolean IsError;
    public String Message;

    public static class ReturnEntity {
        /**
         * Id : 6
         * Title : 黑子的篮球
         * FrontCover : http://img01.ishuhui.com/mhcover/heizi.jpg
         * RefreshTime : /Date(1442557713000)/
         * RefreshTimeStr : 2015-09-18 14:28:33
         * Explain : 逆境不是，可以令人燃烧起来吗？
         * SerializedState : 0
         * Author : 藤卷忠俊
         * LastChapterNo : 0
         * ClassifyId : 0
         * LastChapter : null
         * Chapters : null
         * MoreUrl : null
         * Recommend : false
         * Copyright : 0
         */

        public ParentItemEntity ParentItem;
        public int ListCount;
        public int PageSize;
        public int PageIndex;
        /**
         * Id : 1387
         * Title : Replace04
         * Sort : 4
         * FrontCover : http://img08.ickeep.com/heizi/Replace04/hzs.png
         * Images : null
         * RefreshTime : /Date(1432098173000)/
         * RefreshTimeStr : 2015-05-20
         * Book : null
         * PostUser : 发布者
         * ChapterNo : 4
         * Reel : 0
         * BookId : 6
         * ChapterType : 0
         * DownLoadUrl : null
         * Copyright : null
         * Tencent : null
         * ExceptionChapter : null
         * CreateTime : /Date(-59011459200000)/
         */

        public java.util.List<Chapter> List;

        public static class ParentItemEntity {
            public int Id;
            public String Title;
            public String FrontCover;
            public String RefreshTime;
            public String RefreshTimeStr;
            public String Explain;
            public String SerializedState;
            public String Author;
            public int LastChapterNo;
            public int ClassifyId;
            public boolean Recommend;
            public int Copyright;
        }

        public static class Chapter {
            public int Id;
            public String Title;
            public int Sort;
            public String FrontCover;
            public String RefreshTime;
            public String RefreshTimeStr;
            public String PostUser;
            public int ChapterNo;
            public int Reel;
            public int BookId;
            public int ChapterType;
            public String CreateTime;
        }
    }
}

