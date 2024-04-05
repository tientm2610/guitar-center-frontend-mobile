package com.example.guitar_center.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guitar_center.R;
import com.example.guitar_center.entity.Product;

import java.util.List;

public class FormAdapter {

    private List<Product> productList;
    private Context context;
    //hàm tạo, nhận danh sách dữ liệu
    public FormAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    // Phương thức ánh xạ các thành phần trong layout của dialog

    @NonNull

    public FormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_form, parent, false);
        return new FormViewHolder(view);
    }



    public void onBindViewHolder(@NonNull FormViewHolder holder, int position) {
        // Set data to form
        Product product = productList.get(position);
        holder.editTextID.setText(product.getId_product());
        holder.editTextName.setText(product.getName_product());
        holder.editTextUnit.setText((int)product.getUnit());
        holder.editTextPrice.setText((int)product.getPrice());
        holder.editTextImage.setText(product.getImage());
        holder.editTextDescription.setText(product.getDescription());

        holder.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }





    public class FormViewHolder extends RecyclerView.ViewHolder {


        EditText editTextID, editTextName, editTextUnit, editTextPrice, editTextImage, editTextDescription;
        Button buttonSave, buttonCancel;
        public FormViewHolder(@NonNull View itemView) {
            super(itemView);
            editTextID = itemView.findViewById(R.id.editTextID);
            editTextName = itemView.findViewById(R.id.editTextName);
            editTextUnit = itemView.findViewById(R.id.editTextUnit);
            editTextPrice = itemView.findViewById(R.id.editTextPrice);
            editTextImage = itemView.findViewById(R.id.editTextImage);
            editTextDescription = itemView.findViewById(R.id.editTextDescription);
            buttonSave = itemView.findViewById(R.id.buttonSave);
            buttonCancel = itemView.findViewById(R.id.buttonCancel);
        }


    }

}