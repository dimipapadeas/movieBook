package org.papadeas.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class MovieDto extends MovieBaseDto {

    Instant createdOn;

    List<VoteDto> votes;

    private long likes;

    private long dislikes;
}
