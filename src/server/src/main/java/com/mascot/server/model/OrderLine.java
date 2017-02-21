package com.mascot.server.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Nikolay on 07.11.2016.
 */
@Entity
@Table(name = "order_line")
public class OrderLine extends Identified {

    @ManyToOne(targetEntity = Order.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(targetEntity = Cloth.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "main_cloth_id")
    private Cloth mainCloth;

    @ManyToOne(targetEntity = Cloth.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "comp_1_cloth_id")
    private Cloth compCloth1;

    @ManyToOne(targetEntity = Cloth.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "comp_2_cloth_id")
    private Cloth compCloth2;

    @Column(name = "stitching")
    private StitchingType stitchingType;

    @Column(name = "count")
    private Integer count;

    @Column(name = "cost")
    private Double cost;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cloth getMainCloth() {
        return mainCloth;
    }

    public void setMainCloth(Cloth mainCloth) {
        this.mainCloth = mainCloth;
    }

    public Cloth getCompCloth1() {
        return compCloth1;
    }

    public void setCompCloth1(Cloth compCloth1) {
        this.compCloth1 = compCloth1;
    }

    public Cloth getCompCloth2() {
        return compCloth2;
    }

    public void setCompCloth2(Cloth compCloth2) {
        this.compCloth2 = compCloth2;
    }

    public StitchingType getStitchingType() {
        return stitchingType;
    }

    public void setStitchingType(StitchingType stitchingType) {
        this.stitchingType = stitchingType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
