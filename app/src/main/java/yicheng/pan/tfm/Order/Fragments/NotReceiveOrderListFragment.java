package yicheng.pan.tfm.Order.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import yicheng.pan.tfm.Adapter.NotReceiveOrderListAdapter;
import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Model.ExpressModel;
import yicheng.pan.tfm.Order.OrderViewModel;
import yicheng.pan.tfm.User;
import yicheng.pan.tfm.databinding.FragmentNotReceiveOrderListBinding;


public class NotReceiveOrderListFragment extends BaseFragment {

    private OrderViewModel orderViewModel;
    private FragmentNotReceiveOrderListBinding binding;
    private ProgressDialog dialog;
    private NotReceiveOrderListAdapter notReceiveOrderListAdapter;
    private List<ExpressModel> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        User user = orderViewModel.getUser();
        binding = FragmentNotReceiveOrderListBinding.inflate(inflater, container, false);
        dialog = new ProgressDialog(requireActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        dialog.show();
        binding.fragmentOrderList.setLayoutManager(new LinearLayoutManager(requireActivity()));
        notReceiveOrderListAdapter = new NotReceiveOrderListAdapter(requireContext(), position -> {
            ExpressModel expressModel = list.get(position);
            expressModel.setCourierUid(user.getUid());
            expressModel.setExpressType("good has been sent");
            updateData(expressModel);

        });

        binding.fragmentOrderList.setAdapter(notReceiveOrderListAdapter);

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
                    return;
                }
                list.clear();
                for (int i = 0; i < models.size(); i++) {
                    ExpressModel expressModel = models.get(i);
                    if (expressModel.getCourierUid() == null) {
                        expressModel.setKey(i);
                        list.add(expressModel);
                    }
                }

                notReceiveOrderListAdapter.addData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        return binding.getRoot();
    }


    private void updateData(ExpressModel expressModel) {
        if (dialog != null) {
            dialog.show();
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference dataBaseRef = myRef.child("data").child("express").child("" + expressModel.getKey());
        dataBaseRef.setValue(expressModel);
        dataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (isAdded()) {
                    requireActivity().finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
}
