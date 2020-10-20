package com.manishtraders.model;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name="sales_order")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private int orderNumber;

    @NonNull
    private String partyName;

    private String item;

    private String quantity;

    private String sortBy;

    private String rate;

    private String comments;

    private String date;

    private String createdBy;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private User user;
}
