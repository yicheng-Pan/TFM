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

import yicheng.pan.tfm.Model.ExpressModel;
import yicheng.pan.tfm.R;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ItemHolder> {

    private List<ExpressModel> datas = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private OnItemClickListner onItemClickListner;

    public OrderListAdapter(Context context, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.onItemClickListner=onItemClickListner;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(inflater.inflate(R.layout.item_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        ExpressModel expressModel = datas.get(position);
        holder.goods_name.setText("Goods-Name："+expressModel.getGoodsName());
        holder.order_no.setText("Barcode："+expressModel.getExpressId()+"");
        holder.receive_address.setText(expressModel.getSenderInfo());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return datas != null && datas.size() > 0 ? datas.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView goods_name, order_no, receive_address;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            goods_name = itemView.findViewById(R.id.goods_name);
            order_no = itemView.findViewById(R.id.order_no);
            receive_address = itemView.findViewById(R.id.receive_address);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {

            if (onItemClickListner!=null){
                onItemClickListner.itemClick((int) itemView.getTag());
            }

        }
    }

    public void addData(List<ExpressModel> list) {
        datas.clear();
        datas.addAll(list);
        notifyDataSetChanged();

    }

    public interface OnItemClickListner{
        void itemClick(int position);
    }

}
