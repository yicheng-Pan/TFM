package yicheng.pan.tfm.Auth.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Main.CourierMainActivity;
import yicheng.pan.tfm.Main.MainActivity;
import yicheng.pan.tfm.R;
import yicheng.pan.tfm.User;
import yicheng.pan.tfm.databinding.FragmentLoginBinding;


public class LoginFragment extends BaseFragment {

    private final String TAG = "AUTH_LOGIN";
    private FirebaseAuth mAuth;
    private FragmentLoginBinding binding;
    private ProgressDialog dialog;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(inflater, container, false);


        binding.authLoginBtnLogin.setOnClickListener(v -> {
            login();
        });
        dialog = new ProgressDialog(requireActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");

        binding.authLoginBtnRegister.setOnClickListener(v -> {

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.auth_fragment_container, RegisterFragment.class, null)
                    .commit();
        });

        return binding.getRoot();
    }

    private void login() {
        if (dialog != null) {
            dialog.show();
        }
        String email = binding.authLoginUsername.getText().toString();
        String password = binding.authLoginPass.getText().toString();

        if ("".equals(email)) {
            showToast("Email cannot be empty");
            return;
        }

        if ("".equals(password)) {
            showToast("Password cannot be empty");
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("data").child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                GenericTypeIndicator<List<User>> t = new GenericTypeIndicator<List<User>>() {
                };
                List<User> models = snapshot.getValue(t);
                User user = null;

                if (models == null) {
                    showToast("The user does not exist");
                    return;
                }
                boolean flag = false;
                for (int i = 0; i < models.size(); i++) {
                    user = models.get(i);
                    if (email.equals(user.getName())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    user = null;
                }

                if (user == null) {
                    showToast("The user does not exist");
                    return;
                }

                if (!user.getPassword().equals(password)) {
                    showToast("The password is incorrect");
                    return;
                }

                showToast("Login Success");
                Intent intent=null;
                if (user.getIsCourier()==0){
                    intent = new Intent(requireActivity(), MainActivity.class);
                }else {
                    intent = new Intent(requireActivity(), CourierMainActivity.class);
                }
                intent.putExtra("user", user);
                startActivity(intent);
                requireActivity().finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showToast("Login failed");
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
}