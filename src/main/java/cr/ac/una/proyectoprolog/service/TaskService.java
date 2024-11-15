package cr.ac.una.proyectoprolog.service;

import cr.ac.una.proyectoprolog.model.Task;
import cr.ac.una.proyectoprolog.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getTasksByDate(String date) {
        return taskRepository.findByDate(date);
    }

    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setName(updatedTask.getName());
                    task.setEstimatedTime(updatedTask.getEstimatedTime());
                    task.setDesiredTime(updatedTask.getDesiredTime());
                    task.setDate(updatedTask.getDate());
                    task.setPriority(updatedTask.getPriority());
                    task.setDependencies(updatedTask.getDependencies());
                    task.setWeather(updatedTask.getWeather());
                    task.setStatus(updatedTask.getStatus());
                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
