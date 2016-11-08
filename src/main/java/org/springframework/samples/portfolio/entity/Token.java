package org.springframework.samples.portfolio.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by thinhdd on 10/18/2016.
 */
@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(nullable = false, length = 36)
    private String id;
    @Column(length = 32, unique = false, nullable = false)
    private String userId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredDate;
    @Column(nullable = false, length = 36)
    private String tokenData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getTokenData() {
        return tokenData;
    }

    public void setTokenData(String tokenData) {
        this.tokenData = tokenData;
    }
}
