package com.kenuiworks.frameworkbox.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SatisfactionLevel {

    EXCELENT("Excelente"),
    GOOD("Bom"),
    NICE("Legal"),
    BAD("Ruim");

    private final String description;
}
