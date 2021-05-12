package yicheng.pan.tfm.Express;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import yicheng.pan.tfm.R;

public class ExpressActivity extends AppCompatActivity {

    private ExpressViewModel expressViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expressViewModel = new ViewModelProvider(this).get(ExpressViewModel.class);

        if (getIntent() != null) {
            expressViewModel.setUser(getIntent().getParcelableExtra("user"));
        }

        setContentView(R.layout.activity_express);
    }
}
