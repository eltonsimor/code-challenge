package br.com.code.challenge.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class PricingInfosDTO implements Serializable {
    private static final long serialVersionUID = -6539623861679173507L;

    private String yearlyIptu;
    private String price;
    private String businessType;
    private String monthlyCondoFee;
    private String period;
    private String rentalTotalPrice;
}
