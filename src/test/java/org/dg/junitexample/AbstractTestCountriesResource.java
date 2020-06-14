package org.dg.junitexample;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;

import io.restassured.response.Response;

public abstract class AbstractTestCountriesResource {

    public static final String COUNTRIES_PATH = "/JAXRSJsonExample/rest/countries";
    public static final int OK_STATUS_CODE = javax.ws.rs.core.Response.Status.OK.getStatusCode();
    public static final int NO_CONTENT_STATUS_CODE = javax.ws.rs.core.Response.Status.NO_CONTENT.getStatusCode();
    public static final int NOT_FOUND_STATUS_CODE = javax.ws.rs.core.Response.Status.NOT_FOUND.getStatusCode();

    //
    // Each of the test methods calls an abstract method that makes a RESTful call on the countries resource.
    //
    // Test classes that extend this abstract class, *override* those abstract methods to provide a concrete
    // implementation for a specific REST client (e.g. REST-assured, Jersey client).
    //
    
    /**
     * Test getting all countries from the resource.
     * 
     * @throws IOException
     */
    @Test
    public void testGetAllCountries() throws IOException {
        
        Country[] countries = getAllCountries();
        
        assertEquals(countries.length, 4);
    }
    
    /**
     * Test getting a country using a known country id (known ids are 1,2,3 & 4)
     */
    @Test
    public void testGetCountryUsingKnownId() {
        
        Country country = getCountryForValidId(3);
        
        validateCountry(country, 3, "Nepal");
    }
    
    /**
     * Test getting a country using an unknown country id (known ids are 1,2,3 & 4)
     */
    @Test
    public void testGetCountryUsingUnknownId() {
        
        getCountryForUnknownId(999);
    }
    
    /**
     * Test getting a country using an invalid country id (i.e. non positive integers)
     */
    @Test
    public void testGetCountryUsingInvalidIds() {
        
        getCountryForInvalidId(-1);
        getCountryForInvalidId("InvalidId");
    }

    private void validateCountry(Country country, int expectedCountryId, String expectedcountryName) {
        
        assertEquals(country.id, expectedCountryId);
        assertEquals(country.countryName, expectedcountryName);
    }
    
    //
    // The *overloaded* methods below provide assertion of specific HTTP response codes for either a
    // JAX-RS Jersey response or an REST-assured response.
    //
    
    protected void assertResponseOK(ClientResponse response) {
        
        int status = response.getStatus();
        
        assertEquals(status, OK_STATUS_CODE);
    }
    
    protected void assertResponseOK(Response response) {
        
        int status = response.getStatusCode();
        
        assertEquals(status, OK_STATUS_CODE);
    }

    protected void assertResponseNoContent(ClientResponse response) {
        
        int status = response.getStatus();
        
        assertEquals(status, NO_CONTENT_STATUS_CODE);
    }
    
    protected void assertResponseNoContent(Response response) {
        
        int status = response.getStatusCode();
        
        assertEquals(status, NO_CONTENT_STATUS_CODE);
    }

    protected void assertResponseNotFound(ClientResponse response) {
        
        int status = response.getStatus();
        
        assertEquals(status, NOT_FOUND_STATUS_CODE);
    }
    
    protected void assertResponseNotFound(Response response) {
        
        int status = response.getStatusCode();
        
        assertEquals(status, NOT_FOUND_STATUS_CODE);
    }

    protected abstract Country[] getAllCountries() throws IOException;
    protected abstract Country getCountryForValidId(int i);
    protected abstract void getCountryForUnknownId(int i);
    protected abstract void getCountryForInvalidId(Object id);
}
