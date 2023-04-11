package org.webinars.stream.practice.service;

import org.webinars.stream.practice.model.DataEntry;
import org.webinars.stream.practice.model.TimeTemperature;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public final class WeatherService {
    public enum CollectorType {
        MAX_TEMP, MIN_TEMP, AVG_TEMP
    }
    private final List<DataEntry> data;

    public WeatherService(List<DataEntry> data) {
        this.data = data;
    }

    public Optional<DataEntry> getMaximumTemp() {
        return data.stream()
                .max((o1, o2) -> Float.compare(o1.maximum().temp(), o2.maximum().temp()));
    }

    public Optional<DataEntry> getMinimumTemp() {
        return data.stream()
                .min((o1, o2) -> Float.compare(o1.minimum().temp(), o2.minimum().temp()));
    }

    public Map<String, Optional<TimeTemperature>> getTopTempGroupedByProvince(boolean max) {
        Collector<DataEntry, ?, Optional<TimeTemperature>> collector = max ?
                getTemperatureCollector(CollectorType.MAX_TEMP) : getTemperatureCollector(CollectorType.MIN_TEMP);
        return data.stream().collect(Collectors.groupingBy(DataEntry::province, collector));
    }

    public Map<String, Double> getAvgTempGroupedByProvince() {
        return data.stream().collect(Collectors.toMap(Function.identity(), // returns each data object, as they will be sorted and grouped later on
                        o -> (o.maximum().temp() + o.minimum().temp()) / 2)) // calculate the average between the maximum and minimum temperature
                .entrySet().stream().collect(Collectors.groupingBy(dataEntryFloatEntry ->
                        dataEntryFloatEntry.getKey().province(), // keys about to be ordered
                        TreeMap::new, // data structure used for inserting the data, in this case it's a Red-Black tree based on the natural sort of the keys
                        Collectors.averagingDouble(Map.Entry::getValue)));

    }

    public List<DataEntry> getTempByProvince(String province) {
        return data.stream().filter(dataEntry -> dataEntry.province().equalsIgnoreCase(province))
                .collect(Collectors.toList());
    }

    private Collector<DataEntry, ?, Optional<TimeTemperature>> getTemperatureCollector(CollectorType type) {
        Collector<DataEntry, ?, Optional<TimeTemperature>> collector = null;
        switch (type) {
            case MAX_TEMP -> collector = Collectors.mapping(DataEntry::maximum,
                    Collectors.maxBy((o1, o2) -> Float.compare(o1.temp(), o2.temp())));
            case MIN_TEMP -> collector = Collectors.mapping(DataEntry::minimum,
                    Collectors.minBy((o1, o2) -> Float.compare(o1.temp(), o2.temp())));
            default -> throw new IllegalArgumentException("CollectorType isn't recognized");
        }
        return collector;
    }

}
