package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DepartmentActivity extends AppCompatActivity implements DepartmentAdapter.DepartmentCallback {
    RecyclerView rvListCode;
    ArrayList<Department> lstDepartment;
    DepartmentAdapter departmentAdapter;
    FloatingActionButton fbAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        rvListCode = findViewById(R.id.rvListDe);
        fbAdd = findViewById(R.id.fbAddDe);
        fbAdd.setOnClickListener(view -> addUserDialog());
        // Lấy dữ liệu
        lstDepartment = DepartmentDataQuery.getAll(this);
        departmentAdapter = new DepartmentAdapter(lstDepartment);
        departmentAdapter.setCallback(this);
        // Gán adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListCode.setAdapter(departmentAdapter);
        rvListCode.setLayoutManager(linearLayoutManager);
    }



    void addUserDialog() {
        // Mở một hộp thoại để thêm User mới
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DepartmentActivity.this);
        alertDialog.setTitle("Thêm mới");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_department, null);
        alertDialog.setView(dialogView);
        EditText edName = (EditText) dialogView.findViewById(R.id.edDeName);

        alertDialog.setPositiveButton("Đồng ý", (dialog, which) -> {
            String name = edName.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(DepartmentActivity.this, "Nhập dữ liệu không hợp lệ", Toast.LENGTH_LONG).show();

            } else {
                Department de = new Department(0, name);
                long id = DepartmentDataQuery.insert(DepartmentActivity.this, de);
                if (id > 0) {
                    Toast.makeText(DepartmentActivity.this, "Thêm phòng ban thành công", Toast.LENGTH_LONG).show();
                    resetData();
                    dialog.dismiss();
                }
            }
        });
        alertDialog.setNegativeButton("Hủy", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.create();
        alertDialog.show();
    }

    void updateDepartmentDialog(Department de){
        //khởi tạo dialog để cập nhật người dùng
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(DepartmentActivity.this);
        alertDialog.setTitle("Cập nhật");
        LayoutInflater inflater=this.getLayoutInflater();
        View dialogView=inflater.inflate(R.layout.dialog_add_department,null);
        alertDialog.setView(dialogView);
        EditText edName=(EditText) dialogView.findViewById(R.id.edDeName);

        //gán dữ liệu
        edName.setText(de.getName());

        //
        alertDialog.setPositiveButton("Đồng ý",(dialog, which) -> {
            de.setName(edName.getText().toString());

            if(de.name.isEmpty()){
                Toast.makeText(DepartmentActivity.this,"Nhập dữ liệu không hợp lệ",Toast.LENGTH_LONG).show();

            }else {
                int id=DepartmentDataQuery.update(DepartmentActivity.this,de);
                if(id>0){
                    Toast.makeText(DepartmentActivity.this,"Cập nhật phòng ban thành công ",Toast.LENGTH_LONG).show();
                    resetData();
                    dialog.dismiss();
                }
            }
        });
        alertDialog.setNegativeButton("hủy",(dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.create();
        alertDialog.show();


    }
    void resetData(){
        lstDepartment.clear();
        lstDepartment.addAll(DepartmentDataQuery.getAll(DepartmentActivity.this));
        departmentAdapter.notifyDataSetChanged();
    }
    @Override
    public void onItemDeleteClicked(Department de, int position) {
        boolean rs=DepartmentDataQuery.delete(DepartmentActivity.this,de.id);
        if(rs){
            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_LONG).show();
            resetData();
        }else {
            Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemEditClicked(Department de, int position) {updateDepartmentDialog(de);

    }
}