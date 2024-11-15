package cr.ac.una.proyectoprolog.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "weather")
@Data
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date; // Fecha del clima en formato YYYY-MM-DD.

    private String hour; // Hora específica (e.g., "07:00").

    private String condition; // Condición climática (e.g., "Soleado", "Lluvioso").
}
