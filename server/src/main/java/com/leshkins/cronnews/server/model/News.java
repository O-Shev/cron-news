package com.leshkins.cronnews.server.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String headline;

    @Column
    private String description;

    @Column
    private Timestamp publishedAt;

    @Column
    private Timestamp lastUpdateAt;

    @Column
    private String mediaURL;
}
