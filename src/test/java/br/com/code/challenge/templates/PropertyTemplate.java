package br.com.code.challenge.templates;

import br.com.code.challenge.rest.response.AddressResponse;
import br.com.code.challenge.rest.response.PricingInfosResponse;
import br.com.code.challenge.rest.response.PropertyResponse;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.parse;

public class PropertyTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(PropertyResponse.class).addTemplate("mockProperty", new Rule(){{
            add("usableAreas", 69);
            add("listingType", "USED");
            add("createdAt", parse("2016-11-16T04:14:02Z",  DateTimeFormatter.ISO_DATE_TIME));
            add("listingStatus", "ACTIVE");
            add("id", "a0f9d9647551");
            add("parkingSpaces", 1);
            add("updatedAt", parse("2016-11-16T04:14:02Z", DateTimeFormatter.ISO_DATE_TIME));
            add("owner", false);
            add("images", getImages());
            add("address", one(AddressResponse.class, "mockAddress"));
            add("bathrooms", 2);
            add("bedrooms", 3);
            add("pricingInfos", one(PricingInfosResponse.class, "mockPricingInfos"));
        }});
    }

    private List<String> getImages(){
        List<String> images = new ArrayList<>();

        images.add("https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/285805119ab0761500127aebd8ab0e1d.jpg");
        images.add("https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/4af1656b66b9e12efff6ce06f51926f6.jpg");
        images.add("https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/895f0d4ce1e641fd5c3aad48eff83ac8.jpg");
        images.add("https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/e7b5cce2d9aee78867328dfa0a7ba4c6.jpg");
        images.add("https://resizedimgs.vivareal.com/crop/400x300/vr.images.sp/d833da4cdf6b25b7acf3ae0710d3286d.jpg");

        return images;
    }
}
