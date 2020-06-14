package org.dg.junitexample;

import static io.restassured.RestAssured.when;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

/**
 * Tests the countries resource using the REST-assured RESTful services testing toolkit.
 * 
 * Note: In order for REST-assured to deserialize JSON bodies, a JSON deserializer such as
 *       Jackson must be on the classpath.
 */
public class TestCountriesResourceUsingRESTassured extends AbstractTestCountriesResource {
    
    @Override
    public Country[] getAllCountries() {

        Response response = when().get(AbstractTestCountriesResource.COUNTRIES_PATH);
        ResponseBody<?> responseBody = response.getBody();
        
        assertResponseOK(response);
        
        return responseBody.as(Country[].class);
    }

    @Override
    public Country getCountryForValidId(int countryId) {
        
        Response response = when().get(AbstractTestCountriesResource.COUNTRIES_PATH + "/" + countryId);
        ResponseBody<?> responseBody = response.getBody();
        
        assertResponseOK(response);
        
        return responseBody.as(Country.class);
    }

    @Override
    protected void getCountryForUnknownId(int countryId) {
        
        Response response = when().get(AbstractTestCountriesResource.COUNTRIES_PATH + "/" + countryId);
        ResponseBody<?> responseBody = response.getBody();
        
        assertResponseNoContent(response);
    }

    @Override
    protected void getCountryForInvalidId(Object countryId) {
        
        Response response = when().get(AbstractTestCountriesResource.COUNTRIES_PATH + "/" + countryId);
        ResponseBody<?> responseBody = response.getBody();
        
        assertResponseNotFound(response);
    }
}
