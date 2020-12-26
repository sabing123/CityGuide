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
import com.example.cityguide.model.places.famousPlaceModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterFamousPlaces extends RecyclerView.Adapter<AdapterFamousPlaces.FamousPlacesViewHolder>{
    Context mcontext;
    List<famousPlaceModel> famousPlaceModelslist;

    public AdapterFamousPlaces(Context mcontext, List<famousPlaceModel> famousPlaceModelslist)
    {
        this.mcontext = mcontext;
        this.famousPlaceModelslist = famousPlaceModelslist;
    }

    @NonNull
    @Override
    public FamousPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_famousplace,parent,false);
        return new FamousPlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FamousPlacesViewHolder holder, int position) {

        famousPlaceModel famousPlaceModel = famousPlaceModelslist.get(position);

        holder.tvplacename.setText(famousPlaceModel.getName());
        holder.tvplacelocation.setText(famousPlaceModel.getLocation());
        holder.tvplacedescription.setText(famousPlaceModel.getDescription());

        String imgpath = URL.imagePath + famousPlaceModel.getImage();
        Log.e("Image path is :" ,"Image path is" + imgpath);
        Picasso.get().load(imgpath).into(holder.placeimageView);

    }

    @Override
    public int getItemCount() {
        return famousPlaceModelslist.size();
    }

    public class FamousPlacesViewHolder extends RecyclerView.ViewHolder{
        ImageView placeimageView;
        TextView tvplacename,tvplacelocation,tvplacedescription;
        public FamousPlacesViewHolder(@NonNull View itemView) {
            super(itemView);

            placeimageView = itemView.findViewById(R.id.placeimg);
            tvplacename = itemView.findViewById(R.id.tvname);
            tvplacelocation = itemView.findViewById(R.id.tvlocation);
            tvplacedescription = itemView.findViewById(R.id.tvdescription);

        }
    }
}
