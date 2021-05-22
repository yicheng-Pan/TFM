package yicheng.pan.tfm.Main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;

import yicheng.pan.tfm.R;
import yicheng.pan.tfm.User;


public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        if (getIntent() != null) {
            User user = getIntent().getParcelableExtra("user");
            mainViewModel.setUser(user);
        }
    }

}