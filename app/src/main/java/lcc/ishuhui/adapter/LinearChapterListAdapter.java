package lcc.ishuhui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import lcc.ishuhui.R;
import lcc.ishuhui.activity.WebActivity;
import lcc.ishuhui.manager.ChapterListManager;
import lcc.ishuhui.model.ChapterListModel;
import lcc.ishuhui.utils.JsonUtil;
import lcc.ishuhui.utils.PreferencesUtil;

/**
 * Created by lcc_luffy on 2016/1/23.
 */
public class LinearChapterListAdapter extends LoadMoreAdapter<ChapterListModel.ReturnEntity.Chapter> {

    public LinearChapterListAdapter(final Context context) {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ChapterListModel.ReturnEntity.Chapter chapter = data.get(position);
                ChapterListManager.instance().setChapters(data, position);
                PreferencesUtil.getInstance().start()
                        .put("book" + chapter.BookId, JsonUtil.getInstance().toJson(chapter))
                        .put("book_chapter_" + chapter.BookId, chapter.ChapterNo)
                        .commit();
                WebActivity.jumpToMe(context, chapter);
            }
        });
    }

    @Override
    public void onBindHolder(RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).onBindData(data.get(position));
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_linear_detial_book, parent, false));
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_comic_title)
        TextView tv_comic_title;


        @Bind(R.id.tv_comic_status)
        TextView tv_comic_status;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBindData(ChapterListModel.ReturnEntity.Chapter data) {
            tv_comic_title.setText(data.Title);
            tv_comic_status.setText(data.Sort + "话");
        }
    }
}
