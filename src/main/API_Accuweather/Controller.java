import java.io.IOException;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    WeatherProvider weatherProvider = new WeatherProvider() {
        @Override
        public void getWeather(Periods period) throws IOException {

        }
    };

    Map<Integer, Functionality> variantResult = new HashMap();

    public Controller(){
        variantResult.put(1,Functionality.GET_CURRENT_WEATHER);
        variantResult.put(2,Functionality.GET_WEATHER_NEXT_5_DAYS);
    }
    public void onUserInput(String input) throws IOException {
        int command = Integer.parseInt(input);
        if (!variantResult.containsKey(command)) {
            throw new IOException("There is not command key");
        }
        switch (variantResult.get(command)) {
            case GET_CURRENT_WEATHER:
                getCurrentWeather();
            break;
            case GET_WEATHER_NEXT_5_DAYS:
                get_Weather_Next_5_Days();
            break;
        }
    }
    public void getCurrentWeather() throws IOException {
        weatherProvider.getWeather(Periods.NOW);

    }

    public void get_Weather_Next_5_Days() throws RuntimeException, IOException {
        weatherProvider.getWeather(Periods.DAYS_5);
    }


}
