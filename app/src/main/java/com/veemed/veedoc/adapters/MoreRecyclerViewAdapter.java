package com.veemed.veedoc.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.veemed.veedoc.R;

import java.util.ArrayList;

public class MoreRecyclerViewAdapter extends RecyclerView.Adapter<MoreRecyclerViewAdapter.ViewHolder>{

    private Context context;
    private ArrayList<String> optionsText = new ArrayList<>();
    private ArrayList<Drawable> iconImages = new ArrayList<>();
    private static RecyclerViewListener recyclerViewListener;

    public MoreRecyclerViewAdapter(Context context, RecyclerViewListener recyclerViewListener, ArrayList<String> optionsText, ArrayList<Drawable> iconImages) {
        this.context = context;
        this.recyclerViewListener = recyclerViewListener;
        this.optionsText = optionsText;
        this.iconImages = iconImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_recyclerview_options, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.iconImage.setImageDrawable(iconImages.get(position));
        holder.optionTextView.setText(optionsText.get(position));

    }

    @Override
    public int getItemCount() {
        return optionsText.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ConstraintLayout moreRecyclerViewOptions;
        ImageView iconImage, arrowImage;
        TextView optionTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moreRecyclerViewOptions = itemView.findViewById(R.id.more_recyclerView_options);
            arrowImage = itemView.findViewById(R.id.arrowImageView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            optionTextView = itemView.findViewById(R.id.optionTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recyclerViewListener.itemClicked(view, getLayoutPosition());
        }
    }
}
