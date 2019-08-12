package br.com.code.challenge.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Setter
@Getter
@JsonInclude(NON_EMPTY)
public class LocationResponse implements Serializable {
    private static final long serialVersionUID = 357033965584606955L;

    private Double lon;
    private Double lat;
}
