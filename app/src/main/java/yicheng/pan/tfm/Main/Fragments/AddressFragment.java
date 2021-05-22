package yicheng.pan.tfm.Main.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Main.MainViewModel;
import yicheng.pan.tfm.R;
import yicheng.pan.tfm.databinding.FragmentAddressBinding;


public class AddressFragment extends BaseFragment {

    private final String TAG = "Frag-ADDRESS";
    private FragmentAddressBinding binding;
    private MainViewModel mainViewModel;

    public AddressFragment() {
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
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

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

        //binding.fragmentAddressList.setAdapter(adapter);
        return binding.getRoot();
    }
}
