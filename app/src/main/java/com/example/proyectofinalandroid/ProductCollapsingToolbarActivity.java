package com.example.proyectofinalandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.proyectofinalandroid.custom.CustomVolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductCollapsingToolbarActivity extends AppCompatActivity {

    // URL's
    private final String WS_BASE_URL = "http://192.168.1.6:8800";
    private final String WS_GET_PRODUCT_URL = WS_BASE_URL + "/api/productos";

    private String productId = "";

    // Volley
    private RequestQueue requestQueue;

    // imagen
    private NetworkImageView imageView;
    private ImageLoader imageLoader;

    private TextView tvName, tvPrice, tvUserUid, tvId;
    private Button btnRetroceder;
    private String userUID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_collapsing_toolbar);
        // Esconder ActionBar
        getSupportActionBar().hide();

        // Obteniendo el codigo del producto enviado por el anterior Activity
        productId = getIntent().getStringExtra("productId");
        // Obteniendo el User UID del login
        userUID = getIntent().getStringExtra("userUid");

        //
        tvName = (TextView) findViewById(R.id.tv_product_name);
        tvPrice = (TextView) findViewById(R.id.tv_product_price);
        tvUserUid = (TextView) findViewById(R.id.tv_product_userUID);
        tvId = (TextView) findViewById(R.id.tv_product_id);
        imageView = (NetworkImageView) findViewById(R.id.v_product_image);

        // Realizar petición
        fetchProductById(WS_GET_PRODUCT_URL + "/" + productId);

        // Retroceder de activity
        btnRetroceder = (Button) findViewById(R.id.btnRetroceder);
        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                intent.putExtra("userUid", userUID);
                startActivity(intent);
                finish();
            }
        });

    }

    private void fetchProductById(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(ProductCollapsingToolbarActivity.this,
                        "Producto de ID: " + productId + " fue encontrado.",
                        Toast.LENGTH_SHORT).show();
                fillComponents(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductCollapsingToolbarActivity.this, "No se encontró el producto.",
                        Toast.LENGTH_SHORT).show();
                System.err.print("Error: " + error.getMessage());
            }
        });

        // Para que se ejecute la peticion
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void fillComponents(JSONObject response) {
        try {
            tvName.setText(response.getString("name"));
            tvPrice.setText("S/ " + response.getString("price"));
            tvUserUid.setText("UID: " + response.getString("userUid"));
            tvId.setText("Id: " + response.getString("id"));

            // Ruta de imagen guardada en el servidor web
            String url = WS_BASE_URL + "/" + response.getString("imageName");

            // Realizar petición para traer la imagen del servidor web
            imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                    .getImageLoader();
            imageLoader.get(url, ImageLoader.getImageListener(imageView,
                    R.drawable.d_cafe, android.R.drawable
                            .ic_dialog_alert));
            imageView.setImageUrl(url, imageLoader);

        } catch (JSONException e) {
            Log.e("TAG", "Error de parsing: " + e.getMessage());
        }

    }


}