package com.example.dining_hall_capes.adapters;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.dining_hall_capes.*;
import com.example.dining_hall_capes.activities.DetailActivity;
import com.example.dining_hall_capes.activities.PostActivity;
import com.example.dining_hall_capes.models.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;

    //Constructor for posts adapter, takes in context and list of posts to display.
    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post  = posts.get(position);
        try {
            holder.bind(post);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addAll(List<Post> newPosts) {
        posts.addAll(newPosts);
        notifyDataSetChanged();
    }

    public void replaceAll(List<Post> newPosts) {
        clear();
        addAll(newPosts);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private TextView tvComment;
        private ImageView pfpImage;
        private RelativeLayout container;
        private TextView postTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.itemContainer);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvComment = itemView.findViewById(R.id.tvComment);
            pfpImage = itemView.findViewById(R.id.ivPFP);
            postTime = itemView.findViewById(R.id.postTime);
        }

        public void bind(Post post) throws ParseException {
            //get post text: tvComment.setText(post.getComment)
            String name = post.getAuthor().fetchIfNeeded().getUsername();
            tvUsername.setText(name);
            tvComment.setText(post.getReview());
            postTime.setText(post.getTime());
            // Getting profile picture of user who posted if needed
            ParseFile image = AppUser.getProfilePicture(post.getAuthor());
            if (image != null) {
                Glide.with(context)
                        .load(image.getUrl())
                        .transform(new CenterCrop(), new RoundedCorners(20))
                        .into(pfpImage);
            }
            container.setOnClickListener(view -> {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra(PostActivity.EXTRA_POST, Parcels.wrap(post));
                context.startActivity(i);
            });

        }

    }
}