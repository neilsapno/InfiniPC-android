package cedric.ciel.infinipc.Lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cedric.ciel.infinipc.R;

public class PartsListAdapter extends RecyclerView.Adapter<PartsListAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList title;
    private final ArrayList t_specs0;
    private final ArrayList t_specs1;
    private final ArrayList t_specs2;
    private final ArrayList t_specs3;
    private final ArrayList price;


    public PartsListAdapter(Context context, ArrayList title, ArrayList t_specs0, ArrayList t_specs1, ArrayList t_specs2, ArrayList t_specs3, ArrayList price) {
        this.context = context;
        this.title = title;
        this.t_specs0 = t_specs0;
        this.t_specs1 = t_specs1;
        this.t_specs2 = t_specs2;
        this.t_specs3 = t_specs3;
        this.price = price;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_build,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(String.valueOf(title.get(position)));
        holder.t_specs0.setText(String.valueOf(t_specs0.get(position)));
        holder.t_specs1.setText(String.valueOf(t_specs1.get(position)));
        holder.t_specs2.setText(String.valueOf(t_specs2.get(position)));
        holder.t_specs3.setText(String.valueOf(t_specs3.get(position)));
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,t_specs0,t_specs1, t_specs2, t_specs3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tv_title);
            t_specs0=itemView.findViewById(R.id.tv_parts_0);
            t_specs1=itemView.findViewById(R.id.tv_parts_1);
            t_specs2=itemView.findViewById(R.id.tv_parts_2);
            t_specs3=itemView.findViewById(R.id.tv_parts_3);
        }
    }
}
