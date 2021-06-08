package yicheng.pan.tfm.Order.Fragments;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import yicheng.pan.tfm.Adapter.OrderListAdapter;
import yicheng.pan.tfm.Model.ExpressModel;
import yicheng.pan.tfm.Order.OrderDetailsActivity;
import yicheng.pan.tfm.Order.OrderViewModel;
import yicheng.pan.tfm.User;
import yicheng.pan.tfm.databinding.FragmentSearchOrderBinding;


public class SearchOrderFragment extends Fragment {
    private OrderViewModel orderViewModel;
    private ExpressModel expressModel;
    private FragmentSearchOrderBinding binding;
    private ProgressDialog dialog;
    private OrderListAdapter orderListAdapter;
    private List<ExpressModel> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        User user = orderViewModel.getUser();
        binding = FragmentSearchOrderBinding.inflate(inflater, container, false);
        binding.btnSearch.setOnClickListener(view -> {
            String orderNo = binding.etOrderNo.getText().toString().trim();
            if ("".equals(orderNo)) {
                Toast.makeText(requireActivity(), "Please enter the order number", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dialog != null) {
                dialog.show();
            }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference dataBaseRef = myRef.child("data").child("express");

        dataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                GenericTypeIndicator<List<ExpressModel>> t = new GenericTypeIndicator<List<ExpressModel>>() {
                };
                List<ExpressModel> models = snapshot.getValue(t);
                if (models == null) {
                    Toast.makeText(requireActivity(), "The order does not exist", Toast.LENGTH_SHORT).show();
                    return;
                }

                ExpressModel searchOrder = null;

                for (int i = 0; i < models.size(); i++) {
                    ExpressModel expressModel = models.get(i);
                    if (orderNo.equals(expressModel.getExpressId())) {
                        expressModel.setKey(i);
                        searchOrder = expressModel;
                        break;
                    }
                }

                if (searchOrder==null){
                    Toast.makeText(requireActivity(), "The order does not exist", Toast.LENGTH_SHORT).show();
                    return;
                }

                //跳转到详情页
                Intent intent = new Intent(requireActivity(), OrderDetailsActivity.class);
                intent.putExtra("expressModel", searchOrder);
                intent.putExtra("user", user);
                startActivity(intent);
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
}