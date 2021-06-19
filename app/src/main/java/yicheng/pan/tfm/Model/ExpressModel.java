package yicheng.pan.tfm.Model;

import java.io.Serializable;

public class ExpressModel implements Serializable {

    private String senderInfo;
    private String addresseeInfo;
    private String senderType;
    private String dateTime;
    private String payType;
    private double price;
    private String goodsName;
    private int goodsWeight;
    private int key;
    private String goodsImgUrl;
    private String expressId;
    private String expressType;
    private String uid;
    private String courierUid;

    public String getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(String senderInfo) {
        this.senderInfo = senderInfo;
    }

    public String getAddresseeInfo() {
        return addresseeInfo;
    }

    public void setAddresseeInfo(String addresseeInfo) {
        this.addresseeInfo = addresseeInfo;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(int goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getExpressType() {
        return expressType;
    }

    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    public String getUid() {
        return uid;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCourierUid() {
        return courierUid;
    }

    public void setCourierUid(String courierUid) {
        this.courierUid = courierUid;
    }
}
