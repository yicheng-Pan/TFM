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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import yicheng.pan.tfm.Address.AddressListActivity;
import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Express.ExpressDetailsActivity;
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
    private String payType = "";
    private TimePickerView timePickerView;
    private BottomSheetDialog bottomSheetDialog;

    private int weight = 0;
    private double price = 0.00;



    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        expressViewModel = new ViewModelProvider(requireActivity()).get(ExpressViewModel.class);
        User user = expressViewModel.getUser();
        binding = yicheng.pan.tfm.databinding.FragmentExpressMainBinding.inflate(inflater, container, false);

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
      //选择付款方式
        binding.rgPay.setOnCheckedChangeListener((radioGroup,i)->{
            switch (i) {
                case R.id.rb_pay_online: //马上付
                    payType = "Pay Online";
                    break;

                case R.id.rb_pay:  //到付
                    payType = "Collect";
                    break;
            }
        });


        //选择时间
        binding.expressMainExpectedTimeValue.setOnClickListener(view -> timePickerView.show());
        //选择物品
        binding.expressMainGoodInfoValue.setOnClickListener(view -> bottomSheetDialog.show());

        View view = View.inflate(requireActivity(), R.layout.dialog_artical_detail, null);
        bottomSheetDialog = new BottomSheetDialog(requireActivity());
        bottomSheetDialog.setContentView(view);


        //计算重量
        EditText et_weight = view.findViewById(R.id.et_weight);
        EditText et_article_detail_name = view.findViewById(R.id.et_article_detail_name);
        TextView tv_price = view.findViewById(R.id.tv_price);
        view.findViewById(R.id.tv_add).setOnClickListener(view1 -> {
            weight++;
            price = weight * 5;
            et_weight.setText(weight + "");
            tv_price.setText("￥：" + price);
        });

        view.findViewById(R.id.tv_remove).setOnClickListener(view1 -> {
            if (weight > 0) {
                weight--;
            }
            price = weight * 5;
            tv_price.setText("￥：" + price);
            et_weight.setText(weight + "");
        });

        view.findViewById(R.id.btn_article_detail_confirm).setOnClickListener(view1 -> {
            bottomSheetDialog.dismiss();
            String name = et_article_detail_name.getText().toString().trim();
            String etWeight = et_weight.getText().toString().trim();
            binding.expressMainGoodInfoValue.setText(name + "    " + etWeight + "KG");
        });

       //点击提交
        binding.expressMainSubmit.setOnClickListener(view1 -> {
            String senderInfo = binding.expressMainSenderPlaceHolder.getText().toString().trim();
            String addresseeInfo = binding.expressMainReceiverPlaceHolder.getText().toString().trim();
            String dateTime = binding.expressMainExpectedTimeValue.getText().toString().trim();
            String goodsInfo = binding.expressMainGoodInfoValue.getText().toString().trim();
            String goodsName = et_article_detail_name.getText().toString();
            if (equals(senderInfo)) {
                Toast.makeText(requireActivity(), "Sender info can not be null ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (equals(addresseeInfo)) {
                Toast.makeText(requireActivity(), "Receiver info can not be null", Toast.LENGTH_SHORT).show();
                return;
            }

            if (equals(dateTime)) {
                Toast.makeText(requireActivity(), "Expeceted Time can not be null", Toast.LENGTH_SHORT).show();
                return;
            }

            if (equals(goodsInfo)) {
                Toast.makeText(requireActivity(), "Goods info can not be null", Toast.LENGTH_SHORT).show();
                return;
            }
            expressModel=new ExpressModel();
            expressModel.setSenderInfo(senderInfo);
            expressModel.setAddresseeInfo(addresseeInfo);
            expressModel.setSenderType(typeStr);
            expressModel.setDateTime(dateTime);
            expressModel.setPayType(payType);
            expressModel.setGoodsName(goodsName);
            expressModel.setGoodsWeight(weight);
            expressModel.setPrice(price);
            String strRand = "";
            for (int i = 0; i < 8; i++) {
                strRand += String.valueOf((int) (Math.random() * 10));
            }

            expressModel.setExpressId(strRand);
            expressModel.setUid(user.getUid());
            expressList.add(expressModel);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            DatabaseReference dataBaseRef = myRef.child("data").child("express");
            dataBaseRef.setValue(expressList);
            dataBaseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //添加到数据库成功
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }

                    //跳转到详情页
                    Intent intent = new Intent(requireActivity(), ExpressDetailsActivity.class);
                    intent.putExtra("expressModel", expressModel);
                    intent.putExtra("user", user);
                    startActivity(intent);
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


        initTime();
        getExpressList();
        return binding.getRoot();
    }

    //获取全部的订单

    private void getExpressList() {
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
                if (models != null && models.size() > 0) {
                    expressList.clear();
                    expressList.addAll(models);
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


    /**
     * 初始化日期选择控件
     */
    private void initTime() {
        timePickerView = new TimePickerBuilder(requireActivity(), (date, v) -> binding.expressMainExpectedTimeValue.setText(getTime(date)))
                .setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
                .setLineSpacingMultiplier(2.5f)
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("", "", "", "h", "m", "s")
                .setSubmitText("Confirm")
                .setCancelText("Cancel")
                .isDialog(true)
                .addOnCancelClickListener(view -> Log.i("pvTime", "onCancelClickListener"))
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
