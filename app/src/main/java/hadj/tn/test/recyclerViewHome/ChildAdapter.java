package hadj.tn.test.recyclerViewHome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hadj.tn.test.R;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder> {
    List<ChildModelClass> childModelClassList;
    Context context;

    public ChildAdapter(List<ChildModelClass> childModelClassList, Context context) {
        this.childModelClassList = childModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.child_rv_layout,null,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.iv_child_image.setImageResource(childModelClassList.get(position).img);
    }

    @Override
    public int getItemCount() {
        return childModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_child_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_child_image= itemView.findViewById(R.id.iv_child_item);
        }
    }
}
