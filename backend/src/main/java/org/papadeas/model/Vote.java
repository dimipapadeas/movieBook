package org.papadeas.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.papadeas.enums.Reaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "T_VOTES")
public class Vote extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "MOVIE", nullable = false, updatable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "USER", nullable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "VOTE")
    private Reaction vote;

}
