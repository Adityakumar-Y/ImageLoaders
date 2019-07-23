package com.example.imageloaders;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgPicasso, imgGlide;
    private Button btnLoad, btnGif;
    private ProgressBar pbPicasso, pbGlide;
    private TextView tvPicasso, tvGlide;
    long startTime, elapsedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        imgPicasso = (ImageView) findViewById(R.id.imgPicasso);
        imgGlide = (ImageView) findViewById(R.id.imgGlide);
        pbPicasso = (ProgressBar) findViewById(R.id.pbPicasso);
        pbPicasso.setVisibility(View.GONE);
        pbGlide = (ProgressBar) findViewById(R.id.pbGlide);
        pbGlide.setVisibility(View.GONE);
        tvPicasso = (TextView)findViewById(R.id.tvPicasso);
        tvPicasso.setVisibility(View.GONE);
        tvGlide = (TextView) findViewById(R.id.tvGlide);
        tvGlide.setVisibility(View.GONE);
        btnLoad = (Button) findViewById(R.id.btnLoad);
        btnGif = (Button) findViewById(R.id.btnGif);

        btnLoad.setOnClickListener(this);
        btnGif.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.btnLoad:
                resetImages();
                showProgressBar();
                startTime = System.currentTimeMillis();
                Log.d("MainActivity", "Load");
                loadImgPicasso();
                loadImgGlide();
                break;
            case R.id.btnGif:
                resetImages();
                showProgressBar();
                Log.d("MainActivity", "Load Gif");
                loadGifPicasso();
                loadGifGlide();
                break;
        }

    }

    private void resetImages() {
        imgGlide.setImageResource(0);
        imgPicasso.setImageResource(0);
    }

    private void loadGifGlide() {
        Glide.with(this)
                .load("http://image.noelshack.com/fichiers/2014/38/1410967177-dragonballzgif-0.gif")
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        pbGlide.setVisibility(View.GONE);
                        tvGlide.setVisibility(View.VISIBLE);
                        Log.d("Glide Exception : ", e.toString());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        pbGlide.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imgGlide);
    }

    private void loadGifPicasso() {
        Picasso.with(this)
                .load("http://image.noelshack.com/fichiers/2014/38/1410967177-dragonballzgif-0.gif")
                .into(imgPicasso, new Callback() {
                    @Override
                    public void onSuccess() {
                        pbPicasso.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        pbPicasso.setVisibility(View.GONE);
                        tvPicasso.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void showProgressBar() {
        pbPicasso.setVisibility(View.VISIBLE);
        pbGlide.setVisibility(View.VISIBLE);
    }


    private void loadImgPicasso() {
        Picasso.with(MainActivity.this)
                .load("https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png")
                .centerCrop()
                .fit()
                .into(imgPicasso, new Callback() {
                    @Override
                    public void onSuccess() {
                        pbPicasso.setVisibility(View.GONE);
                        elapsedTime = System.currentTimeMillis() - startTime;
                        Log.i("Picasso Time:", elapsedTime+ " ms");
                    }

                    @Override
                    public void onError() {
                        pbPicasso.setVisibility(View.GONE);
                        tvPicasso.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void loadImgGlide() {
        Glide.with(MainActivity.this)
                .load("https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png")
                .centerCrop()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        pbGlide.setVisibility(View.GONE);
                        tvGlide.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        pbGlide.setVisibility(View.GONE);
                        elapsedTime = System.currentTimeMillis() - startTime;
                        Log.i("Glide Time:", elapsedTime+ " ms");
                        return false;
                    }
                })
                .into(imgGlide);
    }

}
