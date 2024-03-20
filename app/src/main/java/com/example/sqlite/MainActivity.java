package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UserAdapter.UserCallback {
    RecyclerView rvListCode;
    ArrayList<User> lstUser;
    UserAdapter userAdapter;
    FloatingActionButton fbAdd;
    Spinner spinnerDepartments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvListCode = findViewById(R.id.rvList);
        fbAdd = findViewById(R.id.fbAdd);


        fbAdd.setOnClickListener(view -> addUserDialog());
        // Lấy dữ liệu
        lstUser = UserDataQuery.getAll(this);
        userAdapter = new UserAdapter(lstUser);
        userAdapter.setCallback(this);
        // Gán adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListCode.setAdapter(userAdapter);
        rvListCode.setLayoutManager(linearLayoutManager);
    }



    void loadDepartmentsIntoSpinner() {
        ArrayList<Department> departments = DepartmentDataQuery.getAll(this);
        ArrayList<Department> departmentItems = new ArrayList<>();
        for (Department department : departments) {
            Department item = new Department(department.getId(), department.getName());
            departmentItems.add(item);
        }
        ArrayAdapter<Department> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departmentItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartments.setAdapter(adapter);
    }

    void addUserDialog() {
        // Mở một hộp thoại để thêm User mới
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Thêm mới");
        LayoutInflater inflater = this.getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_add_user, null);
        spinnerDepartments = dialogView.findViewById(R.id.spinner);
        loadDepartmentsIntoSpinner();
        alertDialog.setView(dialogView);
        EditText edName = (EditText) dialogView.findViewById(R.id.edDeName);
        EditText edAvatar = (EditText) dialogView.findViewById(R.id.edAvatar);

        alertDialog.setPositiveButton("Đồng ý", (dialog, which) -> {
            String name = edName.getText().toString();
            String avatar = edAvatar.getText().toString();



            int selectedPosition = spinnerDepartments.getSelectedItemPosition();
            int de_id = -1;
            if (selectedPosition != AdapterView.INVALID_POSITION) {
                Department selectedItem = (Department) spinnerDepartments.getItemAtPosition(selectedPosition);
                de_id = selectedItem.getId();
            }


            if (name.isEmpty()) {
                Toast.makeText(MainActivity.this, "Nhập dữ liệu không hợp lệ", Toast.LENGTH_LONG).show();

            } else {
                User us = new User(0, name, avatar, de_id);
                long id = UserDataQuery.insert(MainActivity.this, us);
                if (id > 0) {
                    Toast.makeText(MainActivity.this, "Thêm người dùng thành công", Toast.LENGTH_LONG).show();
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

void updateUserDialog(User us){
        //khởi tạo dialog để cập nhật người dùng
    AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
    alertDialog.setTitle("cập nhật");
    LayoutInflater inflater=this.getLayoutInflater();
    View dialogView=inflater.inflate(R.layout.dialog_add_user,null);
    spinnerDepartments = dialogView.findViewById(R.id.spinner);
    loadDepartmentsIntoSpinner();


    alertDialog.setView(dialogView);
    EditText edName=(EditText) dialogView.findViewById(R.id.edDeName);
    EditText edAvatar=(EditText) dialogView.findViewById(R.id.edAvatar);
    //gán dữ liệu
    edName.setText(us.getName());
    edAvatar.setText(us.getAvatar());
    //
    alertDialog.setPositiveButton("Đồng ý",(dialog, which) -> {

        us.setName(edName.getText().toString());
        us.setAvatar(edAvatar.getText().toString());
        int selectedPosition = spinnerDepartments.getSelectedItemPosition();

        if (selectedPosition != AdapterView.INVALID_POSITION) {
            Department selectedItem = (Department) spinnerDepartments.getItemAtPosition(selectedPosition);
            us.de_id = selectedItem.getId();
        }



        if(us.name.isEmpty()){
            Toast.makeText(MainActivity.this,"Nhập dữ liệu không hợp lệ",Toast.LENGTH_LONG).show();

        }else {
            int id=UserDataQuery.update(MainActivity.this,us);
            if(id>0){
                Toast.makeText(MainActivity.this,"Cập nhật người dùng thành công ",Toast.LENGTH_LONG).show();
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
        lstUser.clear();
        lstUser.addAll(UserDataQuery.getAll(MainActivity.this));
        userAdapter.notifyDataSetChanged();
}
    @Override
    public void onItemDeleteClicked(User us, int position) {
                boolean rs=UserDataQuery.delete(MainActivity.this,us.id);
                if(rs){
                    Toast.makeText(this, "Xóa thành công", Toast.LENGTH_LONG).show();
                    resetData();
                }else {
                    Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_LONG).show();
                }
    }

    @Override
    public void onItemEditClicked(User us, int position) {updateUserDialog(us);

    }
}
