package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    ArrayList<User> lstUser;
    Context context;
    UserCallback userCallback;

    //khởi tạo tương tác với list
    public  UserAdapter(ArrayList<User>lstUser){
        this.lstUser=lstUser;
    }

    public void setCallback(UserCallback callback){this.userCallback=callback;}

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        //Add layout for View to perform user
        View userView=inflater.inflate(R.layout.layoutitem,parent,false);
        UserViewHolder viewHolder=new UserViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, int position) {
        //lấy item từng dữ liệu
        User item=lstUser.get(position);
        //gán vào item của vỉew
        holder.imAvatar.setImageBitmap(Utils.converToBitmapFromAssets(context,item.getAvatar()));
        holder.tvName.setText(item.getName() + " - " + item.getDepartname());
        //bắt sự kiên
        holder.ivDelete.setOnClickListener(view->userCallback.onItemDeleteClicked(item,position));
        holder.ivEdit.setOnClickListener(view->userCallback.onItemEditClicked(item,position));

    }

    @Override
    public int getItemCount() {
        return lstUser.size();
    }
class UserViewHolder extends RecyclerView.ViewHolder{
        ImageView imAvatar;
        TextView tvName;
        ImageView ivEdit;
        ImageView ivDelete;
        public UserViewHolder(@NonNull View itemView){
            super(itemView);
            imAvatar=itemView.findViewById(R.id.ivAvartar);
            tvName=itemView.findViewById(R.id.tvName);
            ivEdit=itemView.findViewById(R.id.ivEdit);
            ivDelete=itemView.findViewById(R.id.ivDelete);

        }
}
//hàm để thực hiện xóa , cập nhật trên dòng
 public  interface UserCallback{
        void onItemDeleteClicked(User us,int position);
        void onItemEditClicked(User us, int position);
 }


        }
