package com.example.guitar_center.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guitar_center.MainActivity;
import com.example.guitar_center.R;
import com.example.guitar_center.entity.Product;
import com.example.guitar_center.services.ProductAPIServices;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    private Context context;
    private List<Product> productList;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/nhaccuwebapp/rest/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ProductAPIServices productAPIServices = retrofit.create(ProductAPIServices.class);
    MainActivity mainActivity = new MainActivity();
    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Set data to views

        holder.textViewID.setText(product.getId_product());
        holder.textViewName.setText(product.getName_product());
        holder.textViewUnit.setText(String.valueOf(product.getUnit()));
        holder.textViewPrice.setText(String.valueOf(product.getPrice()));
        holder.textViewDescription.setText(product.getDescription());

        // Load hình ảnh từ URL sử dụng Picasso
        String imagePath = "http://10.0.2.2:8080/nhaccuwebapp/img/"+product.getImage();

        Picasso.get()
                .load(imagePath)
                .into(holder.imageView);



        // Set click listeners if needed
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có thật sự muốn xóa sản phẩm?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Lấy thông tin sản phẩm được chọn
                        Product product = productList.get(holder.getAdapterPosition());
                        // Gọi API để xóa sản phẩm
                        Call<Void> call = productAPIServices.deleteProduct(product.getId_product());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    // Xóa sản phẩm thành công, hiển thị thông báo và làm mới danh sách sản phẩm
                                    Toast.makeText(context, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                    // Gọi phương thức để làm mới danh sách sản phẩm

                                } else {
                                    // Xóa sản phẩm không thành công, hiển thị thông báo lỗi
                                    Toast.makeText(context, "Xóa sản phẩm không thành công", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                // Xử lý lỗi khi gọi API
                                Toast.makeText(context, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Không làm gì cả, chỉ đóng hộp thoại
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // Lấy thông tin sản phẩm được chọn
                Product product = productList.get(holder.getAdapterPosition());

                // Tạo một đối tượng AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                // Thiết lập layout cho dialog
                View dialogView = LayoutInflater.from(context).inflate(R.layout.product_form, null);
                builder.setView(dialogView);

                // Ánh xạ các thành phần trong layout của dialog
                EditText editTextID = dialogView.findViewById(R.id.editTextID);
                EditText editTextName = dialogView.findViewById(R.id.editTextName);
                EditText editTextUnit = dialogView.findViewById(R.id.editTextUnit);
                EditText editTextPrice = dialogView.findViewById(R.id.editTextPrice);
                EditText editTextImage = dialogView.findViewById(R.id.editTextImage);
                EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
                Button buttonSave = dialogView.findViewById(R.id.buttonSave);
                Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);

                // Hiển thị thông tin sản phẩm trong các EditText
                 editTextID.setText(product.getId_product());
                editTextName.setText(product.getName_product());
                editTextUnit.setText(String.valueOf(product.getUnit()));
                editTextPrice.setText(String.valueOf(product.getPrice()));
                editTextImage.setText(product.getImage());
                editTextDescription.setText(product.getDescription());

                // Disable editTextID
                editTextID.setEnabled(false);

                // Tạo và hiển thị dialog
                AlertDialog dialog = builder.create();
                dialog.show();

                // Gắn sự kiện cho nút Save
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Lấy thông tin từ EditText
                        String id = editTextID.getText().toString();
                        String name = editTextName.getText().toString();
                        String unit = editTextUnit.getText().toString();
                        String price = editTextPrice.getText().toString();
                        String image = editTextImage.getText().toString();
                        String description = editTextDescription.getText().toString();

                        // Thực hiện sửa sản phẩm
                        if (name.isEmpty()||id.isEmpty()||unit.isEmpty()   || price.isEmpty() || image.isEmpty()||description.isEmpty() ) {
                            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                        }else if (!TextUtils.isDigitsOnly(unit) || !TextUtils.isDigitsOnly(price)) {
                            // Nếu unit hoặc price không phải là số, hiển thị thông báo lỗi
                            Toast.makeText(context, "Số lượng và giá sản phẩm phải là số !", Toast.LENGTH_SHORT).show();
                        }else {
                            Call<List<Product>> call = productAPIServices.updateProduct(new Product(id, name, Integer.parseInt(unit), Double.parseDouble(price), image, description));
                            call.enqueue(new Callback<List<Product>>() {
                                @Override
                                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(context, "Product added successfully", Toast.LENGTH_SHORT).show();

                                        // Lấy lại danh sách sản phẩm ngay sau khi thêm sản phẩm

                                        // Đóng dialog sau khi lưu dữ liệu
                                        dialog.dismiss();


                                    } else {
                                        Toast.makeText(context, "Đã xảy ra lỗi về đường dẫn", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Product>> call, Throwable t) {
                                    Toast.makeText(context, "Failed to add product: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        // Đóng dialog sau khi lưu dữ liệu
                    }
                });

                // Gắn sự kiện cho nút Cancel
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xử lý khi nhấn nút Cancel
                        // Đóng dialog
                        dialog.dismiss();
                    }
                });
            }
        });
    }


    public void setProductList(List<Product> productList) {
        // Gán danh sách sản phẩm mới vào biến productList của adapter
        this.productList = productList;

        // Thông báo cho adapter biết rằng dữ liệu đã thay đổi và cần phải cập nhật giao diện
        notifyDataSetChanged();
    }

    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewID, textViewName, textViewUnit, textViewPrice, textViewDescription;
        Button buttonDelete, buttonEdit;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewID = itemView.findViewById(R.id.textViewID);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewUnit = itemView.findViewById(R.id.textViewUnit);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);

        }
    }


}
