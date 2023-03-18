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

public class BuildListAdapter extends RecyclerView.Adapter<BuildListAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList build_name;
    private final ArrayList cpu_name;
    private final ArrayList cpu_cooler;
    private final ArrayList mobo_name;

    public BuildListAdapter(Context context, ArrayList build_name, ArrayList cpu_name, ArrayList cpu_cooler, ArrayList mobo_name) {
        this.context = context;
        this.build_name = build_name;
        this.cpu_name = cpu_name;
        this.cpu_cooler = cpu_cooler;
        this.mobo_name = mobo_name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_build,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.build_name.setText(String.valueOf(build_name.get(position)));
        holder.cpu_name.setText(String.valueOf(cpu_name.get(position)));
        holder.cpu_cooler.setText(String.valueOf(cpu_cooler.get(position)));
        holder.mobo_name.setText(String.valueOf(mobo_name.get(position)));
    }

    @Override
    public int getItemCount() {
        return build_name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView build_name,cpu_name,cpu_cooler, mobo_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            build_name=itemView.findViewById(R.id.tv_title);
            cpu_name=itemView.findViewById(R.id.tv_parts_1);
            cpu_cooler=itemView.findViewById(R.id.tv_parts_2);
            mobo_name=itemView.findViewById(R.id.tv_parts_3);
        }
    }
}
