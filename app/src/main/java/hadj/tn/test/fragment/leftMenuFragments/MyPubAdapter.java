package hadj.tn.test.fragment.leftMenuFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hadj.tn.test.MapsActivity;
import hadj.tn.test.R;
import hadj.tn.test.model.Post;
import hadj.tn.test.util.API;
import hadj.tn.test.util.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPubAdapter extends RecyclerView.Adapter<MyPubAdapter.MyViewHolder> {
    List<String> listNames = new ArrayList<>(), listPubContenu= new ArrayList<>(), dates= new ArrayList<>();
    List<Bitmap> listImages=new ArrayList<>();
    List<Integer> iDs;


    public MyPubAdapter(List<String>  list,List<String> date ,List<Bitmap> listImages, List<String>  listPubContenu, List<Integer> iDs) {
        this.listNames = list;
        this.dates=date;
        this.listImages = listImages;
        this.listPubContenu = listPubContenu;
        this.iDs=iDs;


    }

    @NonNull
    @Override
    public MyPubAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itempub,parent,false));

    }




    @Override
    public void onBindViewHolder(@NonNull MyPubAdapter.MyViewHolder holder, int position) {
        RetrofitClient retrofitClient = new RetrofitClient();
        API userApi = retrofitClient.getRetrofit().create(API.class);

        holder.name.setText(listNames.get(position));
        holder.createdAt.setText(dates.get(position));
        holder.pic.setImageBitmap(listImages.get(position));
        holder.post.setText(listPubContenu.get(position));
        holder.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogEditPhone = new AlertDialog.Builder(v.getContext());

                final View customLayout
                        = LayoutInflater.from(v.getContext())
                        .inflate(
                                R.layout.update_post_dialog,
                                null);
                dialogEditPhone.setView(customLayout);
                AlertDialog dialog = dialogEditPhone.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                ImageView picture = customLayout.findViewById(R.id.imgProfile);
                EditText content = customLayout.findViewById(R.id.content);
                Button update = customLayout.findViewById(R.id.okEdit);
                Button cancel = customLayout.findViewById(R.id.cancelEdit);

                picture.setImageBitmap(listImages.get(holder.getAbsoluteAdapterPosition()));
                content.setText(listPubContenu.get(holder.getAbsoluteAdapterPosition()));

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences preferences = v.getContext().getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
                        String retrievedToken  = preferences.getString("TOKEN",null);
                        String token = "Bearer "+retrievedToken;
                        Post post = new Post();
                        post.setPubId(iDs.get(holder.getAbsoluteAdapterPosition()));
                        post.setContenu(content.getText().toString());
                        userApi.updatePost(token,post).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.code()==200)
                                    System.out.println("updated");
                                    dialog.cancel();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                });

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogEditPhone = new AlertDialog.Builder(v.getContext());
                SharedPreferences preferences = v.getContext().getSharedPreferences("AYACHNI", Context.MODE_PRIVATE);
                String retrievedToken  = preferences.getString("TOKEN",null);
                String token = "Bearer "+retrievedToken;
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

                        userApi.deletePubById(token,iDs.get(holder.getAbsoluteAdapterPosition())).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.code()==200){
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
                        dialog.dismiss();
                    }
                });
            }
        });


    }


    @Override
    public int getItemCount() {
        System.out.println(listPubContenu.size());
        return listPubContenu.size();

    }
    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,post, createdAt;
        ImageView modify;
        Button delete;
        de.hdodenhof.circleimageview.CircleImageView pic;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.publisherName);
            createdAt= itemView.findViewById(R.id.txtDate);
            post = itemView.findViewById(R.id.post);
            pic = itemView.findViewById(R.id.publisherPic);
            delete= itemView.findViewById(R.id.delete_action);
            modify=itemView.findViewById(R.id.modify_action);


        }
    }
}
