package com.akamai.technical.task.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
//DONT USE @DATA to entity - problem with hashcode and equals lack of lazy fetch and problem with hashSets
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class SocialNetworkPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String author;
    private Long viewCount;
    private LocalDate postDate;
    @Version
    private Long version;
}
