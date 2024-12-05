package com.example.lr4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "market", schema = "market_lr4")
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmarket", nullable = false)
    private Integer id;

    @Column(name = "name", length = 45)
    private String name;

}