package lcc.ishuhui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PictureActivity extends AppCompatActivity {

    ImageView touchImageView;

    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        touchImageView = new ImageView(this);
        touchImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(touchImageView);
        url = getIntent().getStringExtra("url");

        Glide.with(this).load(url)
                .placeholder(Color.GRAY)
                .fitCenter()
                .into(touchImageView);
    }
}
