package yicheng.pan.tfm.Address;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import yicheng.pan.tfm.Model.AddressModel;
import yicheng.pan.tfm.R;

public class AddAddressActivity extends AppCompatActivity {

    private AddressViewModel addressViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressViewModel = new ViewModelProvider(this).get(AddressViewModel.class);
        if (getIntent() != null) {
            addressViewModel.setUser(getIntent().getParcelableExtra("user"));
            addressViewModel.setList((List<AddressModel>) getIntent().getSerializableExtra("list"));
        }

        setContentView(R.layout.activity_add_address);
    }
}
