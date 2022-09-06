package org.papadeas.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "T_MOVIE")
public class Movie extends EntityBase {

    @Column(name = "CREATED_ON", updatable = false)
    @CreationTimestamp
    Instant createdOn;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY", updatable = false)
    @CreatedBy
    private User createdBy;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TITLE")
    private String title;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes = new java.util.ArrayList<>();
}
