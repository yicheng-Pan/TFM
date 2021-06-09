package yicheng.pan.tfm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import yicheng.pan.tfm.Model.GoodsLocationModel;
import yicheng.pan.tfm.R;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ItemHolder> {

    private List<GoodsLocationModel> datas = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private OnItemClickListner onItemClickListner;

    public LocationAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(inflater.inflate(R.layout.item_order_location, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        GoodsLocationModel goodsLocationModel = datas.get(position);
        holder.tv_location.setText(goodsLocationModel.getAddress());
        holder.tv_time.setText(goodsLocationModel.getTime());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return datas != null && datas.size() > 0 ? datas.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_location, tv_time;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_time = itemView.findViewById(R.id.tv_time);
        }

        @Override
        public void onClick(View view) {

            if (onItemClickListner!=null){
                onItemClickListner.itemClick((int) itemView.getTag());
            }

        }
    }

    public void addData(List<GoodsLocationModel> list) {
        datas.clear();
        datas.addAll(list);
        notifyDataSetChanged();

    }

    public interface OnItemClickListner{
        void itemClick(int position);
    }

}
