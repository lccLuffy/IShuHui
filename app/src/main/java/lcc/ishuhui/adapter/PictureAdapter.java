package lcc.ishuhui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;
import lcc.ishuhui.R;
import lcc.ishuhui.activity.PictureActivity;
import lcc.ishuhui.model.PictureModel;

/**
 * Created by lcc_luffy on 2016/2/13.
 */
public class PictureAdapter extends LoadMoreAdapter<PictureModel.ResultsEntity> {
    LayoutInflater inflater;

    public PictureAdapter(final Context context) {
        inflater = LayoutInflater.from(context);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, PictureActivity.class);
                intent.putExtra("url", data.get(position).url);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public void onBindHolder(RecyclerView.ViewHolder holder, final int position) {
        ((Holder) holder).onBindData(data.get(position));
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
        return new Holder(inflater.inflate(R.layout.item_picture, parent, false));
    }

    class Holder extends RecyclerView.ViewHolder {
        @Bind(R.id.picture)
        ImageView picture;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBindData(PictureModel.ResultsEntity data) {
            Glide.with(itemView.getContext())
                    .load(data.url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(picture);
        }
    }
}
