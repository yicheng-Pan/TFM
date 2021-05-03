package yicheng.pan.tfm.Auth.Fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.R;
import yicheng.pan.tfm.databinding.FragmentLoginBinding;

public class LoginFragment extends BaseFragment {

    private final String TAG = "AUTH_LOGIN";
    private FirebaseAuth mAuth;
    private FragmentLoginBinding binding;

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
            if (!validateForm())
                return;
        });
        binding.authLoginBtnRegister.setOnClickListener(v -> {

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.auth_fragment_container, RegisterFragment.class, null)
                    .commit();
        });

        return binding.getRoot();
    }
    private boolean validateForm() {
        // TODO: complete the logic of validate
        return true;
    }

}