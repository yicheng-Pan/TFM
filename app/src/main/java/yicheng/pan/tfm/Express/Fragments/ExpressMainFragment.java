package yicheng.pan.tfm.Express.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yicheng.pan.tfm.BaseFragment;
import yicheng.pan.tfm.R;

public class ExpressMainFragment extends BaseFragment {

    public ExpressMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_express_main, container, false);
    }
}
