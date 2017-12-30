package bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/12/30.
 */

@Entity
public class cartBean {
    @Id
    private Long id;
    private String name;
    private String price;
    private String desc;
    private String img;
    private boolean isCheck;
    private int num;
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
    public String getImg() {
        return this.img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getPrice() {
        return this.price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1565664157)
    public cartBean(Long id, String name, String price, String desc, String img,
            boolean isCheck, int num) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.img = img;
        this.isCheck = isCheck;
        this.num = num;
    }
    @Generated(hash = 360956566)
    public cartBean() {
    }
}
