package com.example.midprojectver2;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.List;

public class imageadapter extends ArrayAdapter<ITEM>
{
    private Context context=null;
    private List<ITEM> object=null;//markeroption리스트를 받는다.
    private Bitmap bm;
    public imageadapter(Context context, int viewid, List<ITEM>objects){
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
            itemView=inflater.inflate(R.layout.image,null);
            //itemView=(ImageView)convertView;
           // itemView=new ImageView(context);
        }
        else{
            itemView=(View)convertView;
        }
        ITEM item=object.get(position);

        MarkerOptions point=item.getMarkerOptions();
        if(point!=null){
            ImageView imgview=(ImageView)itemView.findViewById(R.id.gimg);
            imgview.setPadding(8,8,8,8);
            imgview.setScaleType(ImageView.ScaleType.CENTER_CROP);
           // bm=BitmapFactory.decodeFile(item.suri);
          //  Bitmap mThumbnail=ThumbnailUtils.extractThumbnail(bm,300,300);
           // imgview.setLayoutParams(new GridLayout.LayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,GridView.LayoutParams.MATCH_PARENT)));
           // imgview.setImageBitmap(mThumbnail);


            Log.e("uri--",item.get_suri());
            String spath=getPath(Uri.parse(item.get_suri()));
            //  imgview.setImageURI(Uri.parse(item.get_suri()));

            Log.e("spath",spath);
            //imgview.setImageURI(Uri.parse(item.get_suri()));
            imgview.setImageURI(Uri.parse(spath));


        }
        return itemView;


        /*ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mcontext);
        } else {
            imageView = (ImageView) convertView;
        }
        bm = BitmapFactory.decodeFile(mBasePath + File.separator + mImgList[position]);
        Bitmap mThumbnail = ThumbnailUtils.extractThumbnail(bm, 300, 300);
        imageView.setPadding(8, 8, 8, 8);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT));
        imageView.setImageBitmap(mThumbnail);
        return imageView;*/

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    public String getPath(Uri uri) {
        String result;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        //getRealPathFromURI(activity, outputFileUri);
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


}
