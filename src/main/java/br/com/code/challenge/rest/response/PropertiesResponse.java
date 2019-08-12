package br.com.code.challenge.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Setter
@Getter
@JsonInclude(NON_EMPTY)
public class PropertiesResponse implements Serializable {

    private int pageNumber;
    private int pageSize;
    private int totalCount;
    private List<PropertyResponse> listings;
}
