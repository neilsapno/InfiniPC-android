package cedric.ciel.infinipc.Lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cedric.ciel.infinipc.R;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {
    private ArrayList<StoreParts> mstoreParts = new ArrayList<>();
    private Context mContext;

    public StoreAdapter(Context context, ArrayList<StoreParts> storeParts) {
        mContext = context;
        mstoreParts = storeParts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_browse, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StoreAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(mstoreParts.get(position));

        StoreParts storeParts = mstoreParts.get(position);

        holder.tv_partsTitle.setText(storeParts.getTv_partsTitle());
        holder.tv_partsInfo1.setText(""+storeParts.getTv_partsInfo1() +"");
        holder.tv_partsInfo2.setText(""+storeParts.getTv_partsInfo2());
        holder.tv_partsInfo3.setText(""+storeParts.getTv_partsInfo3());
        holder.tv_partsInfo4.setText(""+storeParts.getTv_partsInfo4());
        holder.tv_partsPrice.setText("$"+storeParts.getTv_partsPrice());
    }

    @Override
    public int getItemCount() {
        return mstoreParts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_partsTitle, tv_partsInfo1, tv_partsInfo2, tv_partsInfo3, tv_partsInfo4,tv_partsPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_partsTitle = itemView.findViewById(R.id.tv_partsTitle);
            tv_partsInfo1 = itemView.findViewById(R.id.tv_partsInfo1);
            tv_partsInfo2 = itemView.findViewById(R.id.tv_partsInfo2);
            tv_partsInfo3 = itemView.findViewById(R.id.tv_partsInfo2);
            tv_partsInfo4 = itemView.findViewById(R.id.tv_partsInfo4);
            tv_partsPrice = itemView.findViewById(R.id.tv_partsPrice);
        }
    }
}
