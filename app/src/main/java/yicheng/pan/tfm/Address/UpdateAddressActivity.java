package yicheng.pan.tfm.Address;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import yicheng.pan.tfm.Model.AddressModel;
import yicheng.pan.tfm.Model.UpdateAddressModel;
import yicheng.pan.tfm.R;


public class UpdateAddressActivity extends AppCompatActivity {

    private UpdateAddressModel updateAddressModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateAddressModel= new ViewModelProvider(this).get(UpdateAddressModel.class);
        if (getIntent() != null) {
            updateAddressModel.setUser(getIntent().getParcelableExtra("user"));
            updateAddressModel.setPosition(getIntent().getIntExtra("position",0));
            updateAddressModel.setAddressModel((AddressModel) getIntent().getSerializableExtra("addressModel"));
        }

        setContentView(R.layout.activity_update_address);

    }
}
