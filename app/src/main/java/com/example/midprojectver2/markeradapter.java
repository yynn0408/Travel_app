package com.example.midprojectver2;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.MarkerOptions;

import java.net.URI;
import java.util.List;

public class markeradapter extends ArrayAdapter<ITEM>
{
    private Context context=null;
    private List<ITEM> object=null;//markeroption리스트를 받는다.
    public markeradapter(Context context, int viewid, List<ITEM>objects){
        super(context,viewid,objects);
        this.context=context;
        this.object=objects;
    }

    public int getCount(){
        if(object==null){
            return 0;
        }
        else
            return object.size();
    }
    public long getItemid(int position){
        return position;
    }//각 셀에 저장될 값
    public ITEM getItem(int position){
        if(object==null){
            return null;
        }
        return object.get(position);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        View itemView =null;//리턴할 뷰
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView=inflater.inflate(R.layout.markers,null);

        }
        else{
            itemView=convertView;
        }
        ITEM item=object.get(position);
        MarkerOptions point=item.getMarkerOptions();
        if(point!=null){
            ImageView imgview=(ImageView)itemView.findViewById(R.id.img);
            String spath=getPath(Uri.parse(item.get_suri()));
           // imgview.setImageURI(Uri.parse(item.get_suri()));
            imgview.setImageURI(Uri.parse(spath));
            TextView title=(TextView)itemView.findViewById(R.id.txttitle);
            title.setText(point.getTitle().toString());
            TextView snipp=(TextView)itemView.findViewById(R.id.txtsnip);
            snipp.setText(point.getSnippet().toString());
        }
        return itemView;
    }
    public String getPath(Uri uri) {
        String result;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = uri.getPath();

        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


}
