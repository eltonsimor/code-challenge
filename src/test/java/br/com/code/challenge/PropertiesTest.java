package br.com.code.challenge;

import br.com.code.challenge.rest.response.ErrorResponse;
import br.com.code.challenge.rest.response.PropertiesResponse;
import br.com.code.challenge.rest.response.PropertyResponse;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static com.nitorcreations.matchers.ReflectionEqualsMatcher.reflectEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
@ConfigurationProperties("application.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertiesTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeClass
    public static void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.code.challenge.templates");
    }

	@Test
	public void test_get_property_by_id_should_be_a0f9d9647551(){

        ResponseEntity<PropertyResponse> response = restTemplate.getForEntity("/properties/a0f9d9647551", PropertyResponse.class);
        PropertyResponse expected = Fixture.from(PropertyResponse.class).gimme("mockProperty");
        PropertyResponse actual = response.getBody();

        assertEquals(OK, response.getStatusCode());
        assertThat(actual, reflectEquals(expected, "address", "pricingInfos"));
        assertThat(actual.getAddress(), reflectEquals(expected.getAddress(),"geoLocation"));
        assertThat(actual.getAddress().getGeoLocation(), reflectEquals(expected.getAddress().getGeoLocation(), "location"));
        assertThat(actual.getAddress().getGeoLocation().getLocation(), reflectEquals(expected.getAddress().getGeoLocation().getLocation()));
        assertThat(actual.getPricingInfos(), reflectEquals(expected.getPricingInfos()));
    }

    @Test
    public void test_get_property_by_id_should_be_no_content(){
        ResponseEntity<PropertyResponse> response = restTemplate.getForEntity("/properties/idNotExist", PropertyResponse.class);

        assertEquals(NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void test_get_list_not_elegible_lat_lon_should_be_than_zero(){
        ResponseEntity<PropertiesResponse> response = restTemplate.getForEntity("/properties?broker=NOT_ELEGIBLE", PropertiesResponse.class);
        PropertiesResponse actual = response.getBody();
        assertEquals(OK, response.getStatusCode());
        assertTrue(actual.getTotalCount() > 0);
        assertEquals(1, actual.getPageNumber());
        assertEquals(100, actual.getPageSize());
        assertEquals(100, actual.getListings().size());
    }

    @Test
    public void test_get_list_not_elegible_lat_lon_should_be_than_zero_paga_number_less_than_zero(){
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity("/properties?broker=NOT_ELEGIBLE&pageNumber=-1", ErrorResponse.class);
        ErrorResponse error = response.getBody();
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("PageNumber and PageSize don't have Less Than Zero", error.getMessage());
        assertEquals("/code-challenge/properties", error.getPath());
    }

    @Test
    public void test_get_list_passing_broker_invalid(){
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity("/properties?broker=INVALID_BROKER", ErrorResponse.class);
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_get_list_portal_vivareal_should_be_greater_than_zero(){
        ResponseEntity<PropertiesResponse> response = restTemplate.getForEntity("/properties?broker=VIVAREAL", PropertiesResponse.class);
        PropertiesResponse actual = response.getBody();

        assertEquals(OK, response.getStatusCode());
        assertTrue(actual.getTotalCount() > 0);
        assertEquals(1, actual.getPageNumber());
        assertEquals(100, actual.getPageSize());
        assertEquals(100, actual.getListings().size());
    }


    @Test
    public void test_get_list_portal_grupo_should_be_greater_than_zero(){
        ResponseEntity<PropertiesResponse> response = restTemplate.getForEntity("/properties?broker=GRUPOZAP", PropertiesResponse.class);
        PropertiesResponse actual = response.getBody();

        assertEquals(OK, response.getStatusCode());
        assertTrue(actual.getTotalCount() > 0);
        assertEquals(1, actual.getPageNumber());
        assertEquals(100, actual.getPageSize());
        assertEquals(100, actual.getListings().size());
    }

    @Test
    public void test_get_list_passing_page_number_and_page_sizer_greater_than_result(){
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity("/properties?broker=GRUPOZAP&pageNumber=900&pageSize=900", ErrorResponse.class);
        ErrorResponse error = response.getBody();
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("The pageNumber and pageSize are greater that result", error.getMessage());
        assertEquals("/code-challenge/properties", error.getPath());
    }

}
