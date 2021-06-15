package yicheng.pan.tfm.Main.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import yicheng.pan.tfm.Auth.AuthActivity;
import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Main.MainViewModel;
import yicheng.pan.tfm.Order.CourierOrderListActivity;
import yicheng.pan.tfm.Order.NotReceiveOrderListActivity;
import yicheng.pan.tfm.Scan.QrcActivity;
import yicheng.pan.tfm.User;
import yicheng.pan.tfm.databinding.FragmentCourierMainBinding;
import yicheng.pan.tfm.databinding.FragmentMainBinding;

import static android.app.Activity.RESULT_OK;


public class CourierMainFragment extends BaseFragment {

    private MainViewModel mainViewModel;
    private FragmentCourierMainBinding binding;
    private ProgressDialog dialog;

    public CourierMainFragment() {
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
        binding = FragmentCourierMainBinding.inflate(inflater, container, false);
        dialog = new ProgressDialog(this.getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        //订单接收
        binding.mainBtnExpressSend.setOnClickListener(v -> {
            User user = mainViewModel.getUser();
            Intent intent = new Intent(this.getActivity(), NotReceiveOrderListActivity.class);
            intent.putExtra("user", mainViewModel.getUser());
            startActivity(intent);

        });

        //我的订单
        binding.mainBtnOrder.setOnClickListener(view -> {
            Intent intent = new Intent(this.getActivity(), CourierOrderListActivity.class);
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

        return binding.getRoot();
    }

}