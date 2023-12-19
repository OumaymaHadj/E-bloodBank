package hadj.tn.test.fragment.quizFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import hadj.tn.test.model.AppUserRole;
import hadj.tn.test.model.Role;
import hadj.tn.test.model.User;
import hadj.tn.test.R;
import hadj.tn.test.fragment.leftMenuFragments.ProfileFragment;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Quiz_8Fragment extends Fragment {

    Button yes, no;
    String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_8, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RetrofitClient retrofitClient = new RetrofitClient();
        API userApi = retrofitClient.getRetrofit().create(API.class);

        SharedPreferences preferences = getActivity().getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
        String retrievedToken  = preferences.getString("TOKEN",null);
        token = "Bearer "+retrievedToken;

        String username  = preferences.getString("USERNAME",null); //second parameter default value.


        yes= view.findViewById(R.id.btn1111111);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogEditPhone = new AlertDialog.Builder(getActivity());

                final View customLayout
                        = getLayoutInflater()
                        .inflate(
                                R.layout.age_restriction,
                                null);
                dialogEditPhone.setView(customLayout);
                AlertDialog dialog = dialogEditPhone.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                Button ok = customLayout.findViewById(R.id.ok);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,new ProfileFragment()).commit();

                    }
                });
            }
        });

        no = view.findViewById(R.id.btn2222222);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogEditPhone = new AlertDialog.Builder(getActivity());

                final View customLayout
                        = getLayoutInflater()
                        .inflate(
                                R.layout.passed,
                                null);
                dialogEditPhone.setView(customLayout);
                AlertDialog dialog = dialogEditPhone.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                Button home = customLayout.findViewById(R.id.okHome);
                home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        User user = new User(username);
                        userApi.setToDonor(token,user).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.code()==200){
                                    System.out.println("check the database");
                                } else{
                                    System.out.println("there's something wrong");
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                System.out.println("fail");
                            }
                        });
                        dialog.dismiss();
                        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,new ProfileFragment()).commit();
                    }
                });
            }
        });
    }
}