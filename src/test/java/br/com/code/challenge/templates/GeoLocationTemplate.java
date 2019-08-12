package br.com.code.challenge.templates;

import br.com.code.challenge.rest.response.GeoLocationResponse;
import br.com.code.challenge.rest.response.LocationResponse;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class GeoLocationTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(GeoLocationResponse.class).addTemplate("mockGeoLocation", new Rule(){{
            add("precision","ROOFTOP");
            add("location", one(LocationResponse.class, "mockLocation"));
        }});
    }
}
