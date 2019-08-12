package br.com.code.challenge.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Setter
@Getter
@JsonInclude(NON_EMPTY)
public class GeoLocationResponse implements Serializable {
    private static final long serialVersionUID = -3798741072470641147L;

    private String precision;
    private LocationResponse location;
}
