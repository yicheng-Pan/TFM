package yicheng.pan.tfm.Auth.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.R;
import yicheng.pan.tfm.User;
import yicheng.pan.tfm.databinding.FragmentRegisterBinding;


public class RegisterFragment extends BaseFragment {

    private final String TAG = "AUTH_REGISTER";
    private FirebaseAuth mAuth;
    private FragmentRegisterBinding binding;
    private ProgressDialog dialog;
    private int isCourier = 0;
    private List<User> list = new ArrayList<>();
    private User user;


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
        dialog = new ProgressDialog(requireActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading...");
        getUserList();
        binding.authRegisterBtnRegister.setOnClickListener(v -> {
           /* if (!validateForm())
                return;
            createAccount();*/
            addUser();
        });


        binding.authRegisterBtnReturn.setOnClickListener(v -> {

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.auth_fragment_container, LoginFragment.class, null)
                    .commit();
        });

        binding.cbUsers.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.cbCourier.setChecked(false);
                isCourier = 0;
            }
        });

        binding.cbCourier.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                binding.cbUsers.setChecked(false);
                isCourier = 1;
            }
        });

        return binding.getRoot();
    }

    /**
     * 添加用户
     */
    private void addUser() {
        String email = binding.authRegisterUsername.getText().toString();
        String password = binding.authRegisterPass.getText().toString();
        Log.d(TAG, "createAccount:" + email);
        if ("".equals(email)) {
            showToast("Email cannot be empty");
            return;
        }

        if ("".equals(password)) {
            showToast("Password cannot be empty");
            return;
        }

        if (dialog != null) {
            dialog.show();
        }
        user=new User();
        user.setIsCourier(isCourier);
        user.setName(email);
        user.setPassword(password);
        UUID uuid = UUID.randomUUID();
        user.setUid(uuid.toString());

        list.add(user);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference dataBaseRef = myRef.child("data").child("user");
        dataBaseRef.setValue(list);
        dataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.auth_fragment_container, LoginFragment.class, null)
                        .commit();
                showToast("createUserWithEmail:success");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    /**
     * 获取全部的用户
     */
    private void getUserList() {
        if (dialog != null) {
            dialog.show();
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference dataBaseRef = myRef.child("data").child("user");

        dataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                GenericTypeIndicator<List<User>> t = new GenericTypeIndicator<List<User>>() {
                };
                List<User> models = snapshot.getValue(t);
                if (models == null) {
                    return;
                }
                list.clear();
                list.addAll(models);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    private void createAccount() {
        String email = binding.authRegisterUsername.getText().toString();
        String password = binding.authRegisterPass.getText().toString();
        Log.d(TAG, "createAccount:" + email);

        if (dialog != null) {
            dialog.show();
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.auth_fragment_container, LoginFragment.class, null)
                                .commit();
                        showToast("createUserWithEmail:success");
                    } else {
                        // If sign in fails, display a message to the user.
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
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