package com.lazulite.zp.web.rest;

import com.lazulite.zp.domain.Zhaopin;
import com.lazulite.zp.service.ZhaopinService;
import com.lazulite.zp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.lazulite.zp.domain.Zhaopin}.
 */
@RestController
@RequestMapping("/api")
public class ZhaopinResource {

    private final Logger log = LoggerFactory.getLogger(ZhaopinResource.class);

    private static final String ENTITY_NAME = "zhaopin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZhaopinService zhaopinService;

    public ZhaopinResource(ZhaopinService zhaopinService) {
        this.zhaopinService = zhaopinService;
    }

    /**
     * {@code POST  /zhaopins} : Create a new zhaopin.
     *
     * @param zhaopin the zhaopin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zhaopin, or with status {@code 400 (Bad Request)} if the zhaopin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/zhaopins")
    public ResponseEntity<Zhaopin> createZhaopin(@RequestBody Zhaopin zhaopin) throws URISyntaxException {
        log.debug("REST request to save Zhaopin : {}", zhaopin);
        if (zhaopin.getId() != null) {
            throw new BadRequestAlertException("A new zhaopin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Zhaopin result = zhaopinService.save(zhaopin);
        return ResponseEntity.created(new URI("/api/zhaopins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /zhaopins} : Updates an existing zhaopin.
     *
     * @param zhaopin the zhaopin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zhaopin,
     * or with status {@code 400 (Bad Request)} if the zhaopin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zhaopin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/zhaopins")
    public ResponseEntity<Zhaopin> updateZhaopin(@RequestBody Zhaopin zhaopin) throws URISyntaxException {
        log.debug("REST request to update Zhaopin : {}", zhaopin);
        if (zhaopin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Zhaopin result = zhaopinService.save(zhaopin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, zhaopin.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /zhaopins} : get all the zhaopins.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zhaopins in body.
     */
    @GetMapping("/zhaopins")
    public ResponseEntity<List<Zhaopin>> getAllZhaopins(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Zhaopins");
        String cluster =queryParams.getFirst("cluster");
        if(StringUtils.isNotBlank(cluster)){
            List<Zhaopin> allByCluster = zhaopinService.findAllByCluster(Long.valueOf(cluster));
            return ResponseEntity.ok().body(allByCluster);
        }
        Page<Zhaopin> page = zhaopinService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /zhaopins/:id} : get the "id" zhaopin.
     *
     * @param id the id of the zhaopin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zhaopin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/zhaopins/{id}")
    public ResponseEntity<Zhaopin> getZhaopin(@PathVariable Long id) {
        log.debug("REST request to get Zhaopin : {}", id);
        Optional<Zhaopin> zhaopin = zhaopinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zhaopin);
    }

    /**
     * {@code DELETE  /zhaopins/:id} : delete the "id" zhaopin.
     *
     * @param id the id of the zhaopin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/zhaopins/{id}")
    public ResponseEntity<Void> deleteZhaopin(@PathVariable Long id) {
        log.debug("REST request to delete Zhaopin : {}", id);
        zhaopinService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
