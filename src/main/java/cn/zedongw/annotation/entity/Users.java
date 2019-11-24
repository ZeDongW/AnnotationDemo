package cn.zedongw.annotation.entity;

import cn.zedongw.annotation.myannotation.Column;
import cn.zedongw.annotation.myannotation.Id;
import cn.zedongw.annotation.myannotation.Table;

/**
 * @ClassName Users
 * @Description: 用户实体类
 * @Author ZeDongW
 * @Date 2019/11/24/0024 11:35
 * @Version 1.0
 * @Modified By:
 * @Modified Time:
 **/
@Table(tablename = "i_users")
public class Users {

    @Id
    @Column(columnName = "i_id")
    private int id; //用户id

    @Column(columnName = "i_userName")
    private String userName; //用户名

    @Column(columnName = "i_age")
    private int age; //年龄

    public Users() {
    }

    public Users(int id, String userName, int age) {
        this.id = id;
        this.userName = userName;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
