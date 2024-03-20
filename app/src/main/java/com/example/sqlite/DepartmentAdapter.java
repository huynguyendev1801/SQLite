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

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder> {
    ArrayList<Department> lstDepart;
    Context context;
    DepartmentAdapter.DepartmentCallback departmentCallback;

    //khởi tạo tương tác với list
    public  DepartmentAdapter(ArrayList<Department>lstDepart){
        this.lstDepart=lstDepart;
    }

    public void setCallback(DepartmentAdapter.DepartmentCallback callback){this.departmentCallback=callback;}


    @NonNull
    @Override
    public DepartmentAdapter.DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View departmentView=inflater.inflate(R.layout.layoutitemtext,parent,false);
        DepartmentAdapter.DepartmentViewHolder viewHolder=new DepartmentAdapter.DepartmentViewHolder(departmentView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull DepartmentViewHolder holder, int position) {
        //lấy item từng dữ liệu
        Department item=lstDepart.get(position);
        //gán vào item của vỉew

        holder.tvName.setText(item.getName());
        //bắt sự kiên
        holder.ivDelete.setOnClickListener(view->departmentCallback.onItemDeleteClicked(item,position));
        holder.ivEdit.setOnClickListener(view->departmentCallback.onItemEditClicked(item,position));

    }


    class DepartmentViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        ImageView ivEdit;
        ImageView ivDelete;
        public DepartmentViewHolder(@NonNull View itemView){
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            ivEdit=itemView.findViewById(R.id.ivEdit);
            ivDelete=itemView.findViewById(R.id.ivDelete);

        }
    }

    @Override
    public int getItemCount() {
        return lstDepart.size();
    }

    //hàm để thực hiện xóa , cập nhật trên dòng
    public  interface DepartmentCallback{
        void onItemDeleteClicked(Department department,int position);
        void onItemEditClicked(Department department, int position);
    }
}
