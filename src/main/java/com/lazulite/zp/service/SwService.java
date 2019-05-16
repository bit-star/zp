package com.lazulite.zp.service;

import com.lazulite.zp.domain.Sw;
import com.lazulite.zp.repository.SwRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Sw}.
 */
@Service
@Transactional
public class SwService {

    private final Logger log = LoggerFactory.getLogger(SwService.class);

    private final SwRepository swRepository;

    public SwService(SwRepository swRepository) {
        this.swRepository = swRepository;
    }

    /**
     * Save a sw.
     *
     * @param sw the entity to save.
     * @return the persisted entity.
     */
    public Sw save(Sw sw) {
        log.debug("Request to save Sw : {}", sw);
        return swRepository.save(sw);
    }

    /**
     * Get all the sws.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Sw> findAll(Pageable pageable) {
        log.debug("Request to get all Sws");
        return swRepository.findAll(pageable);
    }


    /**
     * Get one sw by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Sw> findOne(Long id) {
        log.debug("Request to get Sw : {}", id);
        return swRepository.findById(id);
    }

    /**
     * Delete the sw by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Sw : {}", id);
        swRepository.deleteById(id);
    }
}
