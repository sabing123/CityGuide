package com.example.cityguide.Adapter.places;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityguide.R;
import com.example.cityguide.globalURL.URL;
import com.example.cityguide.model.places.trekkingplaceModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterTrekkingPlace extends RecyclerView.Adapter<AdapterTrekkingPlace.TrekkingPlacesViewHolder> {

    Context mcontext;
    List<trekkingplaceModel> trekkingplaceModelslist;

    public AdapterTrekkingPlace(Context mcontext, List<trekkingplaceModel> trekkingplaceModelslist) {
        this.mcontext = mcontext;
        this.trekkingplaceModelslist = trekkingplaceModelslist;
    }


    @NonNull
    @Override
    public TrekkingPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_trekkingplace, parent, false);
        return new AdapterTrekkingPlace.TrekkingPlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrekkingPlacesViewHolder holder, int position) {
        trekkingplaceModel trekkingplaceModel = trekkingplaceModelslist.get(position);

        holder.trekkingplacename.setText(trekkingplaceModel.getName());
        holder.trekkingplacelocation.setText(trekkingplaceModel.getLocation());
        holder.trekkingplacedescription.setText(trekkingplaceModel.getDescription());

        String imgpath = URL.imagePath + trekkingplaceModel.getImage();
        Log.e("Image path is :", "Image path is" + imgpath);
        Picasso.get().load(imgpath).into(holder.trekkingplaceimageView);
    }

    @Override
    public int getItemCount() {
        return trekkingplaceModelslist.size();
    }

    public class TrekkingPlacesViewHolder extends RecyclerView.ViewHolder {
        ImageView trekkingplaceimageView;
        TextView trekkingplacename, trekkingplacelocation, trekkingplacedescription;

        public TrekkingPlacesViewHolder(@NonNull View itemView) {
            super(itemView);

            trekkingplaceimageView = itemView.findViewById(R.id.trekkingplaceimg);
            trekkingplacename = itemView.findViewById(R.id.trekkingplacename);
            trekkingplacelocation = itemView.findViewById(R.id.trekkingplacelocation);
            trekkingplacedescription = itemView.findViewById(R.id.trekkingplacedescription);

        }
    }
}
