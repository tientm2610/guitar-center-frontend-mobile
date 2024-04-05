//package com.example.guitar_center;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
////import com.example.guitar_center.adapter.FormAdapter;
//import com.example.guitar_center.adapter.ProductAdapter;
//import com.example.guitar_center.entity.Product;
//import com.example.guitar_center.services.ProductAPIServices;
//import com.example.guitar_center.view.ViewHandler;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//
//public class MainBackup extends AppCompatActivity {
//    private Dialog addProductDialog;
//    //    private FormAdapter formAdapter;
//    public RecyclerView recyclerView;
//    private ProductAdapter productAdapter;
//    EditText editTextID,editTextName,editTextUnit,editTextPrice,editTextImage,editTextDescription;
//    Button buttonSave, buttonCancel,buttonAddProduct, buttonReload, buttonEdit,buttonDelete;
//
//    Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:8080/nhaccuwebapp/rest/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();
//
//    ProductAPIServices productAPIServices = retrofit.create(ProductAPIServices.class);
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        //ket noi API
//
//
//
//
//        //ánh xạ từ produc_form.xml sang main
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View rootView = inflater.inflate(R.layout.product_form, null);
//
//        editTextID = rootView.findViewById(R.id.editTextID);
//        editTextName = rootView.findViewById(R.id.editTextName);
//        editTextUnit = rootView.findViewById(R.id.editTextUnit);
//        editTextPrice = rootView.findViewById(R.id.editTextPrice);
//        editTextImage = rootView.findViewById(R.id.editTextImage);
//        editTextDescription = rootView.findViewById(R.id.editTextDescription);
//        buttonSave = rootView.findViewById(R.id.buttonSave);
//        buttonCancel = rootView.findViewById(R.id.buttonCancel);
//
//        //ánh xạ activity_main.xml
//        buttonAddProduct = findViewById(R.id.buttonAddProduct);
//        buttonReload = findViewById(R.id.buttonReload);
//        //ánh xạ item_product.xml đến activity_main.xml
//
//        View itemView = inflater.inflate(R.layout.item_product, null);
//        buttonEdit = itemView.findViewById(R.id.buttonEdit);
//        buttonDelete = itemView.findViewById(R.id.buttonDelete);
//
//        //hien thi danh sach san pham
//        getAllProductToView();
//        //button reload
//        buttonReload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getAllProductToView();
//            }
//        });
//        // Xử lý sự kiện khi nhấn nút Thêm
//        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                LayoutInflater inflater = getLayoutInflater();
//                View dialogView = inflater.inflate(R.layout.product_form, null);
//                builder.setView(dialogView);
//
//                // Ánh xạ các EditText
//                final EditText editTextID = dialogView.findViewById(R.id.editTextID);
//                final EditText editTextName = dialogView.findViewById(R.id.editTextName);
//                final EditText editTextUnit = dialogView.findViewById(R.id.editTextUnit);
//                final EditText editTextPrice = dialogView.findViewById(R.id.editTextPrice);
//                final EditText editTextImage = dialogView.findViewById(R.id.editTextImage);
//                final EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
//
//
//                Button buttonSave = dialogView.findViewById(R.id.buttonSave);
//                Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
//
//                final AlertDialog dialog = builder.create();
//
//                buttonSave.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // Lấy thông tin từ EditText
//                        String id = editTextID.getText().toString();
//                        String name = editTextName.getText().toString();
//                        String unit = editTextUnit.getText().toString();
//                        String price = editTextPrice.getText().toString();
//                        String image = editTextImage.getText().toString();
//                        String description = editTextDescription.getText().toString();
//
//                        // Thực hiện thêm sản phẩm vào danh sách hoặc xử lý theo nhu cầu của bạn
//                        if (name.isEmpty()||id.isEmpty()||unit.isEmpty()   || price.isEmpty() || image.isEmpty()||description.isEmpty() ) {
//                            Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//
//                        }else if (!TextUtils.isDigitsOnly(unit) || !TextUtils.isDigitsOnly(price)) {
//                            // Nếu unit hoặc price không phải là số, hiển thị thông báo lỗi
//                            Toast.makeText(MainActivity.this, "Số lượng và Giá sản phẩm phải là số!!", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Call<List<Product>> call = productAPIServices.insertProduct(new Product(id,name,Integer.parseInt(unit),Double.parseDouble(price),image,description));
//                            call.enqueue(new Callback<List<Product>>() {
//                                @Override
//                                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                                    if (response.isSuccessful()) {
//                                        Toast.makeText(MainActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
//
//                                        // Lấy lại danh sách sản phẩm ngay sau khi thêm sản phẩm
//                                        getAllProductToView();
//                                        //Đong dialog
//                                        dialog.dismiss();
//                                    } else {
//                                        Toast.makeText(MainActivity.this, "Đã xảy ra lỗi về đường dẫn", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<List<Product>> call, Throwable t) {
//                                    Toast.makeText(MainActivity.this, "Failed to add product: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                        }
//
//                    }
//                });
//
//                buttonCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // Đóng dialog
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
//            }
//        });
//
//        //Su ly su kien nut sua
//        buttonEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                LayoutInflater inflater = getLayoutInflater();
//                View dialogView = inflater.inflate(R.layout.product_form, null);
//                builder.setView(dialogView);
//
//                // Ánh xạ các EditText
//                final EditText editTextID = dialogView.findViewById(R.id.editTextID);
//                final EditText editTextName = dialogView.findViewById(R.id.editTextName);
//                final EditText editTextUnit = dialogView.findViewById(R.id.editTextUnit);
//                final EditText editTextPrice = dialogView.findViewById(R.id.editTextPrice);
//                final EditText editTextImage = dialogView.findViewById(R.id.editTextImage);
//                final EditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
//
//
//                Button buttonSave = dialogView.findViewById(R.id.buttonSave);
//                Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
//
//                final AlertDialog dialog = builder.create();
//
//                buttonSave.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // Lấy thông tin từ EditText
//                        String id = editTextID.getText().toString();
//                        String name = editTextName.getText().toString();
//                        String unit = editTextUnit.getText().toString();
//                        String price = editTextPrice.getText().toString();
//                        String image = editTextImage.getText().toString();
//                        String description = editTextDescription.getText().toString();
//
//                        // Thực hiện thêm sản phẩm vào danh sách hoặc xử lý theo nhu cầu của bạn
//                        if (name.isEmpty()||id.isEmpty()||unit.isEmpty()   || price.isEmpty() || image.isEmpty()||description.isEmpty() ) {
//                            Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//
//                        }else if (!TextUtils.isDigitsOnly(unit) || !TextUtils.isDigitsOnly(price)) {
//                            // Nếu unit hoặc price không phải là số, hiển thị thông báo lỗi
//                            Toast.makeText(MainActivity.this, "Số lượng và Giá sản phẩm phải là số!!", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Call<List<Product>> call = productAPIServices.insertProduct(new Product(id,name,Integer.parseInt(unit),Double.parseDouble(price),image,description));
//                            call.enqueue(new Callback<List<Product>>() {
//                                @Override
//                                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                                    if (response.isSuccessful()) {
//                                        Toast.makeText(MainActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
//
//                                        // Lấy lại danh sách sản phẩm ngay sau khi thêm sản phẩm
//                                        getAllProductToView();
//                                        //Đong dialog
//                                        dialog.dismiss();
//                                    } else {
//                                        Toast.makeText(MainActivity.this, "Đã xảy ra lỗi về đường dẫn", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<List<Product>> call, Throwable t) {
//                                    Toast.makeText(MainActivity.this, "Failed to add product: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                        }
//
//                    }
//                });
//
//                buttonCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // Đóng dialog
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
//            }
//        });
//
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//
//    }
//
//    private  void getForm(){
//
//    };
//
//    private void insertProduct(){
//
//    }
//    private void getAllProductToView(){
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        //su dung cac chuc nang
//        ViewHandler viewHandler = new ViewHandler(this);
//
//        // su dung cu phap lambda de load view
//        viewHandler.getAllProductToView(productList -> {
//            productAdapter = new ProductAdapter(MainActivity.this, productList);
//            recyclerView.setAdapter(productAdapter);
//        });
//    }
//
//}