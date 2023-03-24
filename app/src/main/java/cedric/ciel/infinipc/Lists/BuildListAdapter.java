package cedric.ciel.infinipc.Lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cedric.ciel.infinipc.R;

public class BuildListAdapter extends RecyclerView.Adapter<BuildListAdapter.ViewHolder> {
    private ArrayList<BuildData> mbuildData = new ArrayList<>();
    private Context mContext;

    public BuildListAdapter(Context context, ArrayList<BuildData> buildData) {
        mContext = context;
        mbuildData = buildData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_build, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BuildListAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(mbuildData.get(position));

        BuildData buildData = mbuildData.get(position);

        holder.tv_bName.setText(buildData.getBuild_name());
        holder.tv_CPU.setText("CPU: "+buildData.getCpu_name());
        holder.tv_Memory.setText("Memory: "+buildData.getRam_count() +"GB");
        holder.tv_Watts.setText("Watts: "+buildData.getWatts());
        holder.tv_Price.setText("Est Price: $"+buildData.getEst_price());
    }

    @Override
    public int getItemCount() {
        return mbuildData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_bName, tv_CPU, tv_Memory, tv_Watts, tv_Price;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_bName = itemView.findViewById(R.id.tv_bName);
            tv_CPU = itemView.findViewById(R.id.tv_cpu);
            tv_Memory = itemView.findViewById(R.id.tv_ram);
            tv_Watts = itemView.findViewById(R.id.tv_watts);
            tv_Price = itemView.findViewById(R.id.tv_estPrice);
        }
    }
}
