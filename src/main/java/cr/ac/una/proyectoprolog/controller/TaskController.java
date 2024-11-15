package cr.ac.una.proyectoprolog.controller;

import cr.ac.una.proyectoprolog.model.Task;
import cr.ac.una.proyectoprolog.service.PrologService;
import cr.ac.una.proyectoprolog.service.TaskService;
import org.jpl7.Query;
import org.jpl7.Term;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    private final PrologService prologService;

    public TaskController(TaskService taskService, PrologService prologService) {
        this.taskService = taskService;
        this.prologService = prologService;
    }

    @PostMapping("/createTask")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @GetMapping("/getTaskByDate")
    public ResponseEntity<List<Task>> getTasksByDate(@RequestParam String date) {
        return ResponseEntity.ok(taskService.getTasksByDate(date));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return ResponseEntity.ok(taskService.updateTask(id, updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/optimize")
    public ResponseEntity<List<Map<String, Object>>> optimizePlan() {
        List<Map<String, Object>> results = prologService.optimizePlan();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/test-optimize")
    public ResponseEntity<String> testOptimizePlan() {
        Query query = new Query("optimize_plan(Tasks)");

        if (!query.hasSolution()) {
            return ResponseEntity.ok("No solution found for optimize_plan.");
        } else {
            Map<String, Term> solution = query.oneSolution();
            return ResponseEntity.ok("Raw Prolog result: " + solution);
        }
    }


}
