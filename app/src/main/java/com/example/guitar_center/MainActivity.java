package com.example.guitar_center;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.guitar_center.adapter.FormAdapter;
import com.example.guitar_center.adapter.FormAdapter;
import com.example.guitar_center.adapter.ProductAdapter;
import com.example.guitar_center.entity.Product;
import com.example.guitar_center.services.ProductAPIServices;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity  {

    private RecyclerView recyclerView;
    ProductAdapter productAdapter;
    private EditText editTextID, editTextName, editTextUnit, editTextPrice, editTextImage, editTextDescription;
    private Button buttonSave, buttonCancel;


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/nhaccuwebapp/rest/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ProductAPIServices productAPIServices = retrofit.create(ProductAPIServices.class);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ RecyclerView từ layout
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //load product
        getProductToView();

        //chức năng hiện form thêm sản phẩm
        Button buttonAddProduct = findViewById(R.id.buttonAddProduct);
        Button buttonReload = findViewById(R.id.buttonReload);
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFormAdd();
            }
        });

        //button reload
        buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProductToView();
            }
        });


    }

    public void getProductToView() {

        // Gọi phương thức API để lấy danh sách sản phẩm
        // yeu cau api va hien thi du lieu
        Call<List<Product>> call = productAPIServices.getAllProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> productList = response.body();
                    productAdapter = new ProductAdapter(MainActivity.this, productList);
                    recyclerView.setAdapter(productAdapter);


                } else {
                    Toast.makeText(MainActivity.this, "Lấy sản phẩm thất bại !!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lấy sản phẩm thất bại !!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private  void showFormAdd(){
        // Tạo một đối tượng AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        // Thiết lập layout cho dialog
        View dialogView = inflater.inflate(R.layout.product_form, null);
        builder.setView(dialogView);
        // Hiển thị dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        // Ánh xạ các EditText
        final EditText editTextID = dialogView.findViewById(R.id.editTextID);
        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final EditText editTextUnit = dialogView.findViewById(R.id.editTextUnit);
        final EditText editTextPrice = dialogView.findViewById(R.id.editTextPrice);
        final EditText editTextImage = dialogView.findViewById(R.id.editTextImage);
        final EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);

        buttonSave = dialogView.findViewById(R.id.buttonSave);
        buttonCancel = dialogView.findViewById(R.id.buttonCancel);
        // Gắn sự kiện cho các nút
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

                // Thực hiện thêm sản phẩm vào danh sách hoặc xử lý theo nhu cầu của bạn
                if (name.isEmpty()||id.isEmpty()||unit.isEmpty()   || price.isEmpty() || image.isEmpty()||description.isEmpty() ) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                }else if (!TextUtils.isDigitsOnly(unit) || !TextUtils.isDigitsOnly(price)) {
                    // Nếu unit hoặc price không phải là số, hiển thị thông báo lỗi
                    Toast.makeText(MainActivity.this, "Số lượng và Giá sản phẩm phải là số!!", Toast.LENGTH_SHORT).show();
                }else {
                    Call<List<Product>> call = productAPIServices.insertProduct(new Product(id, name, Integer.parseInt(unit), Double.parseDouble(price), image, description));
                    call.enqueue(new Callback<List<Product>>() {
                        @Override
                        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();

                                // Lấy lại danh sách sản phẩm ngay sau khi thêm sản phẩm
//                                getProductToView();

                                // Đóng dialog sau khi lưu dữ liệu
                                dialog.dismiss();

                            } else {
                                Toast.makeText(MainActivity.this, "Đã xảy ra lỗi về đường dẫn", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Product>> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Failed to add product: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi nhấn nút Cancel
                // Đóng dialog
                dialog.dismiss();
            }
        });
    }


}
