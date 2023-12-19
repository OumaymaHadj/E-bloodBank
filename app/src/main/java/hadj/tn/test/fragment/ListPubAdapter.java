package hadj.tn.test.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hadj.tn.test.R;


public class ListPubAdapter extends RecyclerView.Adapter<ListPubAdapter.MyViewHolder> {

    List<String> listNames = new ArrayList<>(), listPubContenu= new ArrayList<>(), dates = new ArrayList<>();
    List<Bitmap> listImages=new ArrayList<>();
    List<Bitmap> listImagesProfile =new ArrayList<>();


    public ListPubAdapter(List<String>  list,List<String> date, List<Bitmap> listImages, List<String>  listPubContenu,List<Bitmap> listImagesProfile) {
        this.listNames = list;
        this.dates=date;
        this.listImages = listImages;
        this.listPubContenu = listPubContenu;
        this.listImagesProfile = listImagesProfile;
    }

    @NonNull
    @Override
    public ListPubAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pub,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(listNames.get(position));
        holder.createdAt.setText(dates.get(position));
        holder.imageView.setImageBitmap(listImages.get(position));
        holder.imageViewCom.setImageBitmap(listImagesProfile.get(position));
        holder.textView8.setText(listPubContenu.get(position));
    }

    @Override
    public int getItemCount() {
        return listNames.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView,textView8,createdAt;

        de.hdodenhof.circleimageview.CircleImageView imageView,imageViewCom;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView7);
            textView8 = itemView.findViewById(R.id.textView8);
            createdAt= itemView.findViewById(R.id.textDate);
            imageView = itemView.findViewById(R.id.imagePub);

            imageViewCom =  itemView.findViewById(R.id.profilePub);

        }
    }
}

