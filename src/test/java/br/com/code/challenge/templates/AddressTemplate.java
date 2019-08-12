package br.com.code.challenge.templates;

import br.com.code.challenge.rest.response.AddressResponse;
import br.com.code.challenge.rest.response.GeoLocationResponse;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class AddressTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(AddressResponse.class).addTemplate("mockAddress", new Rule(){{
            add("city", null);
            add("neighborhood", null);
            add("geoLocation", one(GeoLocationResponse.class, "mockGeoLocation"));
        }});
    }
}
