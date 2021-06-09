package yicheng.pan.tfm.Main.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Main.MainViewModel;
import yicheng.pan.tfm.Order.NotReceiveOrderListActivity;
import yicheng.pan.tfm.User;
import yicheng.pan.tfm.databinding.FragmentCourierMainBinding;
import yicheng.pan.tfm.databinding.FragmentMainBinding;

import static android.app.Activity.RESULT_OK;


public class CourierMainFragment extends BaseFragment {

    private MainViewModel mainViewModel;
    private FragmentCourierMainBinding binding;
    private ProgressDialog dialog;

    public CourierMainFragment() {
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
        binding = FragmentCourierMainBinding.inflate(inflater, container, false);
        dialog = new ProgressDialog(this.getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        binding.mainBtnExpressSend.setOnClickListener(v -> {
            User user = mainViewModel.getUser();
            Intent intent = new Intent(this.getActivity(), NotReceiveOrderListActivity.class);
            intent.putExtra("user", mainViewModel.getUser());
            startActivity(intent);

        });

        return binding.getRoot();
    }

}