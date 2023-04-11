package org.webinars.stream.practice.utils;

import org.webinars.stream.practice.model.TimeTemperature;

import java.util.Map;
import java.util.Optional;

public class PrintUtils {
    public static final String STR_TMP_DATA_DISPLAY = """
            %s -> Estaci�n meteorol�gica: %s (%s)
            \tTemperatura: %.2fºC\tHora: %s
            """;
    public static final String STR_TMP_ENTRY_DISPLAY = "Provincia: %-25s | Temperatura: %6.2fºC | Hora: %s\n";

    public static void printMap(Map<String, Optional<TimeTemperature>> map) {
        map.keySet().stream().sorted().forEach(s -> map.get(s).ifPresentOrElse(timeTemperature ->
                System.out.printf(STR_TMP_ENTRY_DISPLAY, s, timeTemperature.temp(), timeTemperature.hour()),
                () -> System.err.println("No se pudo encontrar la temperatura correspondiente a " + s)
        ));
    }
}
