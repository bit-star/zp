package com.lazulite.zp.service;

import com.lazulite.zp.domain.Zhaopin;
import com.lazulite.zp.repository.ZhaopinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Zhaopin}.
 */
@Service
@Transactional
public class ZhaopinService {

    private final Logger log = LoggerFactory.getLogger(ZhaopinService.class);

    private final ZhaopinRepository zhaopinRepository;

    public ZhaopinService(ZhaopinRepository zhaopinRepository) {
        this.zhaopinRepository = zhaopinRepository;
    }

    /**
     * Save a zhaopin.
     *
     * @param zhaopin the entity to save.
     * @return the persisted entity.
     */
    public Zhaopin save(Zhaopin zhaopin) {
        log.debug("Request to save Zhaopin : {}", zhaopin);
        return zhaopinRepository.save(zhaopin);
    }

    /**
     * Get all the zhaopins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Zhaopin> findAll(Pageable pageable) {
        log.debug("Request to get all Zhaopins");
        return zhaopinRepository.findAll(pageable);
    }


    /**
     * Get one zhaopin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Zhaopin> findOne(Long id) {
        log.debug("Request to get Zhaopin : {}", id);
        return zhaopinRepository.findById(id);
    }

    /**
     * Delete the zhaopin by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Zhaopin : {}", id);
        zhaopinRepository.deleteById(id);
    }


    public List<Zhaopin> findAllByCluster(Long cluster){
       return zhaopinRepository.findAllByCluster(cluster);
    }
}
