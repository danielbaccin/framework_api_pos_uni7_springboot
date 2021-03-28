package com.kenuiworks.frameworkbox.builder;

import com.kenuiworks.frameworkbox.dto.FrameworkDTO;
import com.kenuiworks.frameworkbox.enums.SatisfactionLevel;
import lombok.Builder;

import javax.persistence.*;

@Builder
public class FrameworkDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String tittle = "Hibernate";

    @Builder.Default
    private String description = "Frawork para ORM";

    @Builder.Default
    private String language = "Java";

    @Builder.Default
    private SatisfactionLevel satisfactionLevel = SatisfactionLevel.EXCELENT;

    public FrameworkDTO toFrameworkDTO() {
        return new FrameworkDTO(id,
                tittle,
                description,
                language,
                satisfactionLevel);
    }

}
