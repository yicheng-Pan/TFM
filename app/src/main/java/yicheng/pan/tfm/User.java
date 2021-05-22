package yicheng.pan.tfm;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String uid;
    private String name;
    private String password;
    private int isCourier;  //是否是快递员 1：是，0：普通用户

    public User() {
    }

    public User(String uid, String name, String password, int isCourier) {
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.isCourier = isCourier;
    }

    protected User(Parcel in) {
        uid = in.readString();
        name = in.readString();
        password = in.readString();
        isCourier = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public yicheng.pan.tfm.User createFromParcel(Parcel in) {
            return new yicheng.pan.tfm.User(in);
        }

        @Override
        public yicheng.pan.tfm.User[] newArray(int size) {
            return new yicheng.pan.tfm.User[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsCourier() {
        return isCourier;
    }

    public void setIsCourier(int isCourier) {
        this.isCourier = isCourier;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(name);
        parcel.writeString(password);
        parcel.writeInt(isCourier);
    }
}
