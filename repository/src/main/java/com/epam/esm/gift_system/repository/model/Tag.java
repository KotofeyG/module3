package com.epam.esm.gift_system.repository.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
}