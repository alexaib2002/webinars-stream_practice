package org.webinars.stream.practice;

import org.webinars.stream.practice.service.WeatherService;
import org.webinars.stream.practice.utils.PrintUtils;

import java.util.Arrays;

public class Main {

    public static final String STR_SEPARATOR = "-----------------------------------";
    private static final Runnable[] dataPrintMethods = {
            Main::printTotalMaxAndMin,
            Main::printMaxByProvince,
            Main::printMinByProvince,
            Main::printAverageByProvince,
            () -> printAllByProvince("SEVILLA")
    };

    private static WeatherService weatherService;

    public static void main(String[] args) {
        weatherService = new WeatherService(DataSource.getAll());
        printWeatherReport();
    }

    private static void printWeatherReport() {
        System.out.println("Informe meteorol�gico del d�a 31 de Octubre de 2017");
        System.out.println(STR_SEPARATOR);
        Arrays.stream(dataPrintMethods).forEach(Runnable::run);
    }

    private static void printTotalMaxAndMin() {
        System.out.println("TEMPERATURA MAXIMA Y MINIMA DE ESPAÑA");

        weatherService.getMaximumTemp().ifPresentOrElse(dataEntry ->
                System.out.printf(PrintUtils.STR_TMP_DATA_DISPLAY, "MAXIMA", dataEntry.station(), dataEntry.province(),
                        dataEntry.maximum().temp(), dataEntry.maximum().hour()),
                () -> System.err.println("No se han encontrado datos sobre la temperatura m�xima"));
        weatherService.getMinimumTemp().ifPresentOrElse(dataEntry ->
                System.out.printf(PrintUtils.STR_TMP_DATA_DISPLAY, "MAXIMA", dataEntry.station(), dataEntry.province(),
                        dataEntry.maximum().temp(), dataEntry.maximum().hour()),
                () -> System.err.println("No se han encontrado datos sobre la temperatura m�nima"));
    }

    private static void printMaxByProvince() {
        System.out.println("TEMPERATURA MAXIMA DE CADA PROVINCIA");
        PrintUtils.printMap(weatherService.getTopTempGroupedByProvince(true));
    }

    private static void printMinByProvince() {
        System.out.println("TEMPERATURA MINIMA DE CADA PROVINCIA");
        PrintUtils.printMap(weatherService.getTopTempGroupedByProvince(true));
    }

    private static void printAverageByProvince() {
        System.out.println("TEMPERATURA MEDIA DE CADA PROVINCIA");
        PrintUtils.printMap(weatherService.getAvgTempGroupedByProvince());
    }

    private static void printAllByProvince(String province) {

    }

}
