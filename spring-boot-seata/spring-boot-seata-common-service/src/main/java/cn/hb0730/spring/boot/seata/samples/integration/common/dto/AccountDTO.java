package cn.hb0730.spring.boot.seata.samples.integration.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 账户信息
 * </P>
 *
 * @author bing_huang
 * @since V1.0
 */
@Data
public class AccountDTO implements Serializable {
    private Integer id;

    private String userId;

    private BigDecimal amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
