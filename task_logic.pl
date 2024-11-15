% Declaración de hechos dinámicos
:- dynamic task/8.
:- dynamic weather/3.

% Regla para verificar si una tarea puede ejecutarse
can_execute(TaskID) :-
    task(TaskID, _, 'Pendiente', _, _, _, RequiredWeather, Dependencies),
    check_dependencies(Dependencies),
    check_weather(RequiredWeather).

% Verificar si las dependencias están completas
check_dependencies([]). % Sin dependencias, puede ejecutarse
check_dependencies([H|T]) :-
    task(H, _, 'Completa', _, _, _, _, _), % La tarea dependiente está completa
    check_dependencies(T). % Verificar el resto de las dependencias

% Verificar si el clima coincide
check_weather(''). % Sin requerimientos climáticos, puede ejecutarse
check_weather(RequiredWeather) :-
    weather(_, _, RequiredWeather). % Clima requerido está disponible

% Generar el plan optimizado basado en dependencias y clima
% Generar el plan optimizado basado en dependencias y clima, eliminando duplicados
optimize_plan(Tasks) :-
    setof([ID, Name, Status, EstimatedTime, Date, Time, Weather],
          (task(ID, Name, Status, EstimatedTime, Date, Time, Weather, Dependencies),
           can_execute(ID)),
          Tasks).


% Hechos de prueba (puedes eliminarlos una vez que los datos se carguen dinámicamente desde Java)
% Estos son solo para pruebas iniciales dentro de Prolog:
% task(ID, Name, Status, EstimatedTime, Date, Time, Weather, Dependencies)
% weather(Date, Hour, Condition)

% Hechos de ejemplo
% task(1, 'Comprar comida', 'Pendiente', 60, '2024-11-15', '08:00', 'Soleado', []).
% task(2, 'Limpiar casa', 'Pendiente', 120, '2024-11-15', '10:00', 'Nublado', [1]).
% weather('2024-11-15', '08:00', 'Soleado').
% weather('2024-11-15', '10:00', 'Nublado').

