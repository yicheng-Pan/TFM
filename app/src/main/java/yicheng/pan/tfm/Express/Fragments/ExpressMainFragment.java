package yicheng.pan.tfm.Express.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Express.ExpressViewModel;
import yicheng.pan.tfm.R;
import yicheng.pan.tfm.databinding.FragmentExpressMainBinding;

public class ExpressMainFragment extends BaseFragment {
    private final String TAG = "EXPRESS-MAIN-FRAG";
    private boolean is_op_sender = true;
    private ExpressViewModel expressViewModel;
    private FragmentExpressMainBinding binding;

    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetBehavior mDialogBehavior;

    public ExpressMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        expressViewModel = new ViewModelProvider(requireActivity()).get(ExpressViewModel.class);

        binding = FragmentExpressMainBinding.inflate(inflater, container, false);

        binding.expressMainSenderAddressBook.setOnClickListener(v -> {
            is_op_sender = true;
            showSheetDialog(R.layout.dialog_select_address);
            bottomSheetDialog.show();
        });

        binding.expressMainExpectedTimeValue.setOnClickListener(v -> {
            showSheetDialog(R.layout.dialog_select_time);
            bottomSheetDialog.show();
        });

        binding.expressMainReceiverAddressBook.setOnClickListener(v -> {
            is_op_sender = false;
            showSheetDialog(R.layout.dialog_select_address);
            bottomSheetDialog.show();
        });
        return binding.getRoot();
    }
    @SuppressLint("SetTextI18n")
    private void showSheetDialog(int layout) {
        View view = View.inflate(requireContext(), layout, null);

        if (layout == R.layout.dialog_select_address) {
            TextView textView_info = view.findViewById(R.id.dialog_select_address_info);
            if (is_op_sender)
                textView_info.setText("Sender Address");
            else
                textView_info.setText("Receiver Address");


            List<HashMap<String, String>> data = new ArrayList<>();

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", "pablo");
            hashMap.put("phone", "657011540");
            hashMap.put("address", "Av.oporto,73");
            data.add(hashMap);

            HashMap<String, String> hashMap1 = new HashMap<>();
            hashMap1.put("name", "jaime");
            hashMap1.put("phone", "659994510");
            hashMap1.put("address", "Av.oporto,74");
            data.add(hashMap1);

            SimpleAdapter adapter = new SimpleAdapter(
                    requireContext(),
                    data,
                    R.layout.item_address,
                    new String[]{"name", "phone", "address"},
                    new int[]{R.id.address_name, R.id.address_phone, R.id.address_address}
            );
            ListView listView = view.findViewById(R.id.dialog_select_address_list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((parent, view1, position, id) -> {
                String address = data.get(position).get("address");
                String nameAndPhone = data.get(position).get("name") + "  " + data.get(position).get("phone");
                if (is_op_sender) {
                    binding.expressMainSenderAddress.setText(address);
                    binding.expressMainSenderNameAndPhone.setText(nameAndPhone);
                    binding.expressMainSenderInfo.setVisibility(View.VISIBLE);
                    binding.expressMainSenderPlaceHolder.setVisibility(View.GONE);
                } else {
                    binding.expressMainReceiverAddress.setText(address);
                    binding.expressMainReceiverNameAndPhone.setText(nameAndPhone);
                    binding.expressMainReceiverInfo.setVisibility(View.VISIBLE);
                    binding.expressMainReceiverPlaceHolder.setVisibility(View.GONE);
                }
                bottomSheetDialog.cancel();
            });
        }

        if (layout == R.layout.dialog_select_time) {

            List<String> data = new ArrayList<>();
            for (int i = 8; i < 22; i++) {
                data.add(i + ":00~" + (i + 1) + ":00");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    data);

            ListView listView = view.findViewById(R.id.dialog_select_time_hour);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view12, position, id) -> {
                RadioGroup radioGroup = view.findViewById(R.id.dialog_select_time_day);
                String selected_time = ((RadioButton)
                        view.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString()
                        + "\n" + data.get(position);
                binding.expressMainExpectedTimeValue.setText(selected_time);
                bottomSheetDialog.cancel();
            });
        }


        // Common
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(view);
        mDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
        mDialogBehavior.setPeekHeight(getPeekHeight());
    }

    protected int getPeekHeight() {
        int peekHeight = getResources().getDisplayMetrics().heightPixels;
        //设置弹窗高度为屏幕高度的3/4
        return peekHeight - peekHeight / 6;
    }
}
