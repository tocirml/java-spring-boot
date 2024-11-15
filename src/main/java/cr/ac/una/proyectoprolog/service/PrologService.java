package cr.ac.una.proyectoprolog.service;

import cr.ac.una.proyectoprolog.model.Task;
import cr.ac.una.proyectoprolog.model.Weather;
import cr.ac.una.proyectoprolog.repository.TaskRepository;
import cr.ac.una.proyectoprolog.repository.WeatherRepository;
import org.jpl7.Query;
import org.jpl7.Term;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PrologService {

    private final TaskRepository taskRepository;
    private final WeatherRepository weatherRepository;

    public PrologService(TaskRepository taskRepository, WeatherRepository weatherRepository) {
        this.taskRepository = taskRepository;
        this.weatherRepository = weatherRepository;

        String prologFile = "consult('task_logic.pl')";
        Query cargarArchivo = new Query(prologFile);

        // Comprobar si el archivo Prolog se carga correctamente
        if (cargarArchivo.hasSolution()) {
            System.out.println("Archivo Prolog cargado correctamente.");
        } else {
            System.out.println("Error al cargar el archivo Prolog.");
            throw new RuntimeException("Error al cargar el archivo Prolog.");
        }
    }

    /**
     * Cargar datos desde las tablas de la base de datos como hechos en Prolog.
     */
    public void loadFactsToProlog() {
        // Limpiar hechos previos en Prolog para evitar duplicados
        Query clearTasks = new Query("retractall(task(_, _, _, _, _, _, _, _)).");
        clearTasks.hasSolution();
        Query clearWeather = new Query("retractall(weather(_, _, _)).");
        clearWeather.hasSolution();

        // Cargar hechos de tareas
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            String dependencies = task.getDependencies() == null || task.getDependencies().isEmpty()
                    ? "[]"
                    : task.getDependencies();

            String fact = String.format(
                    "assertz(task(%d, '%s', '%s', %d, '%s', '%s', '%s', %s)).",
                    task.getId(),
                    task.getName(),
                    task.getStatus(),
                    task.getEstimatedTime(),
                    task.getDate(),
                    task.getDesiredTime(),
                    task.getWeather(),
                    dependencies
            );

            Query query = new Query(fact);
            query.hasSolution();
        }

        // Cargar hechos de clima
        List<Weather> weathers = weatherRepository.findAll();
        for (Weather weather : weathers) {
            String fact = String.format(
                    "assertz(weather('%s', '%s', '%s')).",
                    weather.getDate(),
                    weather.getHour(),
                    weather.getCondition()
            );

            Query query = new Query(fact);
            query.hasSolution();
        }

        System.out.println("Hechos cargados en Prolog.");
    }

    /**
     * Generar el plan optimizado basado en las reglas de Prolog y los datos cargados.
     */
    public List<Map<String, Object>> optimizePlan() {
        // Cargar datos desde la base de datos en Prolog
        loadFactsToProlog();

        Query query = new Query("optimize_plan(Tasks)");

        if (!query.hasSolution()) {
            throw new RuntimeException("No solution found for optimize_plan");
        }

        Term tasksTerm = query.oneSolution().get("Tasks");

        List<Map<String, Object>> formattedResults = new ArrayList<>();

        // Verificar si el término es una lista
        if (tasksTerm.isList()) {
            for (Term task : tasksTerm.listToTermArray()) {
                Term[] taskDetails = task.listToTermArray();

                Map<String, Object> taskData = new HashMap<>();
                taskData.put("id", Integer.parseInt(taskDetails[0].toString()));
                taskData.put("name", taskDetails[1].toString().replace("'", ""));
                taskData.put("status", taskDetails[2].toString().replace("'", ""));
                taskData.put("estimatedTime", Integer.parseInt(taskDetails[3].toString()));
                taskData.put("date", taskDetails[4].toString().replace("'", ""));
                taskData.put("time", taskDetails[5].toString().replace("'", ""));
                taskData.put("weather", taskDetails[6].toString().replace("'", ""));
                formattedResults.add(taskData);
            }
        } else {
            throw new RuntimeException("Returned Tasks is not a proper list");
        }

        return formattedResults;
    }

    /**
     * Método para probar la consulta de optimización directamente.
     */
    public void testOptimizePlan() {
        Query query = new Query("optimize_plan(Tasks)");

        if (!query.hasSolution()) {
            System.out.println("No solution found for optimize_plan.");
        } else {
            Map<String, Term> solution = query.oneSolution();
            System.out.println("Raw Prolog result: " + solution);
        }
    }
}

