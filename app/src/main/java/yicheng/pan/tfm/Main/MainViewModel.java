package yicheng.pan.tfm.Main;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

public class MainViewModel extends ViewModel {
    private FirebaseUser user;

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }


}
