package org.dg.junitexample;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Tests the countries resource using the Jersey client API.
 */
public class TestCountriesResourceUsingJerseyClient extends AbstractTestCountriesResource {
    
    /**
     * The tests assume the Countries resource has been deployed to a web container running on
     * the localhost and listening to HTTP requests on port 8080.
     */
    public static final String HOST_SOURCE = "http://localhost:8080";
    
    private Client client;

    
    @Before
    public void initialize() {
        
        client = Client.create();
    }
    
    @After
    public void teardown() {
        
        client.destroy();
    }
    
    @Override
    protected Country[] getAllCountries() throws IOException {

        WebResource webResource = client.resource(HOST_SOURCE + AbstractTestCountriesResource.COUNTRIES_PATH);
        
        ClientResponse response = webResource.get(ClientResponse.class);
        
        assertResponseOK(response);
        
        String output = response.getEntity(String.class);
        return new ObjectMapper().readValue(output, Country[].class);
    }

    @Override
    protected Country getCountryForValidId(int countryId) {
        
        WebResource webResource = client.resource(HOST_SOURCE + AbstractTestCountriesResource.COUNTRIES_PATH + "/" + countryId);
        
        ClientResponse response = webResource.get(ClientResponse.class);
        
        assertResponseOK(response);
        
        String output = response.getEntity(String.class);
        
        try {
            return new ObjectMapper().readValue(output, Country.class);
        } catch (IOException e) {
            fail();
        }
        
        return null;
    }

    @Override
    protected void getCountryForUnknownId(int countryId) {
        
        WebResource webResource = client.resource(HOST_SOURCE + AbstractTestCountriesResource.COUNTRIES_PATH + "/" + countryId);
        
        ClientResponse response = webResource.get(ClientResponse.class);
        
        assertResponseNoContent(response);
    }

    @Override
    protected void getCountryForInvalidId(Object countryId) {
        
        WebResource webResource = client.resource(HOST_SOURCE + AbstractTestCountriesResource.COUNTRIES_PATH + "/" + countryId);
        
        ClientResponse response = webResource.get(ClientResponse.class);
        
        assertResponseNotFound(response);
    }
}
