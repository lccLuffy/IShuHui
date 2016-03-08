package lcc.ishuhui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lcc.state_refresh_recyclerview.Recycler.NiceAdapter;
import com.lcc.state_refresh_recyclerview.Recycler.NiceViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import lcc.ishuhui.R;
import lcc.ishuhui.activity.WebActivity;
import lcc.ishuhui.constants.API;
import lcc.ishuhui.model.ChapterListModel;

/**
 * Created by lcc_luffy on 2016/1/23.
 */
public class ChapterListAdapter extends NiceAdapter<ChapterListModel.ReturnEntity.Chapter> {

    public ChapterListAdapter(final Context context) {
        super(context);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra(WebActivity.URL, API.URL_IMG_CHAPTER+data.get(position).Id);
                intent.putExtra(WebActivity.TITLE, data.get(position).Title);
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected NiceViewHolder onCreateNiceViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detial_book,parent,false));
    }


    class ViewHolder extends NiceViewHolder<ChapterListModel.ReturnEntity.Chapter>
    {
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
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onBindData(ChapterListModel.ReturnEntity.Chapter data) {
            tv_comic_title.setText(data.Title);
            tv_comic_intro.setText(data.RefreshTimeStr);
            tv_comic_status.setText(data.Sort+"话");
            Glide.with(context)
                    .load(data.FrontCover)
                    .centerCrop()
                    .placeholder(R.color.gray)
                    .into(iv_zone_item);
        }
    }
}
