
package com.greenroots.nursery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {

    List<Plant> findByCategoryIgnoreCase(String category);

    List<Plant> findByNameContainingIgnoreCase(String name);
}

