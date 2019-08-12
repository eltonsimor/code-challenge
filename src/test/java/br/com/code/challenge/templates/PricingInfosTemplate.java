package br.com.code.challenge.templates;

import br.com.code.challenge.rest.response.PricingInfosResponse;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class PricingInfosTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(PricingInfosResponse.class).addTemplate("mockPricingInfos", new Rule(){{
            add("yearlyIptu", "0");
            add("price", "405000");
            add("businessType", "SALE");
            add("monthlyCondoFee", "495");
        }});
    }
}
