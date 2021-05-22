package yicheng.pan.tfm.Main;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import yicheng.pan.tfm.User;

public class MainViewModel extends ViewModel {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
