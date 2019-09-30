package com.example.midprojectver2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class extra extends AppCompatActivity implements OnMapReadyCallback{
        private GoogleMap map;
        private DBHelper dbHelper;
        //ArrayList<ITEM> loc;
        Button post;
        EditText edittitle;
        EditText editcontent;
        MarkerOptions mOptions;
        PickImageHelper ViewHelper;
        ImageButton imageButton;
        Uri imageUri;
        int RESULT_OK=9162;
        int PICK_CAMERA=9000;
        //private SQLiteDatabase db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extraview);

        //db=openOrCreateDatabase("mapDB",MODE_PRIVATE,null);
        // dbHelper.getmInstance(this);
         dbHelper=MainActivity.dbHelper;
         imageButton=(ImageButton)findViewById(R.id.imageView);
         imageButton.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v) {
                 ViewHelper.selectImage(extra.this);
             }
         });

        post=findViewById(R.id.button);
        edittitle=findViewById(R.id.etit);
        editcontent=findViewById(R.id.eccon);
        mOptions = new MarkerOptions();



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.emap);//mapfragment 가져오기
        mapFragment.getMapAsync(this);//sync
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_OK) {
  //          Uri tmp=getIntent().getParcelableExtra(MediaStore.EXTRA_OUTPUT);
//            Log.e("tmp",tmp.toString());

            imageUri = ViewHelper.getPickImageResultUri(this, data);

       //     Log.e("extra", imageUri.toString());
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageButton.setImageBitmap(bm);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                // mOptions.title("마커 좌표");
                Double latitude = latLng.latitude; // 위도
                Double longitude = latLng.longitude; // 경도
                mOptions.position(new LatLng(latitude, longitude));
                map.addMarker(mOptions);
            }
        });
    }
    public void post(View view){
        mOptions.title(edittitle.getText().toString());
        mOptions.snippet(editcontent.getText().toString());
        //loc에 추가해주기
        ITEM item=new ITEM();
        item.setMarkerOptions(mOptions);
       // String path=ViewHelper.getrealPath(extra.this);
       Log.e(imageUri.toString(),"imageUri");
        item.set_uri(imageUri.toString());
        dbHelper.addItem(item);
        finish();
    }

}


