package com.example.proyectofinalandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.proyectofinalandroid.model.Product;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    Button btnSignOut;
    private RecyclerView recyclerViewProducts;
    private List<Product> products = new ArrayList<>();
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

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

        btnSignOut = (Button) findViewById(R.id.btnSignOut);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
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
        products.add(new Product(5L, "Dona de Menta", "S/ 10",  R.drawable.d_menta));
    }
}