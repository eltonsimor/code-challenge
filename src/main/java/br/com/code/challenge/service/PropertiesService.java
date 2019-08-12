package br.com.code.challenge.service;

import br.com.code.challenge.dto.PropertyDTO;
import br.com.code.challenge.enums.EnumBroker;
import br.com.code.challenge.exceptions.CodeChallengeException;
import br.com.code.challenge.rest.response.PropertiesResponse;
import br.com.code.challenge.rest.response.PropertyResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.code.challenge.enums.EnumBroker.GRUPOZAP;
import static br.com.code.challenge.enums.EnumBroker.NOT_ELEGIBLE;
import static br.com.code.challenge.enums.EnumBroker.VIVAREAL;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@Service
public class PropertiesService implements Serializable {
    private static final long serialVersionUID = -638991252121303434L;

    private static final String RENTAL = "RENTAL";
    private static final String SALE = "SALE";

    @Value("${grupo-zap.settings.boundaries.lon.min}")
    private double lonMin;
    @Value("${grupo-zap.settings.boundaries.lon.max}")
    private double lonMax;
    @Value("${grupo-zap.settings.boundaries.lat.min}")
    private double latMin;
    @Value("${grupo-zap.settings.boundaries.lat.max}")
    private double latMax;

    @Value("${grupo-zap.settings.portal-zap.rental.min}")
    private Integer portalZapRentMin;
    @Value("${grupo-zap.settings.portal-zap.sale.square-meters-value-min}")
    private Integer portalZapSquereMetersMin;
    @Value("${grupo-zap.settings.portal-zap.sale.min}")
    private Integer portalZapSalesMin;
    @Value("${grupo-zap.settings.portal-zap.sale.in-bounding-box.percent}")
    private Integer portalZapInBoundingBoxPercent;

    @Value("${grupo-zap.settings.portal-vivareal.rental.max}")
    private Integer portalVivaRealRentMax;
    @Value("${grupo-zap.settings.portal-vivareal.rental.condo-free.percent}")
    private Integer portalVivaRealCondoFreePercent;
    @Value("${grupo-zap.settings.portal-vivareal.rental.in-bounding-box.percent}")
    private Integer portalVivaRealInBoundingBoxPercent;
    @Value("${grupo-zap.settings.portal-vivareal.sale.max}")
    private Integer portalVivaRealSaleMax;

    private Optional<List<PropertyDTO>> properties;

    private List<PropertyResponse> portalVivaRealProperties;
    private List<PropertyResponse> portalGrupoZapProperties;
    private List<PropertyResponse> notElegibleProperties;

    @PostConstruct
    public void init() {
        this.portalVivaRealProperties = new ArrayList<>();
        this.properties = Optional.of(new ArrayList<>());
        this.notElegibleProperties = new ArrayList<>();
        this.portalGrupoZapProperties = new ArrayList<>();
    }

    public void addProperty(PropertyDTO propertyDTO){
        properties.get().add(propertyDTO);
    }

    public PropertyResponse getPropertyById(final String id) throws CodeChallengeException {
        log.info("Searching property by Id: {}", id);

        PropertyDTO dto = properties.map(list -> list.stream().filter(p -> p.getId().equalsIgnoreCase(id))).get().findFirst().orElseThrow(() -> {
            log.error("Not Found Property with this Id: {}", id);
            return new CodeChallengeException("Not Found Property", NO_CONTENT);
        });

        return new ModelMapper().map(dto, PropertyResponse.class);
    }

    public PropertiesResponse getProperties(final EnumBroker broker, final Integer pageNumber, final Integer pageSize) throws CodeChallengeException {
        log.info("Searching properties for Broker: {}", broker);

        if(pageNumber <= 0 || pageSize <= 0){
            log.error("PageNumber and PageSize don't have Less Than Zero");
            throw new CodeChallengeException("PageNumber and PageSize don't have Less Than Zero", BAD_REQUEST);
        }

        switch (broker){
            case NOT_ELEGIBLE:
                return getPropertiesNotElegible(pageNumber, pageSize);
            case VIVAREAL:
                return getPropertiesPortalVivaReal(pageNumber, pageSize);
            case GRUPOZAP:
                return getPropertiesPortalGrupoZap(pageNumber, pageSize);
            default:
                return null;
        }
    }

    private PropertiesResponse getPropertiesNotElegible(final Integer pageNumber, final Integer pageSize) throws CodeChallengeException {
        log.info("Searching Properties {}", NOT_ELEGIBLE);

        if(notElegibleProperties.isEmpty()){
            properties.map(list -> list.stream().filter(p ->
                    p.getAddress().getGeoLocation().getLocation().getLat() == 0 && p.getAddress().getGeoLocation().getLocation().getLon() == 0
            )).get().forEach(p -> {
                PropertyResponse property = new ModelMapper().map(p, PropertyResponse.class);
                notElegibleProperties.add(property);
            });
        }

        return build(pageNumber, pageSize, notElegibleProperties);
    }

    private PropertiesResponse getPropertiesPortalVivaReal(final Integer pageNumber, final Integer pageSize) throws CodeChallengeException {
        log.info("Searching Properties {}", VIVAREAL);

        if(portalVivaRealProperties.isEmpty()){
            List<PropertyResponse> rentalProperties = getRentalPropertiesPortalVivaReal();
            List<PropertyResponse> saleProperties = getSalePropertiesPortalVivaReal();

            portalVivaRealProperties.addAll(rentalProperties);
            portalVivaRealProperties.addAll(saleProperties);
        }

        return build(pageNumber, pageSize, portalVivaRealProperties);
    }

    private List<PropertyResponse> getRentalPropertiesPortalVivaReal(){
        log.info("Mapping Rental Properties {}", VIVAREAL);
        List<PropertyResponse> listings = new ArrayList<>();

        List<PropertyDTO> rentalDtos = properties.map(list -> list.stream().filter(p ->
                p.getPricingInfos().getBusinessType().equalsIgnoreCase(RENTAL) &&
                p.getAddress().getGeoLocation().getLocation().getLat() != 0 &&
                p.getAddress().getGeoLocation().getLocation().getLon() != 0 &&
                p.getPricingInfos().getRentalTotalPrice() != null && p.getPricingInfos().getMonthlyCondoFee() != null &&
                Integer.valueOf(p.getPricingInfos().getRentalTotalPrice()) <= portalVivaRealRentMax &&
                Integer.valueOf(p.getPricingInfos().getMonthlyCondoFee()) <= ((Integer.valueOf(p.getPricingInfos().getRentalTotalPrice()) * portalVivaRealCondoFreePercent) / 100)
        )).get().collect(Collectors.toList());

        rentalDtos.forEach(p -> {
            double lat = p.getAddress().getGeoLocation().getLocation().getLat();
            double lon = p.getAddress().getGeoLocation().getLocation().getLon();
            boolean isInBounding = checkIsInBoundingBoxGrupoZap(lon, lat);
            PropertyResponse property = new ModelMapper().map(p, PropertyResponse.class);
            if(isInBounding){
                Integer monthlyCondoFree = Integer.valueOf(property.getPricingInfos().getMonthlyCondoFee());
                Integer newMonthlyCondoFree = monthlyCondoFree + ((monthlyCondoFree * portalVivaRealInBoundingBoxPercent) / 100);
                property.getPricingInfos().setMonthlyCondoFee(newMonthlyCondoFree.toString());
            }
            listings.add(property);
        });

        return listings;
    }

    private List<PropertyResponse> getSalePropertiesPortalVivaReal(){
        log.info("Mapping Sales Properties {}", VIVAREAL);
        List<PropertyResponse> listings = new ArrayList<>();

        List<PropertyDTO> saleDtos = properties.map(list -> list.stream().filter(p ->
                p.getPricingInfos().getPrice() != null &&
                p.getPricingInfos().getBusinessType().equalsIgnoreCase(SALE) &&
                Integer.valueOf(p.getPricingInfos().getPrice()) <= portalVivaRealSaleMax &&
                p.getAddress().getGeoLocation().getLocation().getLat() != 0 &&
                p.getAddress().getGeoLocation().getLocation().getLon() != 0
        )).get().collect(Collectors.toList());

        saleDtos.forEach(p -> {
            PropertyResponse property = new ModelMapper().map(p, PropertyResponse.class);
            listings.add(property);
        });

        return listings;
    }

    private PropertiesResponse getPropertiesPortalGrupoZap(final Integer pageNumber, final Integer pageSize) throws CodeChallengeException {
        log.info("Searching Properties {}", GRUPOZAP);

        if(portalGrupoZapProperties.isEmpty()){
            List<PropertyResponse> rentalProperties = getRentalPropertiesPortalGrupoZap();
            List<PropertyResponse> saleProperties = getSalePropertiesPortalGrupoZap();

            portalGrupoZapProperties.addAll(rentalProperties);
            portalGrupoZapProperties.addAll(saleProperties);
        }

        return build(pageNumber, pageSize, portalGrupoZapProperties);
    }

    private List<PropertyResponse> getRentalPropertiesPortalGrupoZap(){
        log.info("Mapping Rental Properties {}", VIVAREAL);
        List<PropertyResponse> listings = new ArrayList<>();

        List<PropertyDTO> rentalDtos = properties.map(list -> list.stream().filter(p ->
                p.getPricingInfos().getBusinessType().equalsIgnoreCase(RENTAL) &&
                p.getAddress().getGeoLocation().getLocation().getLat() != 0 &&
                p.getAddress().getGeoLocation().getLocation().getLon() != 0 &&
                p.getPricingInfos().getRentalTotalPrice() != null &&
                Integer.valueOf(p.getPricingInfos().getRentalTotalPrice()) >= portalZapRentMin
        )).get().collect(Collectors.toList());

        rentalDtos.forEach(p -> {
            PropertyResponse property = new ModelMapper().map(p, PropertyResponse.class);
            listings.add(property);
        });

        return listings;
    }

    private List<PropertyResponse> getSalePropertiesPortalGrupoZap(){
        log.info("Mapping Sales Properties {}", GRUPOZAP);
        List<PropertyResponse> listings = new ArrayList<>();

        List<PropertyDTO> saleDtos = properties.map(list -> list.stream().filter(p ->
                p.getPricingInfos().getPrice() != null &&
                p.getPricingInfos().getBusinessType().equalsIgnoreCase(SALE) &&
                p.getAddress().getGeoLocation().getLocation().getLat() != 0 &&
                p.getAddress().getGeoLocation().getLocation().getLon() != 0 &&
                (
                    (p.getUsableAreas() > 0 && ((Integer.valueOf(p.getPricingInfos().getPrice()) / p.getUsableAreas()) >= portalZapSquereMetersMin)) &&
                    Integer.valueOf(p.getPricingInfos().getPrice()) >= portalZapSalesMin
                )
        )).get().collect(Collectors.toList());

        saleDtos.forEach(p -> {
            double lat = p.getAddress().getGeoLocation().getLocation().getLat();
            double lon = p.getAddress().getGeoLocation().getLocation().getLon();
            boolean isInBounding = checkIsInBoundingBoxGrupoZap(lon, lat);
            PropertyResponse property = new ModelMapper().map(p, PropertyResponse.class);
            if(isInBounding){
                Integer price = Integer.valueOf(property.getPricingInfos().getPrice());
                Integer newPrice = price - ((price * portalZapInBoundingBoxPercent) / 100);
                property.getPricingInfos().setPrice(newPrice.toString());
            }
            listings.add(property);
        });

        return listings;
    }

    private boolean checkIsInBoundingBoxGrupoZap(final double lon, final double lat){
        return (lonMin <= lon && lon <= lonMax
                && latMin <= lat && lat <= latMax);
    }

    private PropertiesResponse build(final Integer pageNumber, final Integer pageSize, final List<PropertyResponse> listings) throws CodeChallengeException {
        log.info("Building PropertiesResponse ...");
        PropertiesResponse response = new PropertiesResponse();
        List<PropertyResponse> listingsPageable = pageableList(pageNumber, pageSize, listings);
        response.setListings(listingsPageable);
        response.setPageNumber(pageNumber);
        response.setPageSize(pageSize);
        response.setTotalCount(listings.size());

        return response;
    }

    private List<PropertyResponse> pageableList(final Integer pageNumber, final Integer pageSize, final List<PropertyResponse> listings) throws CodeChallengeException {
        log.info("Creating pagination...");
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = pageNumber * pageSize;
        try {
            return listings.subList(startIndex, endIndex);
        } catch (IndexOutOfBoundsException e) {
            log.error("The pageNumber and pageSize are greater that result");
            throw new CodeChallengeException("The pageNumber and pageSize are greater that result", BAD_REQUEST);
        }
    }
}
