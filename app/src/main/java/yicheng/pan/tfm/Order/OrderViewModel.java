package yicheng.pan.tfm.Order;

import androidx.lifecycle.ViewModel;

import java.util.List;

import yicheng.pan.tfm.Model.ExpressModel;
import yicheng.pan.tfm.Model.GoodsLocationModel;
import yicheng.pan.tfm.User;

public class OrderViewModel extends ViewModel {
    private User user;
    private ExpressModel expressModel;
    private List<GoodsLocationModel> list;

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
    public List<GoodsLocationModel> getList() {
        return list;
    }

    public void setList(List<GoodsLocationModel> list) {
        this.list = list;
    }
}
