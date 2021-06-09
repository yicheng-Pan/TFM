package yicheng.pan.tfm.Order.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Model.ExpressModel;
import yicheng.pan.tfm.Model.GoodsLocationModel;
import yicheng.pan.tfm.Order.OrderViewModel;
import yicheng.pan.tfm.databinding.FragmentAddLocationBinding;


public class AddLocationFragment extends BaseFragment {
    private FragmentAddLocationBinding binding;
    private OrderViewModel orderViewModel;
    private List<GoodsLocationModel> list = new ArrayList<>();
    private ExpressModel expressModel;
    private ProgressDialog dialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        dialog = new ProgressDialog(requireActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        expressModel = orderViewModel.getExpressModel();
        binding = FragmentAddLocationBinding.inflate(inflater, container, false);
        if (orderViewModel.getList() != null) {
            list.addAll(orderViewModel.getList());
        }

        binding.btnAdd.setOnClickListener(view -> {
            String location = binding.etLocation.getText().toString();
            if ("".equals(location)) {
                showToast("Please Fill in the current address");
                return;
            }

            GoodsLocationModel goodsLocationModel = new GoodsLocationModel();
            goodsLocationModel.setAddress(location);
            goodsLocationModel.setExpressId(expressModel.getExpressId());
            goodsLocationModel.setTime(getTime(new Date()));
            list.add(goodsLocationModel);
            if (dialog != null) {
                dialog.show();
            }

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference dataBaseRef = myRef.child("data").child("location").child(expressModel.getExpressId());
            dataBaseRef.setValue(list);
            dataBaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //添加到数据库成功
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    //跳转到详情页
                    if (isAdded())
                        requireActivity().finish();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });
        });

        return binding.getRoot();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
