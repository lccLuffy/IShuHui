package lcc.ishuhui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import lcc.ishuhui.R;
import lcc.ishuhui.activity.WebActivity;
import lcc.ishuhui.constants.API;
import lcc.ishuhui.manager.ChapterListManager;
import lcc.ishuhui.model.ChapterListModel;

/**
 * Created by lcc_luffy on 2016/1/23.
 */
public class ChapterListAdapter extends LoadMoreAdapter<ChapterListModel.ReturnEntity.Chapter> {

    public ChapterListAdapter(final Context context) {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ChapterListModel.ReturnEntity.Chapter chapter = data.get(position);
                ChapterListManager.instance().setChapters(data, position);
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra(WebActivity.URL, API.URL_IMG_CHAPTER + chapter.Id);
                intent.putExtra(WebActivity.TITLE, chapter.Title);
                intent.putExtra(WebActivity.CHAPTER_NUM, chapter.ChapterNo);
                context.startActivity(intent);
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detial_book, parent, false));
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_zone_item)
        ImageView iv_zone_item;

        @Bind(R.id.tv_comic_title)
        TextView tv_comic_title;

        @Bind(R.id.tv_comic_intro)
        TextView tv_comic_intro;

        @Bind(R.id.tv_comic_status)
        TextView tv_comic_status;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBindData(ChapterListModel.ReturnEntity.Chapter data) {
            tv_comic_title.setText(data.Title);
            tv_comic_intro.setText(data.RefreshTimeStr);
            tv_comic_status.setText(data.Sort + "话");
            Glide.with(itemView.getContext())
                    .load(data.FrontCover)
                    .centerCrop()
                    .placeholder(R.color.gray)
                    .into(iv_zone_item);
        }
    }
}
