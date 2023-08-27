package com.akamai.technical.task.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

//DONT USE @DATA to entity - problem with hashcode and equals lack of lazy fetch and problem with hashSets
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SocialNetworkPost extends BaseEntity {

    private String content;
    private String author;
    private Long viewCount;

    @CreationTimestamp
    private Instant postDate;
}
