package com.education.service;


import com.education.entity.Country;
import com.education.repository.CountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryInfoService implements CountryInfo {

    @Autowired
    private CountryRepo countryRepo;


    @Override
    public void deleteCountryInfo(Long id) {
      countryRepo.deleteById(id);
    }

    @Override
    public List<Country> getAllCountryInfo() {
        return countryRepo.findAll();
    }

    @Override
    public Country saveCountryInfo(Country country) {
        return countryRepo.save(country);
    }

    @Override
    public Long getTotalNumberOfCountry() {
        return countryRepo.count();
    }


    @Override
    public Country getCountryByName(String countryName) {
        return countryRepo.findByCountryName(countryName);
    }
}




