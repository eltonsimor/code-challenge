package br.com.code.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class LocationDTO implements Serializable {
    private static final long serialVersionUID = 357033965584606955L;

    private Double lon;
    private Double lat;
}
