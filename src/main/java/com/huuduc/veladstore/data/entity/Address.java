package com.huuduc.veladstore.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "phone_receiver", nullable = false)
    private String phoneReceiver;

    @Column(name = "name_receiver", nullable = false)
    private String nameReceiver;

    @Column(name = "defaults", nullable = false)
    private boolean defaults;

    @Column(name = "status", nullable = false)
    private boolean status;

}
