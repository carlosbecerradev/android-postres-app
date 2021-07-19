package com.example.proyectofinalandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;

import java.io.ByteArrayOutputStream;

public class InsertProductActivity extends AppCompatActivity {

    private Button btnOpenCamara, btnInsertProduct, btnExit;
    private EditText edtProductName, edtProductPrice;
    private ImageView ivProductImg;
    private String userUID = "";

    final int CAPTURAR_IMAGEN = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_product);

        userUID = getIntent().getStringExtra("userUid");
        edtProductName = (EditText) findViewById(R.id.edt_product_name);
        edtProductName = (EditText) findViewById(R.id.edt_product_price);
        ivProductImg = (ImageView) findViewById(R.id.iv_product_image);

        btnOpenCamara = (Button) findViewById(R.id.btnOpenCamara);
        btnOpenCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });
    }

    public void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURAR_IMAGEN);
    }

    // Tratar captura de imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURAR_IMAGEN && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
            ivProductImg.setImageBitmap(bitmap);
        }
    }

}