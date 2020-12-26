package com.example.cityguide.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cityguide.R;
import com.example.cityguide.StrictMode.strictmodeclass;
import com.example.cityguide.globalURL.URL;
import com.example.cityguide.model.hotelModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.example.cityguide.globalURL.URL.imagePath;

public class Adapter_Hotel extends RecyclerView.Adapter<Adapter_Hotel.HotelViewHolder> {

    Context mcontext;
    List<hotelModel> hotelList;
    public Adapter_Hotel(Context mcontext, List<hotelModel> hotelList) {
        this.mcontext = mcontext;
        this.hotelList = hotelList;
    }
    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_hotel,parent,false);
        return new Adapter_Hotel.HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        hotelModel hotel = hotelList.get(position);

        String imgpath = URL.imagePath + hotel.getImage();
        Log.e("Image path is :" ,"Image path is" + imgpath);
        Picasso.get().load(imgpath).into(holder.imageView);

        holder.tvhotelname.setText(hotel.getName());
        holder.tvhotellocation.setText(hotel.getLocation());
        holder.tvhotelprice.setText(hotel.getPrice());
        holder.tvhoteldescription.setText(hotel.getDescription());
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvhotelname, tvhotellocation, tvhotelprice, tvhoteldescription;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.placeimg);
            tvhotelname = itemView.findViewById(R.id.tvhotelname);
            tvhotellocation = itemView.findViewById(R.id.tvhotellocation);
            tvhotelprice = itemView.findViewById(R.id.tvhotelprice);
            tvhoteldescription = itemView.findViewById(R.id.tvhoteldescription);

        }
    }
}
