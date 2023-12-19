package hadj.tn.test.recyclerViewHome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hadj.tn.test.R;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ViewHolder> {
    List<ParentModelClass> parentModelClassList;
    Context context;

    public ParentAdapter(List<ParentModelClass> parentModelClassList, Context context) {
        this.parentModelClassList = parentModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public ParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parent_rv_layout,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentAdapter.ViewHolder holder, int position) {

        ChildAdapter childAdapter;
        childAdapter=new ChildAdapter(parentModelClassList.get(position).childModelClassList,context);
        holder.rv_child.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.rv_child.setAdapter(childAdapter);
        childAdapter.notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return parentModelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       RecyclerView rv_child ;
       TextView tv_parent_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_child= itemView.findViewById(R.id.rv_child);
            //tv_parent_title = itemView.findViewById(R.id.parent_title);
        }
    }
}
