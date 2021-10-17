package com.ordersystem.siren.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Authority {
    @Id
    private String authorityName;
}
