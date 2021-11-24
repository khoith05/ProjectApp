package com.example.android.projectmanagement;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.projectmanagement.database.DatabaseHelper;
import com.example.android.projectmanagement.database.TaskSQL;

import java.util.ArrayList;
import java.util.List;


public class SummaryFragment extends Fragment {

    public long projectid;
    public static final int SUM_PROJECT_CODE = 123;
    public DatabaseHelper db;
    public List<TaskSQL> taskSQLList;

    public SummaryFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SummaryFragment newInstance(long projectid) {
        SummaryFragment fragment = new SummaryFragment();
        fragment.projectid = projectid;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    ImageView imgResult;
    TextView txtRes, txtUndone;
    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_summary, container, false);
        imgResult=mView.findViewById(R.id.imgResult);
        txtRes=mView.findViewById(R.id.textViewRes);
        txtUndone=mView.findViewById(R.id.textViewTaskUnDone);
        db=new DatabaseHelper(getContext());
        taskSQLList=new ArrayList<>();
        taskSQLList.addAll(db.getAllTask(projectid));

        xulyTongKet();

        return mView;
    }

    public void xulyTongKet(){
        int count=0;
        for(int i =0;i<taskSQLList.size();i++){
            if(!taskSQLList.get(i).state.equals("complete")){
                count++;
            }
        }
        if(count==0){
            imgResult.setImageResource(R.drawable.res1);
            txtRes.setText("DỰ ÁN HOÀN THÀNH");
            txtRes.setTextColor(Color.GREEN);
            txtUndone.setText("Số task đã thực hiện: "+taskSQLList.size());
        }else{
            imgResult.setImageResource(R.drawable.res2);
            txtRes.setText("DỰ ÁN THẤT BẠI");
            txtRes.setTextColor(Color.RED);
            txtUndone.setText("Số task chưa hoàn thành: "+count);
        }
    }
}