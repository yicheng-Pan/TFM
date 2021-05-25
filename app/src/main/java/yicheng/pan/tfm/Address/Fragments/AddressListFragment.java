package yicheng.pan.tfm.Address.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import yicheng.pan.tfm.Adapter.AddressAdapter;
import yicheng.pan.tfm.Address.AddAddressActivity;
import yicheng.pan.tfm.Address.AddressViewModel;
import yicheng.pan.tfm.Address.UpdateAddressActivity;
import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Model.AddressModel;
import yicheng.pan.tfm.User;
import yicheng.pan.tfm.databinding.FragmentAddressBinding;


public class AddressListFragment extends BaseFragment {

    private final String TAG = "Frag-ADDRESS";
    private FragmentAddressBinding binding;
    private AddressViewModel addressViewModel;
    private AddressAdapter adapter;
    private List<AddressModel> list = new ArrayList<>();
    private int select = 0;
    private ProgressDialog dialog;

    public AddressListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentAddressBinding.inflate(inflater, container, false);
        addressViewModel = new ViewModelProvider(requireActivity()).get(AddressViewModel.class);
        User user = addressViewModel.getUser();
        select = addressViewModel.getType();
        dialog = new ProgressDialog(requireActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        dialog.show();
        adapter = new AddressAdapter(requireContext(), position -> {
            if (select == 1) {
                AddressModel addressModel = list.get(position);
                String data = "Name：" + addressModel.getName() + "\nNumber：" + addressModel.getPhone() + "\nAddress：" + addressModel.getAddress() + addressModel.getDetailAddress();
                Intent intent = new Intent();
                intent.putExtra("address", data);
                requireActivity().setResult(200, intent);
                requireActivity().finish();

            } else {
                Intent intent = new Intent(requireActivity(), UpdateAddressActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("addressModel", list.get(position));
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        binding.fragmentAddressList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.fragmentAddressList.setAdapter(adapter);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference dataBaseRef = myRef.child("data").child(user.getUid()).child("address");

        dataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (dialog!=null&&dialog.isShowing()){
                    dialog.dismiss();
                }
                GenericTypeIndicator<List<AddressModel>> t = new GenericTypeIndicator<List<AddressModel>>() {
                };
                List<AddressModel> models = snapshot.getValue(t);
                if (models==null){
                    return;
                }
                list.clear();
                list.addAll(models);
                adapter.addData(models);
                addressViewModel.setList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (dialog!=null&&dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });

        binding.fabButtom.setOnClickListener(view -> {

            Intent intent = new Intent(this.getActivity(), AddAddressActivity.class);
            intent.putExtra("user", addressViewModel.getUser());
            intent.putExtra("list", (Serializable) addressViewModel.getList());
            startActivityForResult(intent, 88);

        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}