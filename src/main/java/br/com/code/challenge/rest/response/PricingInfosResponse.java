package br.com.code.challenge.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Setter
@Getter
@JsonInclude(NON_EMPTY)
public class PricingInfosResponse implements Serializable {
    private static final long serialVersionUID = -6539623861679173507L;

    private String yearlyIptu;
    private String price;
    private String businessType;
    private String monthlyCondoFee;
    private String period;
    private String rentalTotalPrice;
}
