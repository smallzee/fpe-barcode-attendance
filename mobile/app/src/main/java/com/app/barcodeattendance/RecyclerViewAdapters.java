package com.app.barcodeattendance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class RecyclerViewAdapters extends RecyclerView.Adapter<RecyclerViewAdapters.MyViewHolder>{

    Context mContext;
    private List<Lists> mData;

    public RecyclerViewAdapters(Context mContext, List<Lists> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        android.view.View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lists, parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.st_matric.setText(((Lists) mData.get(position)).getMatric());
        holder.st_name.setText(((Lists) mData.get(position)).getName() +" "+ ((Lists) mData.get(position)).getLevel());

        String is_click = ((Lists) mData.get(position)).getIs_click();

        Transformation transformation = new RoundedTransformationBuilder()
            .cornerRadiusDp(50)
            .oval(true)
            .build();

        Picasso.get().load(mData.get(position).getImage()).transform(transformation).into(holder.st_image);

        Bundle bundle = new Bundle();
        bundle.putString("view_id", ((Lists) mData.get(position)).getId());


        if (is_click.equals("view_attendance")){
            holder.click.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {

                    Intent intent = new Intent(v.getContext(), Scan_attendance_barcode.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public RecyclerViewAdapters(List<Lists> mData){
        this.mData = mData;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout click;
        private ImageView st_image;
        private TextView st_matric;
        private TextView st_name;

        public MyViewHolder(@NonNull android.view.View itemView) {

            super(itemView);

            st_matric = (TextView) itemView.findViewById(R.id.st_matric);
            st_name = (TextView) itemView.findViewById(R.id.st_name);
            st_image = (ImageView) itemView.findViewById(R.id.st_image);
            click = (LinearLayout) itemView.findViewById(R.id.click);

        }
    }

}
