package yicheng.pan.tfm.Address.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.OnAddressSelectedListener;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;
import yicheng.pan.tfm.Address.AddressViewModel;
import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Model.AddressModel;
import yicheng.pan.tfm.User;
import yicheng.pan.tfm.databinding.FragmentAddAddressBinding;


public class AddAddressFragment extends BaseFragment implements OnAddressSelectedListener {

    public AddAddressFragment(){}

    private AddressViewModel addressViewModel;
    private FragmentAddAddressBinding binding;
    private List<AddressModel> list=new ArrayList<>();
    private ProgressDialog dialog;
    private BottomDialog bottomDialog;
    private String county = "";
    private String province = "";
    private String city = "";
    private String street = "";
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentAddAddressBinding.inflate(inflater, container, false);
        addressViewModel = new ViewModelProvider(requireActivity()).get(AddressViewModel.class);
        user = addressViewModel.getUser();
        if (addressViewModel.getList()!=null){
            list.addAll(addressViewModel.getList());
        }

        dialog = new ProgressDialog(requireActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");

        binding.btnAdd.setOnClickListener(view -> {
            String name = binding.etName.getText().toString().trim();
            String phone = binding.etPhone.getText().toString().trim();
            String address = binding.etAddress.getText().toString().trim();
            String detailsAddress = binding.etDetailsAddress.getText().toString().trim();
            if ("".equals(name)) {
                showToast("Name cannot be empty");
                return;
            }

            if ("".equals(phone)) {
                showToast("phone cannot be empty");
                return;
            }

            if ("".equals(address)) {
                showToast("address cannot be empty");
                return;
            }

            if ("".equals(detailsAddress)) {
                showToast("detailsAddress cannot be empty");
                return;
            }
            if (dialog != null) {
                dialog.show();
            }

            AddressModel addressModel = new AddressModel();
            addressModel.setId(user.getUid());
            addressModel.setName(name);
            addressModel.setPhone(phone);
            addressModel.setAddress(address);
            addressModel.setDetailAddress(detailsAddress);
            list.add(addressModel);

            addData(user.getUid(), list);
        });

        binding.etAddress.setOnClickListener(view -> {
            bottomDialog = new BottomDialog(requireActivity());
            bottomDialog.setOnAddressSelectedListener(this::onAddressSelected);
            bottomDialog.show();
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void addData(String uid, List<AddressModel> list) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference dataBaseRef = myRef.child("data").child(uid).child("address");
        dataBaseRef.setValue(list);
        dataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (isAdded()){
                    requireActivity().setResult(200);
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

    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
        if (bottomDialog != null) {
            bottomDialog.dismiss();
        }
        this.county = county == null ? "" : county.name;
        this.province = province == null ? "" : province.name;
        this.city = city == null ? "" : city.name;
        this.street = street == null ? "" : street.name;
        binding.etAddress.setText(this.province + this.city + this.county + this.street);
    }
}
