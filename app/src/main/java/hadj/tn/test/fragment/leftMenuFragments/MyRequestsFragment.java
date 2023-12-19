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

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hadj.tn.test.R;
import hadj.tn.test.model.Alert;
import hadj.tn.test.model.User;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyRequestsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRequestsFragment extends Fragment {

    List<Bitmap> images = new ArrayList<>(), images2 = new ArrayList<>();
    List<String> names = new ArrayList<>(), names2 = new ArrayList<>();
    List<String> dates = new ArrayList<>(), dates2 = new ArrayList<>();
    List<String> times = new ArrayList<>(), times2 = new ArrayList<>();
    List<String> bodies = new ArrayList<>(), bodies2 = new ArrayList<>();
    List<Integer> iDs = new ArrayList<>(), iDs2= new ArrayList<>();


    Bitmap bitmap;
    User user = new User();

    RetrofitClient retrofitClient = new RetrofitClient();
    API userApi = retrofitClient.getRetrofit().create(API.class);

    String token , username;
    int i=0;
    public static MyRequestsFragment newInstance(String param1, String param2) {
        MyRequestsFragment fragment = new MyRequestsFragment();
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
        return inflater.inflate(R.layout.fragment_my_requests, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = getActivity().getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
        String retrievedToken = preferences.getString("TOKEN", null); //second parameter default value.
         username = preferences.getString("USERNAME", null);
         token = "Bearer " + retrievedToken;
        List<Alert> sent = new ArrayList<>();
        List<Alert> received = new ArrayList<>();
         /////////////////////////////////////////////////////////////////////////////////////////////////
        /**************** here we retrieved all alerts and divided them into 2 lists *********************/
        //////////////////////////////////////////////////////////////////////////////////////////////////

        userApi.getAlerts(token).enqueue(new Callback<List<Alert>>() {

            @Override
            public void onResponse(Call<List<Alert>> call, Response<List<Alert>> response) {
               List<Alert> alerts = response.body();
               while (i < alerts.size()){
                   if(alerts.get(i).getSender().equals(username)){
                        sent.add(alerts.get(i));
                   }else if (alerts.get(i).getReceiver().equals(username)){
                       received.add(alerts.get(i));

                   }
                   i++;
               }
                System.out.println(sent); System.out.println(received);
                fillSent(view,sent);
                fillReceived(view,received);
            }

            @Override
            public void onFailure(Call<List<Alert>> call, Throwable t) {

            }
        });


    }
    private void fillSent(View view,List<Alert> sent){
        RecyclerView recyclerView = view.findViewById(R.id.listReq1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        userApi.findUser(token,username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user= response.body();
                if (user.getImage() != null) {
                    byte[] bytes = Base64.decode(user.getImage(), Base64.DEFAULT);
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                }
                for(int i=0; i< sent.size();i++){
                    images.add(bitmap);
                    names.add(username);
                    bodies.add(sent.get(i).getDescription());
                    dates.add(sent.get(i).getCreatedOn());
                    times.add(sent.get(i).getCreatedAt());
                    iDs.add(sent.get(i).getId());
                }

                recyclerView.setAdapter(new SentRequestAdapter(names,dates,times,images,bodies,iDs));

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    private void fillReceived(View view,List<Alert> received){
        String name;

        RecyclerView recyclerView2 = view.findViewById(R.id.listReq2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        for(int i=0; i< received.size();i++){
            name = received.get(i).getSender();
            int finalI = i;

            userApi.findUser(token,name).enqueue(new Callback<User>() {
               @Override
               public void onResponse(Call<User> call, Response<User> response) {
                   user= response.body();
                   if (user.getImage() != null) {
                       byte[] bytes = Base64.decode(user.getImage(), Base64.DEFAULT);
                       bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


                   }
               }
               @Override
               public void onFailure(Call<User> call, Throwable t) {
                    System.out.println("faillll");
               }
           });

            images2.add(bitmap);
            names2.add(received.get(finalI).getSender());
            dates2.add(received.get(finalI).getCreatedOn());
            times2.add(received.get(finalI).getCreatedAt());
            bodies2.add(received.get(finalI).getDescription());
            iDs2.add(received.get(finalI).getId());
            System.out.println("senders "+names2);
        }
        recyclerView2.setAdapter(new ReceivedRequestAdapter(names2,dates2,times2,images2,bodies2,iDs2));
    }


}

