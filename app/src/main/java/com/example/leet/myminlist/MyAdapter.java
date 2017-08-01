package com.example.leet.myminlist;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leet on 17-7-2.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    public String[] datas=null;

    public MyAdapter(ArrayList<String> data){
        datas=new String[data.size()];
        if(data.size()!=0) {
            for (int i = 0; i < data.size(); i++) {
                datas[i] = data.get(i);
            }
        }
    }
    public MyItemClickListener myItemClickListener;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view,myItemClickListener);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.content.setText(datas[position]);
    }


    @Override
    public int getItemCount() {
        return datas.length;
    }

    public void setOnClickListener(MyItemClickListener myItemClickListener){
        this.myItemClickListener=myItemClickListener;
    }

    public void refresh(String[] str){
        datas=str;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView content;
        public TextView delete;
        public TextView done;
        public LinearLayout con;
        public MyItemClickListener myItemClickListener;
        public ViewHolder(View itemView,MyItemClickListener myItemClickListener) {
            super(itemView);
            content=itemView.findViewById(R.id.zone);
            delete=itemView.findViewById(R.id.delete);
            done=itemView.findViewById(R.id.done);
            con=itemView.findViewById(R.id.content);
            this.myItemClickListener=myItemClickListener;
            //itemView.setOnClickListener(this);
            content.setOnClickListener(this);
            delete.setOnClickListener(this);
            done.setOnClickListener(this);
            con.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            if(myItemClickListener!=null){
                if(view.equals(delete)){
                    View vg= (EasySwipeMenuLayout) view.getParent().getParent();
                    myItemClickListener.OnItemClickListener(vg,1);
                }else if(view.equals(con)){
                    myItemClickListener.OnItemClickListener(view,3);
                }
//                else{
//                    View vg=(LinearLayout)view.getParent();
//                    myItemClickListener.OnItemClickListener(vg,2);
//                }
            }
        }
    }
}
