package com.example.android.projectmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.example.android.projectmanagement.database.DatabaseHelper;
import com.example.android.projectmanagement.database.EmployeeSQL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Employee#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Employee extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final int EDIT_ACTIVITY=3;
    private EmployeeAdapter employeeAdapter;
    private RecyclerView recyclerView;
    private List<EmployeeSQL> employeeSQLlist;

    private DatabaseHelper databaseHelper;

    public Employee() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Employee.
     */
    // TODO: Rename and change types and number of parameters
    public static Employee newInstance(String param1, String param2) {
        Employee fragment = new Employee();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }
    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_employee, container, false);

        Toolbar toolbar= (Toolbar) view.findViewById(R.id.EmployeeToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        imageView = (ImageView) view.findViewById(R.id.addEmployee);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                boolean edit=false;
                Intent intent =new Intent(getActivity(), EditEmployee.class );
                intent.putExtra("edit",edit);
                startActivityForResult(intent,EDIT_ACTIVITY);
            }
        });
        // Inflate the layout for this fragment
        recyclerView=view.findViewById(R.id.EmployeeRecyclerView);
        employeeSQLlist=new ArrayList<>();


        databaseHelper=new DatabaseHelper(getActivity());
        employeeSQLlist.addAll(databaseHelper.getAlLEmployee());



        employeeAdapter= new EmployeeAdapter(getActivity(),employeeSQLlist);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(employeeAdapter);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode==EDIT_ACTIVITY){
            if(resultCode== Activity.RESULT_OK){
                if (data.getBooleanExtra("success",true)){
                    employeeSQLlist.clear();
                    employeeSQLlist.addAll(databaseHelper.getAlLEmployee());
                    employeeAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        employeeSQLlist.clear();
        employeeSQLlist.addAll(databaseHelper.getAlLEmployee());
        employeeAdapter.notifyDataSetChanged();
    }
}