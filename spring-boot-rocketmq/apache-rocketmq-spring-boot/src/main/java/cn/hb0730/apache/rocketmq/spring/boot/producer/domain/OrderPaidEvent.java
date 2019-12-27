package cn.hb0730.apache.rocketmq.spring.boot.producer.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * <p>
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
public class OrderPaidEvent implements Serializable {
    private static final long serialVersionUID = 4271902611339907223L;
    private int orderId;

    private BigDecimal paidMoney;

    public OrderPaidEvent() {
    }

    public OrderPaidEvent(int orderId, BigDecimal paidMoney) {
        this.orderId = orderId;
        this.paidMoney = paidMoney;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(BigDecimal paidMoney) {
        this.paidMoney = paidMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPaidEvent that = (OrderPaidEvent) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(paidMoney, that.paidMoney);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, paidMoney);
    }

    @Override
    public String toString() {
        return "OrderPaidEvent{" +
                "orderId='" + orderId + '\'' +
                ", paidMoney=" + paidMoney +
                '}';
    }
}
