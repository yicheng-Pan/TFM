package yicheng.pan.tfm.Express;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import yicheng.pan.tfm.Model.ExpressModel;
import yicheng.pan.tfm.User;

public class ExpressViewModel extends ViewModel {
    private User user;
    private ExpressModel expressModel;

    public ExpressModel getExpressModel() {
        return expressModel;
    }

    public void setExpressModel(ExpressModel expressModel) {
        this.expressModel = expressModel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
