import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AccuWeatherProvider implements WeatherProvider {
    private static final String BASE_HOST = "dataservice.accuweather.com";
    private static final String FORECAST = "forecasts";
    private static final String API_VERSION = "v1";
    private static final String CURRENT_CONDITION_ENDPOINT = "currentconditions";
    private static final String FORECAST_PERIOD = "5day";
    private static final String API_KEY = ApplicationGlobalState.getInstance().getAPI_KEY();

    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void getWeather(Periods periods) throws IOException {
        String cityKey = detectCityKey();
        if (periods.equals(Periods.NOW)) {
            HttpUrl url = new HttpUrl.Builder()
                    .scheme("http")
                    .host(BASE_HOST)
                    .addPathSegment(CURRENT_CONDITION_ENDPOINT)
                    .addPathSegment(API_VERSION)
                    .addPathSegment(cityKey)
                    .addQueryParameter("apikey",API_KEY)
                    .build();

            Request request = new Request.Builder()
                    .addHeader("accept", "application/json")
                    .url(url)
                    .build();
            Response responce = client.newCall(request).execute();
            System.out.println(responce.body().string());

        }
    }
    public String detectCityKey() throws IOException {
        String selectedCity = ApplicationGlobalState.getInstance().getSelectedCity();

        HttpUrl detectedLocationURL = new HttpUrl.Builder()
                .scheme("http")
                .host(BASE_HOST)
                .addPathSegment("location")
                .addPathSegment(API_VERSION)
                .addPathSegment("cities")
                .addPathSegment("autocomplete")
                .addQueryParameter("apiykey", API_KEY)
                .addQueryParameter("q", selectedCity)
                .build();

        Request request = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(detectedLocationURL)
                .build();
        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()){
            throw new IOException("невозможно прочесть иформацию о городе");
            }
        String jsonResponce = response.body().string();
        System.out.println("Произвожу поиск города");

        if (objectMapper.readTree(jsonResponce).size()>0){
            String cityName = objectMapper.readTree(jsonResponce).get(0).at("/LocalizateName").asText();
            String countryName = objectMapper.readTree(jsonResponce).get(0).at("/Country/LocalizateName").asText();
            System.out.println("Найден город " +cityName+ " в стране "+countryName);
        } else throw new IOException("Server find 0 city");

        return objectMapper.readTree(jsonResponce).get(0).at("/Key").asText();
    }
}
