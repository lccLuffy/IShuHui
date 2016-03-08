package lcc.ishuhui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lcc.state_refresh_recyclerview.Recycler.NiceAdapter;
import com.lcc.state_refresh_recyclerview.Recycler.NiceViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import lcc.ishuhui.R;
import lcc.ishuhui.activity.PictureActivity;
import lcc.ishuhui.model.PictureModel;

/**
 * Created by lcc_luffy on 2016/2/13.
 */
public class PictureAdapter extends NiceAdapter<PictureModel.ResultsEntity> {
    LayoutInflater inflater;
    public PictureAdapter(final Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, PictureActivity.class);
                intent.putExtra("url",data.get(position).url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected NiceViewHolder onCreateNiceViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.item_picture,parent,false));
    }
    class Holder extends NiceViewHolder<PictureModel.ResultsEntity>
    {
        @Bind(R.id.picture)
        ImageView picture;
        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onBindData(PictureModel.ResultsEntity data) {
            Glide.with(context)
                    .load(data.url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(picture);
        }
    }
}
