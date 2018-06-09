package com.people.root;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.people.R;

import static com.people.R.drawable;

/**
 * Created by Admin on 5/15/2018.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

        private DisplayImageOptions options;
        Context context;

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imgProfile, imgStar;
            TextView more, tvDesc;
            public ViewHolder(View view) {
                super(view);
                imgProfile = (ImageView) itemView.findViewById(R.id.imgProfile);
                imgStar = (ImageView) itemView.findViewById(R.id.imgStar);
                more = (TextView) itemView.findViewById(R.id.more);
                tvDesc = (TextView) itemView.findViewById(R.id.tvDesc);
            }
        }

        public PostAdapter(Context context) {
            this.context = context;
            options = new DisplayImageOptions.Builder()
                    .displayer(new CircleBitmapDisplayer())
                    .showImageOnLoading(drawable.profile)
                    .showImageForEmptyUri(drawable.profile)
                    .cacheInMemory(true).cacheOnDisk(true)
                    .build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).
                    build();

            ImageLoader.getInstance().init(config);

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_feed_item, null);

            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            ImageLoader.getInstance().displayImage("" , holder.imgProfile, options);
            holder.more.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    TextView text = (TextView)v;
                    String str = (String) context.getResources().getText(R.string.more);
                    if(text.getText().toString().equalsIgnoreCase(str)){
                        text.setText(context.getResources().getText(R.string.less));
                        holder.tvDesc.setMaxLines(Integer.MAX_VALUE);
                    }else{
                        text.setText(context.getResources().getText(R.string.more));
                        holder.tvDesc.setMaxLines(2);
                    }


                }
            });

            holder.imgStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView stars = (ImageView)v;
                    if(stars.getTag()==null){
                        stars.setTag("set");
                        stars.setImageDrawable(context.getResources().getDrawable(R.drawable.fade_stars));
                    }else{
                        stars.setTag(null);
                        stars.setImageDrawable(context.getResources().getDrawable(R.drawable.stars));
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return 10;
        }

    }
