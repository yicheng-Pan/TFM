package yicheng.pan.tfm.Express;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import yicheng.pan.tfm.Model.ExpressModel;
import yicheng.pan.tfm.R;

public class ExpressDetailsActivity extends AppCompatActivity {

    private ExpressViewModel expressViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expressViewModel = new ViewModelProvider(this).get(ExpressViewModel.class);
        if (getIntent() != null) {
            expressViewModel.setUser(getIntent().getParcelableExtra("user"));
            expressViewModel.setExpressModel((ExpressModel) getIntent().getSerializableExtra("expressModel"));
        }

        setContentView(R.layout.activity_express_details);
    }
}
