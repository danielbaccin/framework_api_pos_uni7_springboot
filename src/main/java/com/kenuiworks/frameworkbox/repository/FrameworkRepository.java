package com.kenuiworks.frameworkbox.repository;

import com.kenuiworks.frameworkbox.model.Framework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrameworkRepository extends JpaRepository<Framework, Long> {
}
