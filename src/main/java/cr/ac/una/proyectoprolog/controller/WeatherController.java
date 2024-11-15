package cr.ac.una.proyectoprolog.controller;

import cr.ac.una.proyectoprolog.model.Weather;
import cr.ac.una.proyectoprolog.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping
    public ResponseEntity<Weather> createWeather(@RequestBody Weather weather) {
        return ResponseEntity.ok(weatherService.createWeather(weather));
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<Weather>> getWeatherByDate(@PathVariable String date) {
        return ResponseEntity.ok(weatherService.getWeatherByDate(date));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeather(@PathVariable Long id) {
        weatherService.deleteWeather(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/generate")
    public ResponseEntity<Void> generateDailyWeather() {
        weatherService.generateDailyWeather();
        return ResponseEntity.noContent().build();
    }

}