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

import yicheng.pan.tfm.Model.AddressModel;
import yicheng.pan.tfm.R;


public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ItemHolder> {

    private List<AddressModel> datas = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    private OnItemClickListner onItemClickListner;

    public AddressAdapter(Context context, OnItemClickListner onItemClickListner) {
        this.context = context;
        this.onItemClickListner=onItemClickListner;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemHolder(inflater.inflate(R.layout.item_address, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        AddressModel addressModel = datas.get(position);
        holder.address_name.setText(addressModel.getName());
        holder.address_address.setText(addressModel.getAddress()+addressModel.getDetailAddress());
        holder.address_phone.setText(addressModel.getPhone());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return datas != null && datas.size() > 0 ? datas.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView address_name, address_phone, address_address;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            address_name = itemView.findViewById(R.id.address_name);
            address_phone = itemView.findViewById(R.id.address_phone);
            address_address = itemView.findViewById(R.id.address_address);
            itemView.setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View view) {

            if (onItemClickListner!=null){
                onItemClickListner.itemClick((int) itemView.getTag());
            }

        }
    }

    public void addData(List<AddressModel> list) {
        datas.clear();
        datas.addAll(list);
        notifyDataSetChanged();

    }

    public interface OnItemClickListner{
        void itemClick(int position);
    }

}
