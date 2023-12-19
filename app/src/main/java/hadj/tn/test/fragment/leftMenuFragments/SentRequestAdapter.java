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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hadj.tn.test.R;
import hadj.tn.test.model.Alert;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SentRequestAdapter extends RecyclerView.Adapter<SentRequestAdapter.MyViewHolder>{

    List<String> listNames = new ArrayList<>(), listBody= new ArrayList<>(), dates= new ArrayList<>(), times = new ArrayList<>();
    List<Bitmap> listImages=new ArrayList<>();
    List<Integer> iDs;
    String token;
    public SentRequestAdapter(List<String>  list, List<String> date , List<String> time,List<Bitmap> listImages, List<String>  listBody, List<Integer> iDs) {
        this.listNames = list;
        this.dates=date;
        this.times=time;
        this.listImages = listImages;
        this.listBody = listBody;
        this.iDs=iDs;


    }
    @NonNull
    @Override
    public SentRequestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SharedPreferences preferences = parent.getContext().getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
        String retrievedToken  = preferences.getString("TOKEN",null);
        token = "Bearer "+retrievedToken;
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RetrofitClient retrofitClient = new RetrofitClient();
        API userApi = retrofitClient.getRetrofit().create(API.class);



        holder.name.setText(listNames.get(position));
        holder.createdOn.setText(dates.get(position));
        holder.createdAt.setText(times.get(position));
        holder.pic.setImageBitmap(listImages.get(position));
        holder.post.setText(listBody.get(position));
        holder.accept.setVisibility(View.INVISIBLE);
        holder.accepted.setVisibility(View.INVISIBLE);
        holder.notAccepted.setVisibility(View.INVISIBLE);
        userApi.getAlert(token,iDs.get(position)).enqueue(new Callback<Alert>() {
            @Override
            public void onResponse(Call<Alert> call, Response<Alert> response) {
                Alert alert= response.body();
                if(alert.isAccepted()){
                    holder.accepted.setVisibility(View.VISIBLE);
                }else{
                    holder.notAccepted.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Alert> call, Throwable t) {
               System.out.println("failure");
            }
        });

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
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userApi.deleteAlert(token,iDs.get(holder.getAbsoluteAdapterPosition())).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.code()==200) {System.out.println("deleted"); dialog.cancel();}
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
        System.out.println(listBody.size());
        return listBody.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,post, createdOn, createdAt;
        TextView accept,accepted, notAccepted;
        Button delete;
        de.hdodenhof.circleimageview.CircleImageView pic;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.publisherName);
            createdOn= itemView.findViewById(R.id.txtDate);
            createdAt=itemView.findViewById(R.id.txtTime);
            post = itemView.findViewById(R.id.post);
            pic = itemView.findViewById(R.id.publisherPic);
            delete= itemView.findViewById(R.id.delete_action);
            accept=itemView.findViewById(R.id.toAccept);
            accepted=itemView.findViewById(R.id.accepted);
            notAccepted=itemView.findViewById(R.id.not_accepted);


        }
    }
}
