package com.akamai.technical.task.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Persistable;


@Getter
@Setter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
public abstract class BaseEntity implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    protected BaseEntity() {
        this.isNew = true;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PrePersist
    void trackNotNew() {
        this.isNew = false;
    }


}
