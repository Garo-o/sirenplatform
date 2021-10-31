package com.ordersystem.siren.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MenuImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menuImageId")
    private Long id;

    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false, unique = true)
    private String filePath;
    @Column(nullable = false)
    private Long fileSize;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "image")
    private Menu menu;

}
