package com.kenuiworks.frameworkbox.dto;

import com.kenuiworks.frameworkbox.enums.SatisfactionLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrameworkDTO {

    private Long id;

    @NotNull
    @Size(min = 1, max = 200)
    private String name;

    @NotNull
    @Size(min = 1, max = 500)
    private String description;

    @Size(min = 1, max = 100)
    private String language;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SatisfactionLevel satisfactionLevel;

    @NotNull
    private Integer monthsOfExperience;





}
