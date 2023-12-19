package hadj.tn.test.fragment.leftMenuFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hadj.tn.test.R;
import hadj.tn.test.model.Post;
import hadj.tn.test.model.User;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPublicationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPublicationsFragment extends Fragment {

    List<Bitmap> images = new ArrayList<>();
    List<String> names = new ArrayList<>();
    List<String> dates = new ArrayList<>();
    List<String> posts = new ArrayList<>();
    List<Integer> iDs = new ArrayList<>();

    User user = new User();
    RetrofitClient retrofitClient = new RetrofitClient();
    API userApi = retrofitClient.getRetrofit().create(API.class);


    public static MyPublicationsFragment newInstance(String param1, String param2) {
        MyPublicationsFragment fragment = new MyPublicationsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_publications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = getActivity().getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
        String retrievedToken = preferences.getString("TOKEN", null); //second parameter default value.
        String username = preferences.getString("USERNAME", null);
        String token = "Bearer " + retrievedToken;

        RetrofitClient retrofitClient = new RetrofitClient();
        API userApi = retrofitClient.getRetrofit().create(API.class);


        userApi.findUser(token,username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                assert response.body()!=null;
               user =response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                    System.out.println("faiiiil1");
            }
        });

        getPosts(view);


    }
    public void getPosts(View view){
        SharedPreferences preferences = getActivity().getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
        String retrievedToken = preferences.getString("TOKEN", null); //second parameter default value.
        String token = "Bearer " + retrievedToken;
        RecyclerView recyclerView = view.findViewById(R.id.listPosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        userApi.findAllPubs(token).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                recyclerView.setAdapter(null);
                if( response.body()!=null){
                    List<Post> getPosts = response.body();
                    int i =0;
                    while (i < getPosts.size()) {

                        if(user.getId()==getPosts.get(i).getUserId().getId()){
                            names.add(user.getUsername());
                            String pict = user.getImage();

                            byte[] encodeByte = Base64.decode(pict, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                            images.add(bitmap);
                            posts.add(getPosts.get(i).getContenu());
                            String[] result = getPosts.get(i).getCreatedAt().split(" ");
                            dates.add(result[0]);
                            iDs.add((getPosts.get(i).getPubId()));
                        }
                        i++;

                    }

                    recyclerView.setAdapter(new MyPubAdapter(names,dates, images, posts,iDs));
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                System.out.println("faiiiil2");
            }
        });
    }
}