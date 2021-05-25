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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;
import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.OnAddressSelectedListener;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;
import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Model.AddressModel;
import yicheng.pan.tfm.Model.UpdateAddressModel;
import yicheng.pan.tfm.User;
import yicheng.pan.tfm.databinding.FragmentUpdateAddressBinding;


public class UpdateAddressFragment extends BaseFragment implements OnAddressSelectedListener {

    public UpdateAddressFragment(){

    }

   private UpdateAddressModel updateAddressModel;
    private FragmentUpdateAddressBinding binding;
    private List<AddressModel> list;
    private ProgressDialog dialog;
    private BottomDialog bottomDialog;
    private String county = "";
    private String province = "";
    private String city = "";
    private String street = "";
    private User user;
    private int position=0;
    private AddressModel addressModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentUpdateAddressBinding.inflate(inflater, container, false);
        updateAddressModel = new ViewModelProvider(requireActivity()).get(UpdateAddressModel.class);
        user = updateAddressModel.getUser();
        position=updateAddressModel.getPosition();
        addressModel=updateAddressModel.getAddressModel();

        dialog = new ProgressDialog(requireActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");

        if (addressModel!=null){
            binding.etName.setText(addressModel.getName());
            binding.etPhone.setText(addressModel.getPhone());
            binding.etAddress.setText(addressModel.getAddress());
            binding.etDetailsAddress.setText(addressModel.getDetailAddress());
        }

        binding.btnUpdate.setOnClickListener(view -> {
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

            addressModel.setId(user.getUid());
            addressModel.setName(name);
            addressModel.setPhone(phone);
            addressModel.setAddress(address);
            addressModel.setDetailAddress(detailsAddress);

            updateData(user.getUid());
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

    private void updateData(String uid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference dataBaseRef = myRef.child("data").child(uid).child("address").child(""+position);
        dataBaseRef.setValue(addressModel);
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
