package com.lazulite.zp.repository;

import com.lazulite.zp.domain.Zhaopin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Zhaopin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZhaopinRepository extends JpaRepository<Zhaopin, Long> {

}
