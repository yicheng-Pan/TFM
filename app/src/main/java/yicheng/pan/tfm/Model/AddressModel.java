package yicheng.pan.tfm.Model;

import java.io.Serializable;

public class AddressModel implements Serializable{

    private String id;
    private String name;
    private String phone;
    private String address;
    private String detailAddress;
    private int key;

    public AddressModel() {
    }



    public String getId() {
        return id;
    }

    public void setId(String uid) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
