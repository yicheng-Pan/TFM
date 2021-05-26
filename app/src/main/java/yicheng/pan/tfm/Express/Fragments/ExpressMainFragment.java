package yicheng.pan.tfm.Express.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import yicheng.pan.tfm.Address.AddressListActivity;
import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Express.ExpressViewModel;
import yicheng.pan.tfm.Model.ExpressModel;
import yicheng.pan.tfm.R;
import yicheng.pan.tfm.User;
import yicheng.pan.tfm.databinding.FragmentExpressMainBinding;

public class ExpressMainFragment extends BaseFragment {
    private ExpressViewModel expressViewModel;
    private FragmentExpressMainBinding binding;
    private ExpressModel expressModel;
    private ProgressDialog dialog;
    private List<ExpressModel> expressList = new ArrayList<>();
    private String typeStr = "";
    private TimePickerView timePickerView;
    private BottomSheetDialog bottomSheetDialog;



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
       //选择寄件方式
        binding.expressMainMailingWayValue.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.express_main_mailing_way_value_home: //上门取件
                    typeStr = "Home Access";
                    break;

                case R.id.express_main_mailing_way_value_site:  //寄件
                    typeStr = "Site";
                    break;
            }
        });

        //选择时间
        binding.expressMainExpectedTimeValue.setOnClickListener(view -> {
            timePickerView.show();
        });
        //选择物品
        binding.expressMainGoodInfoValue.setOnClickListener(view -> {
            bottomSheetDialog.show();
        });
        View view = View.inflate(requireActivity(), R.layout.dialog_artical_detail, null);
        bottomSheetDialog = new BottomSheetDialog(requireActivity());
        bottomSheetDialog.setContentView(view);
        initTime();
        return binding.getRoot();
    }


    /**
     * 初始化日期选择控件
     */
    private void initTime() {
        timePickerView = new TimePickerBuilder(requireActivity(), (date, v) -> {
            binding.expressMainExpectedTimeValue.setText(getTime(date));

        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setLineSpacingMultiplier(2.5f)
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("", "", "", "h", "m", "s")
                .setSubmitText("Confirm")
                .setCancelText("Cancel")
                .isDialog(true)
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .build();

        Dialog mDialog = timePickerView.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            timePickerView.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);
                dialogWindow.setGravity(Gravity.BOTTOM);
                dialogWindow.setDimAmount(0.1f);
            }
        }

    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
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
