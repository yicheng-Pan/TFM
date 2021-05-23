package yicheng.pan.tfm.Address;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import yicheng.pan.tfm.R;


public class AddressListActivity extends AppCompatActivity {

    private AddressViewModel addressViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);
        if (getIntent() != null) {
            addressViewModel.setUser(getIntent().getParcelableExtra("user"));
            addressViewModel.setType(getIntent().getIntExtra("select",0));
        }

        setContentView(R.layout.activity_address_list);
    }
}
