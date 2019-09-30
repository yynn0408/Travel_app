package com.example.midprojectver2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.maps.model.MarkerOptions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements Menu1Fragment.conListListener, Menu2Fragment.menu2listener, Menu3Fragment.imgListListener {
    ArrayList<ITEM> loc;
    static DBHelper dbHelper;
    int position;
    //FrameLayout에 각 메뉴의 fragment를 바꿔줌
    private FragmentManager fragmentManager=getSupportFragmentManager();
    //fragment
    private Menu1Fragment menu1Fragment=new Menu1Fragment();
    private Menu2Fragment menu2Fragment=new Menu2Fragment();
    private Menu3Fragment menu3Fragment=new Menu3Fragment();
    private FloatingActionButton fab;

    @Override
    public void onimgClick(AdapterView parent, View v, int position, long id) {
        Intent intent=new Intent(this,slidemain.class);
        this.position=position;
        intent.putExtra("position",this.position);
        startActivity(intent);
    }

    @Override
    public void onmenuClick(AdapterView parent, View v, int position, long id) {
        Intent intent =new Intent(this,Detail.class);
        this.position=position;
        intent.putExtra("position",this.position);
        startActivity(intent);
    }

    @Override
    public void onmarkerListener(int position) {
        Intent intent =new Intent(this,Detail.class);
        this.position=position;
        intent.putExtra("position",this.position);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loc = new ArrayList<>();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();
        dbHelper = new DBHelper( MainActivity.this, "mapDB", null, 1);
      //  dbHelper.getmInstance(MainActivity.this);
        dbHelper.testDB();
        loc=dbHelper.getAllITEM();

        try {
            Log.d("hi", "Key hash is " + getKeyHash(this));
        } catch (PackageManager.NameNotFoundException ex) {
// handle exception
        }
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,extra.class);
                int camreq = 3000;
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED||
                        ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
                        ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ){
                    requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},camreq);
                }else {
                    startActivity(intent);
                }};
        });
        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_menu1: {
                        transaction.replace(R.id.frame_layout, menu1Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu2: {
                        transaction.replace(R.id.frame_layout, menu2Fragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_menu3: {
                        transaction.replace(R.id.frame_layout, menu3Fragment).commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });


    }
    public ArrayList<ITEM> getData(){
        return loc;
    }

    @Override//권한요청에 관한 함수
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {

        Log.d("here", "req");
        Log.d("here",Integer.toString(requestCode));
        Intent cintent=new Intent(this,extra.class);

        if(requestCode==3000){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
                    startActivity(cintent);
                }
            }
            else{
                Toast.makeText(this,"권한요청을 거부했습니다.",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static String getKeyHash(final Context context)throws PackageManager.NameNotFoundException{
        PackageManager pm = context.getPackageManager();
        PackageInfo packageInfo= pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
        if (packageInfo== null) return null;
        for (Signature signature : packageInfo.signatures) {
            try { MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.w("TAG", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }

        return null;
    }


}
