package com.example.midprojectver2;

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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Menu1Fragment extends Fragment {
    ArrayList<ITEM> loc;
    markeradapter adapter;
    DBHelper dbHelper;
    public interface conListListener{//main과 연결하는 인터페이스
        void onmenuClick(AdapterView parent, View v, int position, long id);
    }
    conListListener mCallback=null;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback=(conListListener)context;
        dbHelper=MainActivity.dbHelper;

        if(getActivity()!=null&&getActivity() instanceof MainActivity){
           // loc=((MainActivity)getActivity()).getData();
            loc=dbHelper.getAllITEM();
            adapter = new markeradapter(getActivity(), R.layout.markers,loc);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framgent_menu, container, false);
        ListView list_view = view.findViewById(R.id.listview);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 코드 계속 ...

            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get TextView's Text.
                loc=dbHelper.getAllITEM();
                Log.e("hhh",String.valueOf(loc.get(position).get_id()));
                mCallback.onmenuClick(parent, v, loc.get(position).get_id(), id);
            }
        }) ;
        adapter.notifyDataSetChanged();
        return view;
    }
}
