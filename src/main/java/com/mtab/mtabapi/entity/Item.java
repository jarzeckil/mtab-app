package com.mtab.mtabapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ItemCategory category;

    public Item(String name, double price, ItemCategory category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
}
