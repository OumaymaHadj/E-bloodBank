package hadj.tn.test.fragment.leftMenuFragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import hadj.tn.test.model.Evenement;
import hadj.tn.test.model.Invitation;
import hadj.tn.test.model.User;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InvitationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InvitationFragment extends Fragment {

    List<String> listTitles = new ArrayList<>(), listBody= new ArrayList<>(), dates= new ArrayList<>(), startTimes = new ArrayList<>(),endTimes= new ArrayList<>();
    List<Integer> iDs;

    RetrofitClient retrofitClient = new RetrofitClient();
    API userApi = retrofitClient.getRetrofit().create(API.class);
    String token;

    public static InvitationFragment newInstance(String param1, String param2) {
        InvitationFragment fragment = new InvitationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences preferences = container.getContext().getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
        String retrievedToken = preferences.getString("TOKEN", null); //second parameter default value.
        token = "Bearer " + retrievedToken;
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invitation, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerView = view.findViewById(R.id.listInvitations);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
         userApi.getInvits(token).enqueue(new Callback<List<Invitation>>() {
             @Override
             public void onResponse(Call<List<Invitation>> call, Response<List<Invitation>> response) {
                 List<Invitation> invitations = new ArrayList<>();
                 invitations= response.body();
                 for (int i = 0; i < invitations.size(); i++) {
                     //iDs.add(invitations.get(i).getId());
                     System.out.println("invit" + invitations);
                     userApi.getEvent(token, invitations.get(i).getEvent().getId()).enqueue(new Callback<Evenement>() {
                         @Override
                         public void onResponse(Call<Evenement> call, Response<Evenement> response) {
                             Evenement event = response.body();
                             System.out.println("event "+event.getTitle());
                             listTitles.add(event.getTitle());
                             listBody.add(event.getDescription());
                             dates.add(event.getDate());
                             startTimes.add(event.getStart_time());
                             endTimes.add((event.getEnd_time()));
                         }

                         @Override
                         public void onFailure(Call<Evenement> call, Throwable t) {
                             System.out.println("Failuree");
                         }
                     });

                 }
                 System.out.println("titles " + listTitles);
                 recyclerView.setAdapter(new InvitAdapter(listTitles,dates,startTimes,endTimes,listBody,iDs));

             }
             @Override
             public void onFailure(Call<List<Invitation>> call, Throwable t) {

             }

         });

            }
}