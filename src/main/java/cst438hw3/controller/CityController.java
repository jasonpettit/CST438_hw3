package cst438hw3.controller;

import cst438hw3.domain.CityInfo;
import cst438hw3.service.CityService;
import cst438hw3.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CityController {

	@Autowired
	private CityService cityService;

	@Autowired
	private WeatherService weatherService;
	
	@GetMapping("/cities/{city}")
	public String getCityInfo(@PathVariable("city") String cityName, Model model) {
		CityInfo searchedCity = cityService.getCityInfo(cityName);

		if (searchedCity == null) {
			return "city_not_found";
		}
		else {

			model.addAttribute("cityInfo", searchedCity);
			return "city_show";
		}
	}

	@PostMapping("/cities/reservation")
	public String createReservation(
			@RequestParam("city") String cityName,
			@RequestParam("level") String level,
			@RequestParam("email") String email,
			Model model) {

		model.addAttribute("city", cityName);
		model.addAttribute("level", level);
		model.addAttribute("email", email);

		cityService.requestReservation(cityName, email, level);

		return "request_reservation";
	};
}