package yicheng.pan.tfm.Address;

import androidx.lifecycle.ViewModel;

import java.util.List;

import yicheng.pan.tfm.Model.AddressModel;
import yicheng.pan.tfm.User;

public class AddressViewModel extends ViewModel {

    private User user;
    private List<AddressModel> list;
    private int type = 0;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AddressModel> getList() {
        return list;
    }

    public void setList(List<AddressModel> list) {
        this.list = list;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
