package com.lazulite.zp.web.rest;

import com.lazulite.zp.domain.Sw;
import com.lazulite.zp.service.SwService;
import com.lazulite.zp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
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
 * REST controller for managing {@link com.lazulite.zp.domain.Sw}.
 */
@RestController
@RequestMapping("/api")
public class SwResource {

    private final Logger log = LoggerFactory.getLogger(SwResource.class);

    private static final String ENTITY_NAME = "sw";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SwService swService;

    public SwResource(SwService swService) {
        this.swService = swService;
    }

    /**
     * {@code POST  /sws} : Create a new sw.
     *
     * @param sw the sw to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sw, or with status {@code 400 (Bad Request)} if the sw has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sws")
    public ResponseEntity<Sw> createSw(@RequestBody Sw sw) throws URISyntaxException {
        log.debug("REST request to save Sw : {}", sw);
        if (sw.getId() != null) {
            throw new BadRequestAlertException("A new sw cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sw result = swService.save(sw);
        return ResponseEntity.created(new URI("/api/sws/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sws} : Updates an existing sw.
     *
     * @param sw the sw to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sw,
     * or with status {@code 400 (Bad Request)} if the sw is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sw couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sws")
    public ResponseEntity<Sw> updateSw(@RequestBody Sw sw) throws URISyntaxException {
        log.debug("REST request to update Sw : {}", sw);
        if (sw.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sw result = swService.save(sw);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sw.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sws} : get all the sws.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sws in body.
     */
    @GetMapping("/sws")
    public ResponseEntity<List<Sw>> getAllSws(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Sws");
        Page<Sw> page = swService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sws/:id} : get the "id" sw.
     *
     * @param id the id of the sw to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sw, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sws/{id}")
    public ResponseEntity<Sw> getSw(@PathVariable Long id) {
        log.debug("REST request to get Sw : {}", id);
        Optional<Sw> sw = swService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sw);
    }

    /**
     * {@code DELETE  /sws/:id} : delete the "id" sw.
     *
     * @param id the id of the sw to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sws/{id}")
    public ResponseEntity<Void> deleteSw(@PathVariable Long id) {
        log.debug("REST request to delete Sw : {}", id);
        swService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
