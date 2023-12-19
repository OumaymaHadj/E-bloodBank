package hadj.tn.test.fragment.bottomMenuFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hadj.tn.test.MapsActivity;
import hadj.tn.test.R;


public class ListDonorAdapter extends RecyclerView.Adapter<ListDonorAdapter.MyViewHolder>{
    List<String> listNames = new ArrayList<>()
            , listBlood= new ArrayList<>(), listRegion = new ArrayList<>();
    List<Bitmap> listImages= new ArrayList<>();

    public ListDonorAdapter(){}
    public ListDonorAdapter(List<String> listNames, List<Bitmap> listImages, List<String> listBlood,List<String> listRegion) {
        this.listNames = listNames;
        this.listImages = listImages;
        this.listBlood = listBlood;
        this.listRegion=listRegion;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_donor,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(listNames!=null && listBlood!=null){
        holder.nom.setText(listNames.get(position));
        holder.imageView.setImageBitmap(listImages.get(position));
        holder.type_blood.setText(listBlood.get(position));}
        holder.region.setText((listRegion.get(position)));
    }

    @Override
    public int getItemCount() {
        return listNames.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nom, type_blood, region;
        de.hdodenhof.circleimageview.CircleImageView imageView;
        Button btn;
        public final Context context;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            nom = itemView.findViewById(R.id.donor_name);
            type_blood = itemView.findViewById(R.id.type);
            region=itemView.findViewById(R.id.region);
            imageView = itemView.findViewById(R.id.donor_pic);
            btn = itemView.findViewById(R.id.btnRequest);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent;
                    intent=new Intent(context, MapsActivity.class);
                    intent.putExtra("username",nom.getText().toString());
                    intent.putExtra("blood_type",type_blood.getText().toString());
                    context.startActivity(intent);
                }

            });
        }


    }



}
