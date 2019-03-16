package com.tefarana.cb.repository;

import com.tefarana.cb.domain.Extra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Extra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtraRepository extends JpaRepository<Extra, Long> {

}
