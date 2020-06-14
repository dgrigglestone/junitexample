package org.dg.junitexample;

/**
 * This POJO is required to allow Java deserialization of JSON representations
 * of countries in the body of RESTful requests to the Countries resource.
 */
public class Country {

    public int id;
    public String countryName;
}
