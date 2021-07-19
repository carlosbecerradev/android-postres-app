package com.example.proyectofinalandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class InsertProductActivity extends AppCompatActivity {

    private Button btnOpenCamara, btnInsertProduct, btnExit;
    private EditText edtProductName, edtProductPrice;
    private ImageView ivProductImg;
    private String userUID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_product);

        userUID = getIntent().getStringExtra("userUid");
        edtProductName = (EditText) findViewById(R.id.edt_product_name);
        edtProductName = (EditText) findViewById(R.id.edt_product_price);
        ivProductImg = (ImageView) findViewById(R.id.iv_product_image);
    }
}