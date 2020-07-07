package cst438hw2.service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cst438hw2.domain.*;

@Service
public class CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private WeatherService weatherService;
	
	public CityInfo getCityInfo(String cityName) {
		List<City> city = cityRepository.findByName(cityName);

		if (city.size() == 0){
			return null;
		}

		City tempCity = city.get(0);
		Country country = countryRepository.findByCode(tempCity.getCountryCode());
		TempAndTime weather = weatherService.getTempAndTime(cityName);

		// convert temp from Kelvin to degrees Fahrenheit
		double tempF = Math.round((weather.getTemp() - 273.15) * 9.0/5.0 + 32.0);
		weather.setTemp(tempF);

		// convert epoch time to regular time
		long timeOffset = weather.getTime() + weather.getTimezone();
		Instant unixTime = Instant.ofEpochSecond(timeOffset);
		int hour = unixTime.atZone(ZoneOffset.UTC).getHour();
		int minute = unixTime.atZone(ZoneOffset.UTC).getMinute();
		String time = (String.format("%d:%d", hour, minute));

		return new CityInfo(tempCity, country.getName(), weather.getTemp(), time);
	}
	
}
