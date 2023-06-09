package com.sfg.course.spring6webapp.repository2;

import com.sfg.course.spring6webapp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {


}
