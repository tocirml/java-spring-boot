package cr.ac.una.proyectoprolog.repository;

import cr.ac.una.proyectoprolog.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    List<Weather> findByDate(String date);
}
