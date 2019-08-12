package br.com.code.challenge.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Setter
@Getter
@JsonInclude(NON_EMPTY)
public class AddressResponse implements Serializable {
    private static final long serialVersionUID = -669232696634960986L;

    private String city;
    private String neighborhood;
    private GeoLocationResponse geoLocation;
}
