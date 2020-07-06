package cst438hw2.controller;

import cst438hw2.service.WeatherService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import cst438hw2.domain.*;
import cst438hw2.service.CityService;

@RestController
public class CityRestController {

	@Autowired
	CityRepository cityRepository;

	@Autowired
	WeatherService weatherService;
	
	@GetMapping("/city/{name}")
	//public CityInfo getWeather(@PathVariable("city") String cityName) {
	public ResponseEntity<City> cityInfo(@PathVariable("name") String name){

		// look up city info from database.  Might be multiple cities with same name.
		List<City> cities = cityRepository.findByName(name);
		if (cities.size() == 0){
			return new ResponseEntity<City>(HttpStatus.NOT_FOUND); // city name not found. Send 404 code.
		}
		else {
			City city=cities.get(0); // in case of multiple cities, take the first one.
			CityService cityWeather = weatherService.getWeather(name);

			//convert temp from degrees Kelvin to degrees Fahrenheit
			double tempF = Math.round((cityWeather.getTemp() - 273.15) * 9.0/5.0 + 32.0);
			cityWeather.setTemp(tempF);
			city.setWeather(cityWeather);

			//return 200 status code (OK) and city information in JSON format
			return new ResponseEntity<City>(city, HttpStatus.OK);
		}
	}

	@DeleteMapping
	public ResponseEntity<City> deleteCity(@PathVariable("name") String name){
		List<City> cities = cityRepository.findByName(name);
		if (cities.size() == 0){
			return new ResponseEntity<City>(HttpStatus.NOT_FOUND);
		}
		else {
			for (City c : cities){
				cityRepository.delete(c);
			}
			//return 204, request successful. no content returned.
			return new ResponseEntity<City>(HttpStatus.NO_CONTENT);
		}
	}
}
