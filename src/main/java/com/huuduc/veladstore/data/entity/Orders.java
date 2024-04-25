package com.huuduc.veladstore.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",unique = true,nullable = false)
    private String name;

    @Column(name = "toatl_price")
    private double totalPrice;

    @Temporal(TemporalType.DATE)
    @Column(name = "delivery_time")
    private Date deliveryTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "order_date_time")
    private Date orderDateTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status",nullable = false)
    private Status status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
