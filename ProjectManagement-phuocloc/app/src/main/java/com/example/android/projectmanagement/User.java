package com.example.android.projectmanagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.projectmanagement.database.DatabaseHelper;
import com.example.android.projectmanagement.database.UserSQL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link User#newInstance} factory method to
 * create an instance of this fragment.
 */
public class User extends Fragment {


    TextView nameView;
    TextView phoneView;
    TextView emailView;
    TextView addressView;
    ImageView avatar;
    public User() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment User.
     */
    // TODO: Rename and change types and number of parameters
    public static User newInstance(String param1, String param2) {
        User fragment = new User();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("User");
        View view= inflater.inflate(R.layout.fragment_user, container, false);
        ImageView imageView= view.findViewById(R.id.editUser);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),UserEdit.class);
                startActivity(intent);
            }
        });

        DatabaseHelper user = new DatabaseHelper(getActivity());
        UserSQL usersql = user.getUser();
        nameView= view.findViewById(R.id.UserName);
        nameView.setText(usersql.name);
        phoneView = view.findViewById(R.id.UserPhoneNumber);
        phoneView.setText(usersql.phone);
        emailView = view.findViewById(R.id.UserEmail);
        emailView.setText(usersql.email);
        addressView = view.findViewById(R.id.UserAddress);
        addressView.setText(usersql.address);
        avatar= (ImageView) view.findViewById(R.id.UserAvatar);
        if (usersql.img!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(usersql.img,0,usersql.img.length);
            avatar.setImageBitmap(bitmap);
        }

        return view;

    }
    @Override
    public void onResume() {
        super.onResume();
        DatabaseHelper user = new DatabaseHelper(getActivity());
        UserSQL usersql = user.getUser();
        nameView.setText(usersql.name);
        phoneView.setText(usersql.phone);
        emailView.setText(usersql.email);
        addressView.setText(usersql.address);
        if (usersql.img!=null) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(usersql.img,0,usersql.img.length);
        avatar.setImageBitmap(bitmap);
        }
    }

}