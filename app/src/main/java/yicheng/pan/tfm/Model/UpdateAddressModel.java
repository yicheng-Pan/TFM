package yicheng.pan.tfm.Model;

import androidx.lifecycle.ViewModel;

import yicheng.pan.tfm.User;


public class UpdateAddressModel extends ViewModel {

    private int position;
    private AddressModel addressModel;
     private User user;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public AddressModel getAddressModel() {
        return addressModel;
    }

    public void setAddressModel(AddressModel addressModel) {
        this.addressModel = addressModel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
