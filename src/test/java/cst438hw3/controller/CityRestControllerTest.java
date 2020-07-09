package cst438hw3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import cst438hw3.domain.*;
import cst438hw3.service.CityService;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(CityRestController.class)
public class CityRestControllerTest {

	@MockBean
	private CityService cityService;

	@Autowired
	private MockMvc mvc;

	// This object will be magically initialized by the initFields method below.
	private JacksonTester<CityInfo> json;

	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void getCityInfo() throws Exception {
		//test definition of city
		City city = new City(1, "TestCity", "TST", "DistrictTest", 100000);

		// Test CityService
		// create the stub calls and return data for cityService
		// when the getCityInfo is called with the name parameter "TestCity",
		// the stub will return a CityInfo object
		given(cityService.getCityInfo("TestCity"))
				.willReturn(new CityInfo(city, "TestCountry", 76.0, "13:16"));

		// perform the test
		MockHttpServletResponse response = mvc.perform(get("/api/cities/TestCity"))
				.andReturn().getResponse();

		// convert JSON to CityInfo
		CityInfo cityResult = json.parseObject(response.getContentAsString());
		CityInfo expectedResult = new CityInfo(1, "TestCity", "TST", "TestCountry",
				"DistrictTest", 100000, 76.0, "13:16");

		// assertions
		assertThat(cityResult).isEqualTo(expectedResult);
	}

	@Test
	public void testCityNotFound() throws Exception {
		//test definition of city
		City city = new City(1, "TestCity", "TST", "DistrictTest", 100000);

		// Test CityService
		// create the stub calls and return data for cityService
		// when the getCityInfo is called with the name parameter "TestCity",
		// the stub will return a CityInfo object
		given(cityService.getCityInfo("TestCity"))
				.willReturn(new CityInfo(city, "TestCountry", 76.0, "13:16"));

		// perform the test
		MockHttpServletResponse response = mvc.perform(get("/api/cities/BadTest"))
				.andReturn().getResponse();

		//assertions
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void testCityMultiple() throws Exception {
		//test definition of city
		City city0 = new City(1, "TestCity", "TST0", "DistrictTest0", 100000);
		City city1 = new City(1, "TestCity", "TST1", "DistrictTest1", 111111);
		City city2 = new City(1, "TestCity", "TST2", "DistrictTest2", 222222);

		List<City> cities = new ArrayList<City>();
		cities.add(city0);
		cities.add(city1);
		cities.add(city2);

		// Test CityService
		// create the stub calls and return data for cityService
		// when the getCityInfo is called with the name parameter "TestCity",
		// the stub will return a CityInfo object
		given(cityService.getCityInfo("TestCity"))
				.willReturn(new CityInfo(city0, "TestCountry0", 76.0, "13:16"));
		given(cityService.getCityInfo("TestCity"))
				.willReturn(new CityInfo(city1, "TestCountry1", 76.0, "13:16"));
		given(cityService.getCityInfo("TestCity"))
				.willReturn(new CityInfo(city2, "TestCountry2", 76.0, "13:16"));

		// perform the test
		MockHttpServletResponse response = mvc.perform(get("/api/cities/TestCity"))
				.andReturn().getResponse();

		// convert JSON to CityInfo
		CityInfo cityResult = json.parseObject(response.getContentAsString());
		CityInfo expectedResult = new CityInfo(1, "TestCity", "TST", "TestCountry",
				"DistrictTest", 100000, 76.0, "13:16");

		// assertions
		assertThat(cityResult).isEqualTo(expectedResult);;
	}

}
