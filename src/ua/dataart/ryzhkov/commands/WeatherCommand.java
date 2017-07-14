package ua.dataart.ryzhkov.commands;


import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import tk.plogitech.darksky.api.jackson.DarkSkyJacksonClient;
import tk.plogitech.darksky.forecast.*;
import tk.plogitech.darksky.forecast.model.Forecast;
import ua.dataart.ryzhkov.BuildVars;


import java.io.IOException;

public class WeatherCommand implements Command {

	public static String currentWeather(String location)
			throws InterruptedException, ApiException, IOException, ForecastException {
		GeoApiContext context = new GeoApiContext()
				.setApiKey(BuildVars.gooleMapsApiKey);
		GeocodingResult[] results = GeocodingApi.geocode(context, location).await();

		Longitude longitude = new Longitude(results[0].geometry.location.lng);
		Latitude latitude = new Latitude(results[0].geometry.location.lat);
		ForecastRequest forecastRequest = new ForecastRequestBuilder()
				.key(new APIKey(BuildVars.darkSkyApiKey))
				.language(ForecastRequestBuilder.Language.en)
				.location(new GeoCoordinates(longitude, latitude)).build();
		DarkSkyJacksonClient jacksonClient = new DarkSkyJacksonClient();
		Forecast forecast = jacksonClient.forecast(forecastRequest);


		return  results[0].geometry.location.toString() + " Current temprature: "  + forecast.getCurrently().getTemperature()
				+" Cloud cover: " + forecast.getCurrently().getCloudCover()+ " " +
				forecast.getCurrently().getSummary() + " Humidity: " +
				forecast.getCurrently().getHumidity() + " Wind speed: " +
				forecast.getCurrently().getWindSpeed()
				+ " Week forecast: " + forecast.getDaily().getSummary();
	}
}
