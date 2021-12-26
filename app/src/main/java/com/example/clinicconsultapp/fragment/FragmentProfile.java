package com.example.clinicconsultapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clinicconsultapp.autorithation.LoginActivity;
import com.example.clinicconsultapp.R;

public class FragmentProfile extends Fragment {
    @NonNull
    ImageView imageViewProfile;
    int SELECT_PICTURE = 200;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.profile_fragment,container,false);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("logged",0);
        String userName = sharedPreferences.getString("userName",null);
        String userEmail = sharedPreferences.getString("userEmail", null);
        String passWord = sharedPreferences.getString("userPassword", null);
        TextView profileName = rootView.findViewById(R.id.profileName);
        profileName.setText(userName);
        TextView profileSecondName = rootView.findViewById(R.id.profileSecondName);
        profileSecondName.setText(userName);
        TextView profileEmail = rootView.findViewById(R.id.profileEmail);
        profileEmail.setText(userEmail);
        TextView profilePassword = rootView.findViewById(R.id.profilePassword);
        profilePassword.setText(passWord);
        Button buttonLogOut = (Button) rootView.findViewById(R.id.logOutButton);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("userId",0);
                editor.putString("userName",null);
                editor.putString("userEmail",null);
                editor.putString("userPassword",null);
                editor.apply();
                Intent intent = new Intent(rootView.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        imageViewProfile = (ImageView) rootView.findViewById(R.id.profileImage);
        String uri =sharedPreferences.getString(userEmail,null);
        if (uri!=null){
            Uri imageUri = Uri.parse(uri);
            imageViewProfile.setImageURI(imageUri);
        }

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        return rootView;
    }

    void imageChooser(){
        Intent image = new Intent();
        image.setType("image/*");
        image.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(image,"Select Picture"),SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode== Activity.RESULT_OK){
            if (requestCode==SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                if (selectedImageUri!=null){
                    imageViewProfile.setImageURI(selectedImageUri);
                    SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("logged",0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String email = sharedPreferences.getString("userEmail",null);
                    editor.putString(email,selectedImageUri.toString());
                    editor.commit();
                }
            }
        }

    }

}
