package org.webinars.stream.practice.service;

import org.webinars.stream.practice.model.DataEntry;
import org.webinars.stream.practice.model.TimeTemperature;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public Map<String, Optional<TimeTemperature>> getAvgTempGroupedByProvince() {
        // TODO implement
        return null;
    }

    private Collector<DataEntry, ?, Optional<TimeTemperature>> getTemperatureCollector(CollectorType type) {
        Collector<DataEntry, ?, Optional<TimeTemperature>> collector = null;
        switch (type) {
            case AVG_TEMP -> {}
            case MAX_TEMP -> collector = Collectors.mapping(DataEntry::maximum,
                    Collectors.maxBy((o1, o2) -> Float.compare(o1.temp(), o2.temp())));
            case MIN_TEMP -> collector = Collectors.mapping(DataEntry::minimum,
                    Collectors.minBy((o1, o2) -> Float.compare(o1.temp(), o2.temp())));
            default -> throw new IllegalArgumentException("CollectorType isn't recognized");
        }
        return collector;
    }

}
