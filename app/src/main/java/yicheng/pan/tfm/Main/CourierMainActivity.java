package yicheng.pan.tfm.Main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import yicheng.pan.tfm.R;
import yicheng.pan.tfm.User;


public class CourierMainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        if (getIntent() != null) {
            User user = getIntent().getParcelableExtra("user");
            mainViewModel.setUser(user);
        }
    }
}