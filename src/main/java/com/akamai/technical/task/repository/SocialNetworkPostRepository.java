package com.akamai.technical.task.repository;

import com.akamai.technical.task.model.SocialNetworkPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialNetworkPostRepository extends JpaRepository<SocialNetworkPost, Long> {

}
