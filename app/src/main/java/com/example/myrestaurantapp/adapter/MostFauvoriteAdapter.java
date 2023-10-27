package com.example.myrestaurantapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myrestaurantapp.R;
import com.example.myrestaurantapp.domain.FauvoritesDomain;

import java.util.ArrayList;

public class MostFauvoriteAdapter extends RecyclerView.Adapter<MostFauvoriteAdapter.ViewHolder>
{
    ArrayList<FauvoritesDomain> fauvoritesDomain;

    public MostFauvoriteAdapter(ArrayList<FauvoritesDomain> fauvoritesDomains) {
   this.fauvoritesDomain = fauvoritesDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_most_famous,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.time.setText(fauvoritesDomain.get(position).getTime() +" min");
        holder.title.setText(fauvoritesDomain.get(position).getTitle());
        holder.star.setText(String.valueOf(fauvoritesDomain.get(position).getStar()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(fauvoritesDomain.get(position).getPic() , "drawable" , holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return fauvoritesDomain.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
    TextView title,time,star;
    ImageView pic;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.title);
            pic = itemView.findViewById(R.id.pic);
            star = itemView.findViewById(R.id.star);
            time = itemView.findViewById(R.id.time);

        }
    }
}
