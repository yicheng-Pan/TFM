package yicheng.pan.tfm.Order;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import yicheng.pan.tfm.R;

public class OrderListActivity extends AppCompatActivity {

    private OrderViewModel orderViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        if (getIntent() != null) {
            orderViewModel.setUser(getIntent().getParcelableExtra("user"));
        }

        setContentView(R.layout.activity_order_list);
    }
}
