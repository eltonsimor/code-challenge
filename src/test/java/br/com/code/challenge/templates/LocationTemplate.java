package br.com.code.challenge.templates;

import br.com.code.challenge.rest.response.LocationResponse;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class LocationTemplate implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(LocationResponse.class).addTemplate("mockLocation", new Rule(){{
            add("lon", -46.716542);
            add("lat", -23.502555);
        }});
    }
}
