package yicheng.pan.tfm.Order;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import yicheng.pan.tfm.Model.ExpressModel;
import yicheng.pan.tfm.Model.GoodsLocationModel;
import yicheng.pan.tfm.R;

public class AddLocationActivity extends AppCompatActivity {

    private OrderViewModel orderViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        if (getIntent() != null) {
            orderViewModel.setExpressModel((ExpressModel) getIntent().getSerializableExtra("expressModel"));
            orderViewModel.setList((List<GoodsLocationModel>) getIntent().getSerializableExtra("list"));
        }

        setContentView(R.layout.activity_add_location);
    }
}
