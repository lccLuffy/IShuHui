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
import lcc.ishuhui.activity.ChapterListActivity;
import lcc.ishuhui.model.BookModel;

/**
 * Created by lcc_luffy on 2016/1/23.
 */
public class BookAdapter extends LoadMoreAdapter<BookModel.ResultSet.Book> {

    public BookAdapter(final Context context) {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, ChapterListActivity.class);
                intent.putExtra(ChapterListActivity.ID, data.get(position).Id);
                intent.putExtra(ChapterListActivity.TITLE, data.get(position).Title);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onBindHolder(RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).onBindData(data.get(position));if (onItemClickListener != null) {
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_detial, parent, false));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_comic_title)
        TextView tv_comic_title;

        @Bind(R.id.tv_comic_intro)
        TextView tv_comic_intro;

        @Bind(R.id.tv_num_last)
        TextView tv_num_last;

        @Bind(R.id.tv_comic_status)
        TextView tv_comic_status;

        @Bind(R.id.iv_zone_item)
        ImageView iv_zone_item;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void onBindData(BookModel.ResultSet.Book data) {
            tv_comic_title.setText(data.Title);
            tv_comic_intro.setText(data.Explain);
            tv_num_last.setText(data.LastChapter.Title);
            tv_comic_status.setText(data.Author);
            Glide.with(itemView.getContext())
                    .load(data.FrontCover)
                    .centerCrop()
                    .placeholder(R.color.gray)
                    .into(iv_zone_item);
        }
    }
}
