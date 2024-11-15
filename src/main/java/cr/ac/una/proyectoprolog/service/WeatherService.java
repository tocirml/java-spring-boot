package cr.ac.una.proyectoprolog.service;

import cr.ac.una.proyectoprolog.model.Weather;
import cr.ac.una.proyectoprolog.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Weather createWeather(Weather weather) {
        return weatherRepository.save(weather);
    }

    public List<Weather> getWeatherByDate(String date) {
        return weatherRepository.findByDate(date);
    }

    public void deleteWeather(Long id) {
        weatherRepository.deleteById(id);
    }

    public void generateDailyWeather() {
        // Obtener la fecha actual en formato YYYY-MM-DD
        String date = LocalDate.now().toString();

        // Lista de condiciones climáticas disponibles
        List<String> conditions = Arrays.asList("Soleado", "Nublado", "Lluvioso", "Tormentoso");

        for (int hour = 7; hour <= 20; hour++) {
            String condition;
            if (hour >= 18 && hour <= 20) {
                // Clima entre 6:00 PM y 8:00 PM no debe ser "Soleado"
                condition = conditions.get((int) (Math.random() * (conditions.size() - 1)) + 1); // Evita el índice 0
            } else {
                // Clima aleatorio para otras horas
                condition = conditions.get((int) (Math.random() * conditions.size()));
            }

            Weather weather = new Weather();
            weather.setDate(date);
            weather.setHour(String.format("%02d:00", hour));
            weather.setCondition(condition);

            weatherRepository.save(weather);
        }
    }
}
