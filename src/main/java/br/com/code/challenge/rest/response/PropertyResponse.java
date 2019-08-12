package br.com.code.challenge.rest.response;

import br.com.code.challenge.databind.LocalDateTimeDeserializer;
import br.com.code.challenge.databind.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Getter
@Setter
@JsonInclude(NON_EMPTY)
public class PropertyResponse implements Serializable {
    private static final long serialVersionUID = -3376855727070363823L;

    private Integer usableAreas;
    private String listingType;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    private String listingStatus;
    private String id;
    private Integer parkingSpaces;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    private Boolean owner;
    private List<String> images;
    private Integer bathrooms;
    private Integer bedrooms;

    private AddressResponse address;
    private PricingInfosResponse pricingInfos;
}
