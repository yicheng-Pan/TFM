package yicheng.pan.tfm.Express.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.List;
import yicheng.pan.tfm.Address.AddressListActivity;
import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Express.ExpressViewModel;
import yicheng.pan.tfm.Model.ExpressModel;
import yicheng.pan.tfm.User;
import yicheng.pan.tfm.databinding.FragmentExpressMainBinding;

public class ExpressMainFragment extends BaseFragment {
    private ExpressViewModel expressViewModel;
    private FragmentExpressMainBinding binding;
    private ExpressModel expressModel;
    private ProgressDialog dialog;
    private List<ExpressModel> expressList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        expressViewModel = new ViewModelProvider(requireActivity()).get(ExpressViewModel.class);
        User user = expressViewModel.getUser();
        binding = FragmentExpressMainBinding.inflate(inflater, container, false);

        dialog = new ProgressDialog(requireActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");

        //寄件人信息
        binding.expressMainSenderAddressBook.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), AddressListActivity.class);
            intent.putExtra("select", 1);
            intent.putExtra("user", user);
            startActivityForResult(intent, 88);
        });

        //收件人信息
        binding.expressMainReceiverAddressBook.setOnClickListener(view -> {
            Intent intent1 = new Intent(requireActivity(), AddressListActivity.class);
            intent1.putExtra("select", 1);
            intent1.putExtra("user", user);
            startActivityForResult(intent1, 99);
        });
        return binding.getRoot();
    }

    protected int getPeekHeight() {
        int peekHeight = getResources().getDisplayMetrics().heightPixels;
        //设置弹窗高度为屏幕高度的3/4
        return peekHeight - peekHeight / 6;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 88 && resultCode == 200) {  //选取寄件人地址
            String address = data.getStringExtra("address");
            binding.expressMainSenderPlaceHolder.setText(address);
        } else if (requestCode == 99 && resultCode == 200) {  //收件人地址
            String address = data.getStringExtra("address");
            binding.expressMainReceiverPlaceHolder.setText(address);
        }
    }
}
