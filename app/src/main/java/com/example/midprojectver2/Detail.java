package com.example.midprojectver2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.LocationTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Detail extends AppCompatActivity implements OnMapReadyCallback {
    ImageView img;
    TextView snip;
    TextView tit;
    ArrayList<ITEM> loc;
    MarkerOptions mOptions;
    int position;
    private GoogleMap map;
    private DBHelper dbHelper;
    Button bot1;
    Button bot2;
    ITEM item;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datail);
        img=findViewById(R.id.fab);
        snip=findViewById(R.id.dsnip);
        tit=findViewById(R.id.dtitle);
        bot1=findViewById(R.id.button2);
        bot2=findViewById(R.id.button3);
        Intent intent=getIntent();
        position=intent.getIntExtra("position",1);
        dbHelper=MainActivity.dbHelper;
        item=dbHelper.getSELITEM(position);
        img.setImageURI(Uri.parse(item.get_suri()));
        snip.setText(item.get_snip());
        tit.setText(item.get_title());
        mOptions = new MarkerOptions();
        mOptions=item.getMarkerOptions();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dmap);//mapfragment 가져오기
        mapFragment.getMapAsync(this);//sync
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        map.addMarker(mOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(mOptions.getPosition()));
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
    }
    public void delete(View view){
        dbHelper.deleteITEM(position);
        finish();
    }
    public void share(View view){

        AlertDialog.Builder ab = new AlertDialog.Builder(Detail.this);
        ab.setMessage("공유하시겠습니까?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LocationTemplate params = LocationTemplate.newBuilder(getAddress(mOptions.getPosition().latitude,mOptions.getPosition().longitude),
                        ContentObject.newBuilder(mOptions.getTitle(),
                                "http://www.kakaocorp.com/images/logo/og_daumkakao_151001.png",
                                LinkObject.newBuilder()
                                        .setWebUrl("https://developers.kakao.com")
                                        .setMobileWebUrl("https://developers.kakao.com")
                                        .build())
                                .setDescrption(mOptions.getSnippet())
                                .build())
                        .setAddressTitle(mOptions.getTitle())
                        .build();

                Map<String, String> serverCallbackArgs = new HashMap<String, String>();
                serverCallbackArgs.put("user_id", "${current_user_id}");
                serverCallbackArgs.put("product_id", "${shared_product_id}");

                KakaoLinkService.getInstance().sendDefault(Detail.this, params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Logger.e(errorResult.toString());
                    }

                    @Override
                    public void onSuccess(KakaoLinkResponse result) {
                        // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
                    }
                });
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //노
            }
        });
        AlertDialog alert = ab.create();
        alert.setTitle("공유");
        alert.show();
    }
    public String getAddress(double lat, double lng){
        String address=null;

        Geocoder geocoder=new Geocoder(this, Locale.getDefault());
        List<Address> list=null;
        try{
            list=geocoder.getFromLocation(lat,lng,1);
        }catch (IOException e){
            e.printStackTrace();
        }
        if(list==null){
            Log.e("getAddress","주소데이터 얻기 실패");
            return null;
        }
        if(list.size()>0){
            Address addr=list.get(0);
            address=addr.getCountryName()+" "
                    +addr.getAdminArea()+" "
                    +addr.getLocality()+" "
                    +addr.getThoroughfare()+" "
                    +addr.getFeatureName();
        }
        return address;
    }

}
