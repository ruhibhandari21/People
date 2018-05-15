package com.people.root;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imgProfile;
            public ViewHolder(View view) {
                super(view);
                imgProfile = (ImageView) itemView.findViewById(R.id.imgProfile);
            }
        }

        public PostAdapter(Context context) {
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
        public void onBindViewHolder(ViewHolder holder, final int position) {

            ImageLoader.getInstance().displayImage("" , holder.imgProfile, options);

        }

        @Override
        public int getItemCount() {
            return 10;
        }

    }
