package com.mascot.server.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Nikolay on 20.02.2017.
 */
@Entity
@Table(name = "t_order")
public class Order extends Identified {
    @Column(name = "type")
    private OrderType type;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "modify_date")
    private Date modifyDate;

    @Column(name = "send_date")
    private Date sendDate;

    @Column(name = "sent")
    private Boolean sent;

    @Column(name = "cost")
    private Double cost;

    @OneToMany(targetEntity = OrderProductLine.class, fetch = FetchType.LAZY, mappedBy = "order")
    private Set<OrderProductLine> productLines;

    @OneToMany(targetEntity = OrderClothLine.class, fetch = FetchType.LAZY, mappedBy = "order")
    private Set<OrderClothLine> clothLines;

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Boolean getSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Set<OrderProductLine> getProductLines() {
        return productLines;
    }

    public void setProductLines(Set<OrderProductLine> productLines) {
        this.productLines = productLines;
    }

    public Set<OrderClothLine> getClothLines() {
        return clothLines;
    }

    public void setClothLines(Set<OrderClothLine> clothLines) {
        this.clothLines = clothLines;
    }
}
