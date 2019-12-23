package com.example.myapplication1214;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private Context mCtx;
    private List<Post> postList;

    public PostAdapter(Context mCtx, List<Post> postList) {
        this.mCtx = mCtx;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.layout_post, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.textViewAmount.setText("$"+post.getAmount());
        holder.textViewItem.setText(post.getItem_name());
        holder.textViewType.setText(post.getType());

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
    class PostViewHolder extends RecyclerView.ViewHolder  {

        TextView textViewItem, textViewType, textViewAmount;

        public PostViewHolder(View itemView) {
            super(itemView);

            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewAmount = itemView.findViewById(R.id.text_view_amount);
            textViewItem = itemView.findViewById(R.id.text_view_item);
            //itemView.setOnClickListener(this);

        }

//        @Override
//        public void onClick(View v) {
//            Post post = productList.get(getAdapterPosition());
//            Intent intent = new Intent(mCtx, UpdateProductActivity.class);
//            intent.putExtra("product", product);
//            mCtx.startActivity(intent);
//        }
    }
}
