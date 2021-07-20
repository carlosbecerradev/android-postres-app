package com.example.proyectofinalandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.proyectofinalandroid.model.Product;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private Button btnSearchProduct, btnInsertProduct;
    private EditText edtCodigoProducto;
    private RecyclerView recyclerViewProducts;
    private List<Product> products = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    private String userUid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        fillProducts();

        recyclerViewProducts = findViewById(R.id.rv_products);

        lManager = new LinearLayoutManager(this);
        recyclerViewProducts.setLayoutManager(lManager);

        adapter = new RecyclerViewAdapter(products);
        recyclerViewProducts.setAdapter(adapter);

        // Buscar Producto
        edtCodigoProducto = (EditText) findViewById(R.id.edt_product_id);
        btnSearchProduct = (Button) findViewById(R.id.btnBuscar);
        btnSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductCollapsingToolbarActivity.class);
                intent.putExtra("productId", edtCodigoProducto.getText().toString());
                intent.putExtra("userUid", userUid);
                startActivity(intent);
                finish();
            }
        });

        // Registrar Producto
        userUid = getIntent().getStringExtra("userUid");
        btnInsertProduct = (Button) findViewById(R.id.btnNuevoProducto);
        btnInsertProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InsertProductActivity.class);
                intent.putExtra("userUid", userUid);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signOut() {
        // [START auth_sign_out]
        FirebaseAuth.getInstance().signOut();
        // [END auth_sign_out]
        com.facebook.login.LoginManager.getInstance().logOut();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void fillProducts() {
        products.add(new Product(1L, "Dona de Cafe", "S/ 10", R.drawable.d_cafe));
        products.add(new Product(2L, "Dona de Miel", "S/ 10", R.drawable.d_miel));
        products.add(new Product(3L, "Dona de Chispas", "S/ 10", R.drawable.d_choco_crispi));
        products.add(new Product(4L, "Dona de Chocolate", "S/ 10", R.drawable.d_choco));
        products.add(new Product(5L, "Dona de Menta", "S/ 10", R.drawable.d_menta));
    }

    // Añadiendo App bar - menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_bar_item_cerrar_sesion) {
            signOut();
        }

        return super.onOptionsItemSelected(item);
    }
}