package cr.ac.una.proyectoprolog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer estimatedTime;

    private String desiredTime;

    private String date; // Fecha de la tarea en formato YYYY-MM-DD.

    private String weather;

    private String priority;

    @Column(name = "dependencies")
    private String dependencies;

    private String status;
}
