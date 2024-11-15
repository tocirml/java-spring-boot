package cr.ac.una.proyectoprolog.repository;

import cr.ac.una.proyectoprolog.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDate(String date);
}