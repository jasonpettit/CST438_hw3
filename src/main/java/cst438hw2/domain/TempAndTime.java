package cst438hw2.domain;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneOffset;

public class TempAndTime {
	public double temp;
	public long time;
	public int timezone;
	public double farTemp;
	public String strTime;

	public TempAndTime(double temp, long time, int timezone){
		this.temp = temp;
		this.time = time;
		this.timezone = timezone;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getTimezone() {
		return timezone;
	}

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}

	public double getFarTemp() {
		Double farTemp = (getTemp() - 273.15) * (9.0 / 5.0) + 32;
		DecimalFormat toDecimal = new DecimalFormat("#.##");
		return Double.valueOf(toDecimal.format(farTemp));
	}

	public void setFarTemp(double farTemp) {
		this.farTemp() = farTemp;
	}

	public String getStringTime() {
		long timeOffset = getTime() + getTimezone();
		Instant unixTime = Instant.ofEpochSecond(timeOffset);
		int hour = unixTime.atZone(ZoneOffset.UTC).getHour();
		int minute = unixTime.atZone(ZoneOffset.UTC).getMinute();
		return hour + ":" + minute;
	}

	public void setStrTime (String strTime){
		this.strTime = strTime;
	}
 }
