package yicheng.pan.tfm.Auth.Fragments;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Objects;
import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.Main.MainActivity;
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
            login();
        });


        binding.authLoginBtnRegister.setOnClickListener(v -> getParentFragmentManager().beginTransaction()
                .replace(R.id.auth_fragment_container, RegisterFragment.class, null)
                .commit());

        return binding.getRoot();
    }

    private void login() {
        String email = binding.authLoginUsername.getText().toString();
        String password = binding.authLoginPass.getText().toString();
        Log.d(TAG, "login:" + email);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "loginWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        if (!user.isEmailVerified()) {
                            sendVerified(user, email);
                        } else {
                            showToast("Login succeed");
                            Intent intent = new Intent(this.getActivity(), MainActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "loginWithEmail:failure", Objects.requireNonNull(task.getException()).getCause());
                        showToast("Authentication failed.");
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;
        EditText editText_email = binding.authLoginUsername;
        if (TextUtils.isEmpty(editText_email.getText().toString())) {
            editText_email.setError("Required.");
            valid = false;
        } else {
            editText_email.setError(null);
        }

        EditText editText_pass = binding.authLoginPass;
        if (TextUtils.isEmpty(editText_pass.getText().toString())) {
            editText_pass.setError("Required.");
            valid = false;
        } else {
            editText_pass.setError(null);
        }
        return valid;
    }

    public void sendVerified(FirebaseUser user, String email) {
        showToast(email + " is not verified!");
        user.sendEmailVerification().addOnCompleteListener(requireActivity(), task1 -> {
            if (task1.isSuccessful()) {
                showToast("Send Verification Email to " + email);
            } else {
                showToast("Failed to send Verification Email to " + email);
            }
        });
    }
}