package com.example.midprojectver2;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.net.URI;

public class ITEM {
    String title;String snip;Double lat;Double longi;
    int id;
    String suri;
    //사진
    MarkerOptions markerOptions;
    public MarkerOptions getMarkerOptions(){
        markerOptions=new MarkerOptions();
        markerOptions.title(title);
        markerOptions.snippet(snip);
        markerOptions.position(new LatLng(lat,longi));
        return markerOptions;
    }
    public int get_id(){
        return id;
    }
    public String get_title(){
        return title;
    }
    public String get_snip(){
        return snip;
    }
    public Double get_lat(){
        return lat;
    }
    public Double get_longi(){
        return longi;
    }
    public String get_suri(){return suri;}

    public void set_title(String m){
        title=m;
    }
    public void set_snip(String m){
        snip=m;
    }
    public void set_lat(Double m){
        lat=m;
    }
    public void set_longi(Double m){
        longi=m;
    }
    public void set_id(int i){
        id=i;
    }
    public void set_uri(String i){
        suri=i;
    }
    public void setMarkerOptions(MarkerOptions m){//cli에서 사용
        markerOptions=m;
        title=m.getTitle();
        snip=m.getSnippet();
        lat=m.getPosition().latitude;
        longi=m.getPosition().longitude;
    }

}
