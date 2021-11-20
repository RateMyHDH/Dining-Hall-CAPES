package com.example.dining_hall_capes;
import com.example.dining_hall_capes.*;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dining_hall_capes.models.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

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
    Post post = posts.get(position);
    holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvUsername;
        private TextView tvComment;
        private ImageView pfpImage;

    public ViewHolder(@NonNull View itemView){
        super(itemView);
        tvUsername = itemView.findViewById(R.id.tvUsername);
        tvComment = itemView.findViewById(R.id.tvComment);
        pfpImage = itemView.findViewById(R.id.ivPFP);
    }
    public void bind(Post post){
        //get post text: tvComment.setText(post.getComment)
        tvUsername.setText(post.getAuthor().getUsername());
        ParseFile image = post.getImage();
        if(image != null){
            Glide.with(context).load(post.getImage().getUrl()).into(pfpImage);
        }
    }

    }




}
