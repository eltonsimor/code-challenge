package br.com.code.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PropertyDTO implements Serializable {
    private static final long serialVersionUID = -3376855727070363823L;

    private Integer usableAreas;
    private String listingType;
    private LocalDateTime createdAt;
    private String listingStatus;
    private String id;
    private Integer parkingSpaces;
    private LocalDateTime updatedAt;
    private Boolean owner;
    private List<String> images;
    private Integer bathrooms;
    private Integer bedrooms;
    private AddressDTO address;
    private PricingInfosDTO pricingInfos;
}
