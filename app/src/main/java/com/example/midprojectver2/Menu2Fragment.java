package com.example.midprojectver2;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Menu2Fragment extends Fragment {
    private GoogleMap mMap;
    ArrayList<ITEM> loc;
    private DBHelper dbHelper;
    ListView loclist;
    public Menu2Fragment() {
        // Required empty public constructor
    }
    public interface menu2listener{
        void onmarkerListener(int position);
    }
    menu2listener listener=null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener=(menu2listener)context;
        dbHelper=MainActivity.dbHelper;
        loc=dbHelper.getAllITEM();
        if(getActivity()!=null&&getActivity() instanceof Activity){
            if(getActivity()!=null&&getActivity() instanceof MainActivity){
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.framgent_menu2, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.clear(); //clear old markers
                for(int i=0;i<loc.size();i++){
                    mMap.addMarker(loc.get(i).getMarkerOptions());
                }
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        ITEM temp=dbHelper.MarkerITEM(marker);
                        Log.e("hhht",String.valueOf(temp.get_id()));
                        listener.onmarkerListener(temp.get_id());
                        return false;
                    }
                });
            }
        });


        return rootView;
    }
}
