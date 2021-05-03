package yicheng.pan.tfm.Auth.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.R;
import yicheng.pan.tfm.databinding.FragmentRegisterBinding;


public class RegisterFragment extends BaseFragment {

    private final String TAG = "AUTH_REGISTER";
    private FirebaseAuth mAuth;
    private FragmentRegisterBinding binding;


    public RegisterFragment() {
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
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        binding.authRegisterBtnRegister.setOnClickListener(v -> {
            if (!validateForm())
                return;
            createAccount();
        });
        binding.authRegisterBtnReturn.setOnClickListener(v -> {

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.auth_fragment_container, LoginFragment.class, null)
                    .commit();
        });
        return binding.getRoot();
    }
    private void createAccount() {
        String email = binding.authRegisterUsername.getText().toString();
        String password = binding.authRegisterPass.getText().toString();
        Log.d(TAG, "createAccount:" + email);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        showToast("createUserWithEmail:success");
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        showToast("Authentication failed.");
                    }
                });
    }
    private boolean validateForm() {
        // TODO: complete the logic of validate
        return true;
    }

}