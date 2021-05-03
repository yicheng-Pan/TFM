package yicheng.pan.tfm;

import android.widget.Toast;
import androidx.fragment.app.Fragment;


public class BaseFragment extends Fragment {

    protected void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }
}
