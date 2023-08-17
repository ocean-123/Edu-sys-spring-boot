package com.education.service;

import com.education.entity.Country;

import java.util.List;

public interface CountryInfo {


    void deleteCountryInfo(Long id);
 List<Country> getAllCountryInfo();
    Country saveCountryInfo(Country country);

    Long getTotalNumberOfCountry();


}
