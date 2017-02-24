package com.mascot.server.model;

import javax.persistence.*;

/**
 * Created by Николай on 24.02.2017.
 */
@Entity
@Table(name = "order_cloth_line")
public class OrderClothLine extends OrderLine {
    @ManyToOne(targetEntity = Cloth.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "cloth_id")
    private Cloth cloth;

    public Cloth getCloth() {
        return cloth;
    }

    public void setCloth(Cloth cloth) {
        this.cloth = cloth;
    }
}
