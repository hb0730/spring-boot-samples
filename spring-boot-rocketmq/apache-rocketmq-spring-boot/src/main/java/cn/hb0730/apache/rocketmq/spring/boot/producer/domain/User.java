package cn.hb0730.apache.rocketmq.spring.boot.producer.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class User implements Serializable {
    private static final long serialVersionUID = 3146030732577082505L;
    private String userName;
    private Byte userAge;

    public User() {
    }

    public User(String userName, Byte userAge) {
        this.userName = userName;
        this.userAge = userAge;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Byte getUserAge() {
        return userAge;
    }

    public void setUserAge(Byte userAge) {
        this.userAge = userAge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) &&
                Objects.equals(userAge, user.userAge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userAge);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userAge=" + userAge +
                '}';
    }
}
