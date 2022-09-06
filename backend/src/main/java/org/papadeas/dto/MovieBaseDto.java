package org.papadeas.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MovieBaseDto extends BaseDto {

    private String title;

    private String description;

    private String createdBy;

}
