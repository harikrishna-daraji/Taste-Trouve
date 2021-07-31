package com.example.tastetrouverestaurantowner.Modal;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductModal implements Parcelable {
    String  _id;

    String  name;

    String image;
    Integer  price;
    String  description;
    String  calories;
    Integer  quantity;
    Boolean  kidSection;
    Boolean  popular;
    Boolean  visibleStatus;
    String  DeliveryTime;
    Boolean specialOffer;
    String specialType;


    public ProductModal(String _id, String name, String image, Integer price, String description, String calories, Integer quantity, Boolean kidSection, Boolean popular, Boolean visibleStatus, String deliveryTime, Boolean specialOffer, String specialType) {
        this._id = _id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.calories = calories;
        this.quantity = quantity;
        this.kidSection = kidSection;
        this.popular = popular;
        this.visibleStatus = visibleStatus;
        DeliveryTime = deliveryTime;
        this.specialOffer = specialOffer;
        this.specialType = specialType;
    }


    public ProductModal(Parcel in) {
        _id = in.readString();
        name = in.readString();
        image = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readInt();
        }
        description = in.readString();
        calories = in.readString();
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readInt();
        }
        byte tmpKidSection = in.readByte();
        kidSection = tmpKidSection == 0 ? null : tmpKidSection == 1;
        byte tmpPopular = in.readByte();
        popular = tmpPopular == 0 ? null : tmpPopular == 1;
        byte tmpVisibleStatus = in.readByte();
        visibleStatus = tmpVisibleStatus == 0 ? null : tmpVisibleStatus == 1;
        DeliveryTime = in.readString();
        byte tmpSpecialOffer = in.readByte();
        specialOffer = tmpSpecialOffer == 0 ? null : tmpSpecialOffer == 1;
        specialType = in.readString();
    }

    public static final Creator<ProductModal> CREATOR = new Creator<ProductModal>() {
        @Override
        public ProductModal createFromParcel(Parcel in) {
            return new ProductModal(in);
        }

        @Override
        public ProductModal[] newArray(int size) {
            return new ProductModal[size];
        }
    };



    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getKidSection() {
        return kidSection;
    }

    public void setKidSection(Boolean kidSection) {
        this.kidSection = kidSection;
    }

    public Boolean getPopular() {
        return popular;
    }

    public void setPopular(Boolean popular) {
        this.popular = popular;
    }

    public Boolean getVisibleStatus() {
        return visibleStatus;
    }

    public void setVisibleStatus(Boolean visibleStatus) {
        this.visibleStatus = visibleStatus;
    }

    public String getDeliveryTime() {
        return DeliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        DeliveryTime = deliveryTime;
    }

    public Boolean getSpecialOffer() {
        return specialOffer;
    }

    public void setSpecialOffer(Boolean specialOffer) {
        this.specialOffer = specialOffer;
    }

    public String getSpecialType() {
        return specialType;
    }

    public void setSpecialType(String specialType) {
        this.specialType = specialType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(image);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(price);
        }
        dest.writeString(description);
        dest.writeString(calories);
        if (quantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity);
        }
        dest.writeByte((byte) (kidSection == null ? 0 : kidSection ? 1 : 2));
        dest.writeByte((byte) (popular == null ? 0 : popular ? 1 : 2));
        dest.writeByte((byte) (visibleStatus == null ? 0 : visibleStatus ? 1 : 2));
        dest.writeString(DeliveryTime);
        dest.writeByte((byte) (specialOffer == null ? 0 : specialOffer ? 1 : 2));
        dest.writeString(specialType);
    }


}





