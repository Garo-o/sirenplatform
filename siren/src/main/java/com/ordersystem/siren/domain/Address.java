package com.ordersystem.siren.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String city;
    private String state;
    private String street;
    private String zipCode;

    public Address(String city, String state, String street, String zipCode){
        this.city=city;
        this.state=state;
        this.street=street;
        this.zipCode=zipCode;
    }

    @Setter
    private Long latitude;
    @Setter
    private Long longitude;
}
