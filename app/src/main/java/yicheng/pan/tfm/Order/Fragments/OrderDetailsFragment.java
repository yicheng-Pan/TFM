package yicheng.pan.tfm.Order.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Model.ExpressModel;
import yicheng.pan.tfm.Order.OrderViewModel;
import yicheng.pan.tfm.User;
import yicheng.pan.tfm.databinding.FragmentOrderDetailsBinding;


public class OrderDetailsFragment extends BaseFragment {

    private OrderViewModel orderViewModel;
    private FragmentOrderDetailsBinding binding;
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
        User user = orderViewModel.getUser();
        expressModel = orderViewModel.getExpressModel();
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false);
        Bitmap mBitmap = CodeUtils.createImage(expressModel.getExpressId(), 400, 400, null);
        binding.ivQrc.setImageBitmap(mBitmap);
        binding.tvGoodsName.setText("Goods-Name：" + expressModel.getGoodsName());
        binding.tvOrderNo.setText("Express-Barcode：" + expressModel.getExpressId());
        binding.tvSenderInfo.setText("Sender: "+expressModel.getSenderInfo());
        binding.tvReceiveInfo.setText("Receiver: "+expressModel.getAddresseeInfo());
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 88 && resultCode == 200) {  //选取寄件人地址
            String address = data.getStringExtra("address");
            binding.tvReceiveInfo.setText(address);
        }
    }


}
