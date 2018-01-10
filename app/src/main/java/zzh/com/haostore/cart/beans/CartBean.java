package zzh.com.haostore.cart.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/12/31.
 */

@Entity
public class CartBean {
    @Id
    private long cartID;
    private String product_id;
    private String name;
    private String price;
    private String imgURL;
    private int num;
    private boolean isCheck;
    @Generated(hash = 56798631)
    public CartBean(long cartID, String product_id, String name, String price,
            String imgURL, int num, boolean isCheck) {
        this.cartID = cartID;
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.imgURL = imgURL;
        this.num = num;
        this.isCheck = isCheck;
    }
    @Generated(hash = 1446963280)
    public CartBean() {
    }
    public long getCartID() {
        return this.cartID;
    }
    public void setCartID(long cartID) {
        this.cartID = cartID;
    }
    public String getProduct_id() {
        return this.product_id;
    }
    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return this.price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getImgURL() {
        return this.imgURL;
    }
    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
    public int getNum() {
        return this.num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public boolean getIsCheck() {
        return this.isCheck;
    }
    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    @Override
    public String toString() {
        return "CartBean{" +
                "cartID=" + cartID +
                ", product_id='" + product_id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", num=" + num +
                ", isCheck=" + isCheck +
                '}';
    }
}
