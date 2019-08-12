package br.com.code.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class AddressDTO implements Serializable {
    private static final long serialVersionUID = -669232696634960986L;

    private String city;
    private String neighborhood;
    private GeoLocationDTO geoLocation;
}
