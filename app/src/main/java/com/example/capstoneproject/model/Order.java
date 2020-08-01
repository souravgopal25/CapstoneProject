package com.example.capstoneproject.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
public class Order implements Parcelable {
    public List<CartItem> cartItems;
    public float price;
    public int orderNo;

    protected Order(Parcel in) {
        cartItems = in.createTypedArrayList(CartItem.CREATOR);
        price = in.readFloat();
        orderNo = in.readInt();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public Order(List<CartItem> cartItems, float price, int orderNo) {
        this.cartItems = cartItems;
        this.price = price;
        this.orderNo = orderNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(cartItems);
        parcel.writeFloat(price);
        parcel.writeInt(orderNo);
    }
}
