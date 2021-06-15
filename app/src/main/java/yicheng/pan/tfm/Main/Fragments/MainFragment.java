package yicheng.pan.tfm.Main.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import yicheng.pan.tfm.Address.AddressListActivity;
import yicheng.pan.tfm.Auth.AuthActivity;
import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Express.ExpressActivity;
import yicheng.pan.tfm.Main.MainViewModel;
import yicheng.pan.tfm.Order.OrderListActivity;
import yicheng.pan.tfm.Order.SearchOrderActivity;
import yicheng.pan.tfm.Scan.QrcActivity;
import yicheng.pan.tfm.User;
import yicheng.pan.tfm.databinding.FragmentMainBinding;


public class MainFragment extends BaseFragment {

    private MainViewModel mainViewModel;
    private FragmentMainBinding binding;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        binding = FragmentMainBinding.inflate(inflater, container, false);

        binding.mainBtnExpressSend.setOnClickListener(v -> {
             User user = mainViewModel.getUser();
            Intent intent= new Intent(this.getActivity(), ExpressActivity.class);
            intent.putExtra("user", mainViewModel.getUser());
            startActivity(intent);
        });

        binding.mainBtnAddressBook.setOnClickListener(v -> {
            Intent intent = new Intent(this.getActivity(), AddressListActivity.class);
            intent.putExtra("user", mainViewModel.getUser());
            startActivity(intent);
        });
        binding.mainBtnScan.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), QrcActivity.class);
            startActivityForResult(intent,100);
        });

        binding.ivLogout.setOnClickListener(view -> {
            AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(requireActivity());
            alertdialogbuilder.setMessage("Are you sure you want to log out?");
            alertdialogbuilder.setPositiveButton("sure", (dialogInterface, i) -> {
                startActivity(new Intent(requireActivity(), AuthActivity.class));
                requireActivity().finish();
            });
            alertdialogbuilder.setNeutralButton("cancel", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            final AlertDialog alertdialog1 = alertdialogbuilder.create();
            alertdialog1.show();
        });

        //我的订单
        binding.mainBtnCart.setOnClickListener(view -> {
            User user = mainViewModel.getUser();
            Intent intent = new Intent(this.getActivity(), OrderListActivity.class);
            intent.putExtra("user", mainViewModel.getUser());
            startActivity(intent);

        });

        //查询订单
        binding.svSearch.setOnClickListener(view -> {
            Intent intent = new Intent(this.getActivity(), SearchOrderActivity.class);
            intent.putExtra("user", mainViewModel.getUser());
            startActivity(intent);
        });
        return binding.getRoot();
    }
}