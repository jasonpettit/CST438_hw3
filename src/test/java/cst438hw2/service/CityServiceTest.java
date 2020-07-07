package cst438hw2.service;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import cst438hw2.domain.*;
 
@SpringBootTest
public class CityServiceTest {

	@MockBean
	private WeatherService weatherService;
	
	@Autowired
	private CityService cityService;
	
	@MockBean
	private CityRepository cityRepository;
	
	@MockBean
	private CountryRepository countryRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testCityFound() throws Exception {
		// test definition of country, city, and cities list
		Country country = new Country("TST", "TestCountry");
		City city = new City(1, "TestCity", "TST", "DistrictTest", 100000);
		List<City> cities = new ArrayList<City>();
		cities.add(city);

		// Test WeatherService
		// create the stub calls and return data for weatherService
		// when the getTempAndTime is called with the name parameter "TestCity",
		// the stub will return the given temp and time
		given(weatherService.getTempAndTime("TestCity"))
				.willReturn(new TempAndTime(123.45, 1234560000, -800));

		// Test CityRepository database
		// create the stub calls and return data for cityRepository
		// when the findByName is called with the name parameter "TestCity"
		// the stub will return a list of cities
		given(cityRepository.findByName("TestCity")).willReturn(cities);

		// Test CountryRepository database
		// create the stub calls and return data for countryRepository
		// when the findByCode is called the name parameter "TST"
		// the stub will return the TestCountry associated with country code TST
		given(countryRepository.findByCode("TST")).willReturn(country);

		// perform the tests
		CityInfo cityResult = cityService.getCityInfo("TestCity");
		CityInfo expectedResult = new CityInfo(1, "TestCity", "TST", "TestCountry", "DistrictTest",
				100000, 76.0, "13:16");

		// assertions
		assertThat(cityResult).isEqualTo(expectedResult);
	}
	
	@Test 
	public void  testCityNotFound() {
		// test definition of country, city, and cities list
		Country country = new Country("TST", "TestCountry");
		City city = new City(1, "TestCity", "TST", "DistrictTest", 100000);
		List<City> cities = new ArrayList<City>();
		cities.add(city);

		// Test WeatherService
		// create the stub calls and return data for weatherService
		// when the getTempAndTime is called with the name parameter "TestCity",
		// the stub will return the given temp and time
		given(weatherService.getTempAndTime("TestCity"))
				.willReturn(new TempAndTime(123.45, 1234560000, -800));

		// Test CityRepository database
		// create the stub calls and return data for cityRepository
		// when the findByName is called with the name parameter "TestCity"
		// the stub will return a list of cities
		given(cityRepository.findByName("TestCity")).willReturn(cities);

		// Test CountryRepository database
		// create the stub calls and return data for countryRepository
		// when the findByCode is called the name parameter "TST"
		// the stub will return the TestCountry associated with country code TST
		given(countryRepository.findByCode("TST")).willReturn(country);

		// perform the tests
		CityInfo cityResult = cityService.getCityInfo("BadTest");
		CityInfo expectedResult = null;

		// assertions
		assertThat(cityResult).isEqualTo(expectedResult);
	}
	
	@Test 
	public void  testCityMultiple() {
		// test definition of multiple countries, cities, and cities lists
		Country country0 = new Country("TST0", "TestCountry0");
		Country country1 = new Country("TST1", "TestCountry1");
		Country country2 = new Country("TST2", "TestCountry2");

		City city0 = new City(1, "TestCity", "TST0", "DistrictTest0", 100000);
		City city1 = new City(1, "TestCity", "TST1", "DistrictTest1", 111111);
		City city2 = new City(1, "TestCity", "TST2", "DistrictTest2", 222222);

		List<City> cities = new ArrayList<City>();
		cities.add(city0);
		cities.add(city1);
		cities.add(city2);

		// Test WeatherService
		// create the stub calls and return data for weatherService
		// when the getTempAndTime is called with the name parameter "TestCity",
		// the stub will return the given temp and time
		given(weatherService.getTempAndTime("TestCity"))
				.willReturn(new TempAndTime(123.45, 1234560000, -800));

		// Test CityRepository database
		// create the stub calls and return data for cityRepository
		// when the findByName is called with the name parameter "TestCity"
		// the stub will return a list of cities
		given(cityRepository.findByName("TestCity")).willReturn(cities);

		// Test CountryRepository database
		// create the stub calls and return data for countryRepository
		// when the findByCode is called the name parameter "TST"
		// the stub will return the TestCountry associated with country code TST
		given(countryRepository.findByCode("TST0")).willReturn(country0);
		given(countryRepository.findByCode("TST1")).willReturn(country1);
		given(countryRepository.findByCode("TST2")).willReturn(country2);

		// perform the tests
		CityInfo cityResult = cityService.getCityInfo("TestCity");
		CityInfo expectedResult = new CityInfo(1, "TestCity", "TST", "TestCountry", "DistrictTest",
				100000, 76, "13:16");

		// assertions
		assertThat(cityResult).isEqualTo(expectedResult);
	}
}
