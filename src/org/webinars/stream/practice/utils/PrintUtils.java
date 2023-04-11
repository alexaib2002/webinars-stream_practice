package org.webinars.stream.practice.utils;

import org.webinars.stream.practice.model.DataEntry;
import org.webinars.stream.practice.model.TimeTemperature;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PrintUtils {
    public static final String STR_TMP_DATA_DISPLAY = """
            %s -> Estaci�n meteorol�gica: %s (%s)
            \tTemperatura: %.2fºC\tHora: %s
            """;
    public static final String STR_TMP_ENTRY_DISPLAY = "Provincia: %-25s | Temperatura: %6.2fºC | Hora: %s\n";
    public static final String STR_TMP_EXTENDED_ENTRY_DISPLAY =
            "Estacion meteorologica: %-25s | Temperatura minima: %6.2fºC | Temperatura maxima: %6.2fºC | Precipitacion: %s\n";
    public static final String STR_AVG_ENTRY_DISPLAY = "Provincia: %-25s | Media: %5.2f°C%n";

    public static void printOptMap(Map<String, Optional<TimeTemperature>> map) {
        map.keySet().stream().sorted().forEach(s -> map.get(s).ifPresentOrElse(timeTemperature ->
                System.out.printf(STR_TMP_ENTRY_DISPLAY, s, timeTemperature.temp(), timeTemperature.hour()),
                () -> System.err.println("No se pudo encontrar la temperatura correspondiente a " + s)
        ));
    }

    public static void printMap(Map<String, Double> map) {
        map.forEach((province, temp) -> System.out.printf(STR_AVG_ENTRY_DISPLAY, province, temp));
    }

    public static void printList(List<DataEntry> list) {
        list.forEach(
                dataEntry -> System.out.printf(STR_TMP_EXTENDED_ENTRY_DISPLAY, dataEntry.station(),
                        dataEntry.minimum().temp(), dataEntry.maximum().temp(), dataEntry.precipitation())
        );
    }
}
