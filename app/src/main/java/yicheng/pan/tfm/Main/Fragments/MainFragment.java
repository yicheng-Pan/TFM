package yicheng.pan.tfm.Main.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Main.MainViewModel;
import yicheng.pan.tfm.R;
import yicheng.pan.tfm.databinding.FragmentMainBinding;


public class MainFragment extends BaseFragment {

    private MainViewModel mainViewModel;
    private FragmentMainBinding binding;

    public MainFragment() {
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

        binding = FragmentMainBinding.inflate(inflater, container, false);

        binding.mainBtnAddressBook.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment_container, AddressFragment.class, null)
                    .commit();
        });
        return binding.getRoot();
    }
}