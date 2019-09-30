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
import android.widget.GridView;

import java.util.ArrayList;

public class Menu3Fragment extends Fragment {

    ArrayList<ITEM> loc;
    imageadapter adapter;
    DBHelper dbHelper;
   // public imageadapter mCustomImageAdapter;
    public interface imgListListener{//main과 연결하는 인터페이스
        void onimgClick(AdapterView parent, View v, int position, long id);
    }
    imgListListener iCallback=null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iCallback=(imgListListener)context;
        dbHelper=MainActivity.dbHelper;

        if(getActivity()!=null&&getActivity() instanceof MainActivity){
            // loc=((MainActivity)getActivity()).getData();
            loc=dbHelper.getAllITEM();
            adapter = new imageadapter(getActivity(), R.layout.image,loc);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.framgent_menu3,container,false);
        GridView mGridView=rootView.findViewById(R.id.gridview);
        mGridView.setAdapter(adapter);
        Log.e("hihihihihi","hihihihi");
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 코드 계속 ...
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                loc=dbHelper.getAllITEM();
                Log.e("hhh",String.valueOf(loc.get(position).get_id()));
                iCallback.onimgClick(parent, v, loc.get(position).get_id(), id);
            }
        }) ;

        return rootView;
    }



}