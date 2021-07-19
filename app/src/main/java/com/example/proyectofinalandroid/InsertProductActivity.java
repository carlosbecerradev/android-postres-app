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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class InsertProductActivity extends AppCompatActivity {

    private Button btnOpenCamara, btnInsertProduct, btnExit;
    private EditText edtProductName, edtProductPrice;
    private ImageView ivProductImg;
    private String userUID = "";

    final int CAPTURAR_IMAGEN = 1;
    private Bitmap bitmap;

    // URL's
    private final String WS_BASE_URL = "http://192.168.1.6:8800";
    private final String WS_POST_PRODUCT_URL = WS_BASE_URL + "/api/productos";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_product);

        userUID = getIntent().getStringExtra("userUid");
        edtProductName = (EditText) findViewById(R.id.edt_product_name);
        edtProductPrice = (EditText) findViewById(R.id.edt_product_price);
        ivProductImg = (ImageView) findViewById(R.id.iv_product_image);

        btnOpenCamara = (Button) findViewById(R.id.btnOpenCamara);
        btnOpenCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });

        btnInsertProduct = (Button) findViewById(R.id.btnRegistrarProducto);
        btnInsertProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarProducto(WS_POST_PRODUCT_URL);
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

    private void registrarProducto(String ws_post_product_url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ws_post_product_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(InsertProductActivity.this, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InsertProductActivity.this, "No se pudo registrar el producto.",
                        Toast.LENGTH_SHORT).show();
                System.err.print("Error: " + error.getMessage());
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", edtProductName.getText().toString());
                params.put("price", edtProductPrice.getText().toString());
                params.put("userUid", userUID);
                params.put("imageName", imageToString(bitmap));
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

}