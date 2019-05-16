package com.lazulite.zp.repository;

import com.lazulite.zp.domain.Sw;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sw entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SwRepository extends JpaRepository<Sw, Long> {

}
