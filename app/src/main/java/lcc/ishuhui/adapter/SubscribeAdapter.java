package lcc.ishuhui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lcc.state_refresh_recyclerview.Recycler.NiceAdapter;
import com.lcc.state_refresh_recyclerview.Recycler.NiceViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import lcc.ishuhui.R;
import lcc.ishuhui.activity.ChapterListActivity;
import lcc.ishuhui.customview.tagview.TagImageView;
import lcc.ishuhui.model.BookModel;
import lcc.ishuhui.utils.PreferencesUtil;

/**
 * Created by lcc_luffy on 2016/1/23.
 */
public class SubscribeAdapter extends NiceAdapter<BookModel.ResultSet.Book> {

    public SubscribeAdapter(final Context context) {
        super(context);
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
    protected NiceViewHolder onCreateNiceViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscribe, parent, false));
    }

    public class ViewHolder extends NiceViewHolder<BookModel.ResultSet.Book> {
        @Bind(R.id.tv_item_title)
        TextView tv_item_title;

        @Bind(R.id.tv_item_explain)
        TextView tv_item_explain;

        @Bind(R.id.tv_item_time)
        TextView tv_item_time;

        @Bind(R.id.tv_item_author)
        TextView tv_item_author;

        @Bind(R.id.title_LastChapter)
        TextView title_LastChapter;

        @Bind(R.id.iv_cover)
        TagImageView iv_zone_item;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onBindData(BookModel.ResultSet.Book data) {
            title_LastChapter.setText(data.LastChapter.Title);
            tv_item_explain.setText(data.Explain);
            tv_item_time.setText(data.LastChapter.RefreshTimeStr);
            tv_item_title.setText(data.Title + " " + data.LastChapter.ChapterNo + "话");
            tv_item_author.setText(data.Author);
            Glide.with(context)
                    .load(data.LastChapter.FrontCover)
                    .centerCrop()
                    .placeholder(R.color.gray)
                    .into(iv_zone_item);
            boolean isSubscribed = PreferencesUtil.getInstance().getBoolean("bookId" + data.Id + "isSubscribed", false);
            if (!isSubscribed) {
                PreferencesUtil.getInstance().start().put("bookId" + data.Id + "isSubscribed", true).commit();
            }
        }
    }
}
