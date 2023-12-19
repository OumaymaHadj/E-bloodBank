package hadj.tn.test.fragment.bottomMenuFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hadj.tn.test.R;
import hadj.tn.test.model.AppUserRole;
import hadj.tn.test.model.User;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindDonorFragment extends Fragment {


    List<Bitmap> images = new ArrayList<>();
    List<String> names = new ArrayList<>(), blood = new ArrayList<>(), region = new ArrayList<>();
    String token, username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_donor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences preferences = getActivity().getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
        String retrievedToken = preferences.getString("TOKEN", null);
        username = preferences.getString("USERNAME", null);
        token = "Bearer " + retrievedToken;

        RetrofitClient retrofitClient = new RetrofitClient();
        API userApi = retrofitClient.getRetrofit().create(API.class);

        Call<List<User>> call = userApi.findByRole(token, AppUserRole.DONOR);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                String personImage;
                int i = 0;
                List<User> getusers = new ArrayList<>();
                getusers.addAll(response.body());
                assert getusers != null;
                System.out.println(getusers.size());
                while (i < getusers.size()) {
                    if (!getusers.get(i).getUsername().equals(username)) {

                        names.add(getusers.get(i).getUsername());
                        blood.add(getusers.get(i).getTypeSang());
                        region.add(getusers.get(i).getRegion());
                        personImage = getusers.get(i).getImage();
                        if (personImage != null) {
                            byte[] bytes = Base64.decode(personImage, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            images.add(bitmap);
                        }
                        i++;
                    }
                    i++;
                }

                System.out.println(images);

                RecyclerView recyclerView = view.findViewById(R.id.listDonor);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                ListDonorAdapter myAdapter = new ListDonorAdapter(names, images, blood, region);
                recyclerView.setAdapter(myAdapter);


            }


            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getContext(), "couldn't get", Toast.LENGTH_SHORT).show();
            }
        });


    }
}