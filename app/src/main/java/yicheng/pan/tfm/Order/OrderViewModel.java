package yicheng.pan.tfm.Order;

import androidx.lifecycle.ViewModel;

import yicheng.pan.tfm.Model.ExpressModel;
import yicheng.pan.tfm.User;

public class OrderViewModel extends ViewModel {
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
