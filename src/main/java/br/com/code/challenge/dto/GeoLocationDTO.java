package br.com.code.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class GeoLocationDTO implements Serializable {
    private static final long serialVersionUID = -3798741072470641147L;

    private String precision;
    private LocationDTO location;
}
