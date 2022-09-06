package org.papadeas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.papadeas.enums.Reaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
public class VoteDto extends BaseDto {

    private String movieId;

    private String userId;

    private Reaction vote;

}
