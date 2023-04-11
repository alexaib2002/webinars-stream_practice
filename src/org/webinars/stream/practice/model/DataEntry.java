package org.webinars.stream.practice.model;

import org.webinars.stream.practice.model.TimeTemperature;

import java.time.LocalDate;

public record DataEntry(LocalDate date, String station, String province, TimeTemperature maximum,
                        TimeTemperature minimum, float precipitation) {}
