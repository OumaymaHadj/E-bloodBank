package hadj.tn.test.fragment.leftMenuFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hadj.tn.test.R;
import hadj.tn.test.model.Evenement;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvitAdapter extends RecyclerView.Adapter<InvitAdapter.MyViewHolder>{

    List<String> listTitles = new ArrayList<>(), listBody= new ArrayList<>(), dates= new ArrayList<>(), startTimes = new ArrayList<>(),endTimes= new ArrayList<>();
    List<Integer> iDs;
    String token;
    public InvitAdapter(List<String>  list, List<String> date , List<String> time,List<String> time2, List<String>  listBody, List<Integer> iDs) {
        this.listTitles = list;
        this.dates=date;
        this.startTimes=time;
        this.endTimes = time2;
        this.listBody = listBody;
        this.iDs=iDs;


    }
    @NonNull
    @Override
    public InvitAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SharedPreferences preferences = parent.getContext().getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
        String retrievedToken  = preferences.getString("TOKEN",null);
        token = "Bearer "+retrievedToken;
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invit,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RetrofitClient retrofitClient = new RetrofitClient();
        API userApi = retrofitClient.getRetrofit().create(API.class);

        holder.title.setText(listTitles.get(position));
        holder.date.setText(dates.get(position));
        holder.start.setText(startTimes.get(position));
        holder.end.setText(endTimes.get(position));
        holder.desc.setText(listBody.get(position));
        holder.accepted.setVisibility(View.INVISIBLE);
        holder.notAccepted.setVisibility(View.INVISIBLE);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogEditPhone = new AlertDialog.Builder(v.getContext());

                final View customLayout
                        = LayoutInflater.from(v.getContext())
                        .inflate(
                                R.layout.warning_dialog,
                                null);
                dialogEditPhone.setView(customLayout);
                AlertDialog dialog = dialogEditPhone.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                Button delete = customLayout.findViewById(R.id.okDelete);
                Button cancel = customLayout.findViewById(R.id.cancelDelete);
                Evenement event = new Evenement();
                event.setId(iDs.get(holder.getAbsoluteAdapterPosition()));
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userApi.deleteInvit(token, event).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.code() == 200) {
                                    System.out.println("deleted");
                                    dialog.cancel();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }

                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
            });
    }

    @Override
    public int getItemCount() {
        System.out.println(listTitles.size());
        return listTitles.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,desc, date, start, end;
        TextView accept,accepted, notAccepted;
        Button delete;
        de.hdodenhof.circleimageview.CircleImageView pic;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.event_title);
            desc=itemView.findViewById(R.id.desc);
            date= itemView.findViewById(R.id.txtDate);
            start=itemView.findViewById(R.id.txtTimeStart);
            end = itemView.findViewById(R.id.txtTimeEnd);
            delete= itemView.findViewById(R.id.delete_action);
            accept=itemView.findViewById(R.id.toAccept);
            accepted=itemView.findViewById(R.id.accepted);
            notAccepted=itemView.findViewById(R.id.not_accepted);


        }
    }
}
