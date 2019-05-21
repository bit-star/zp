package com.lazulite.zp.service;

import com.lazulite.zp.domain.Zhaopin;
import com.lazulite.zp.repository.ZhaopinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
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


    public Page<Zhaopin> findByWords(List<String> words,Pageable pageable){
        Page<Zhaopin> resultList = null;
        Specification querySpecifi = (Specification<Zhaopin>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String word : words) {
                predicates.add(criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get("zwmc")), "%"+word.toLowerCase()+"%")));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
        };
        resultList =  this.zhaopinRepository.findAll(querySpecifi,pageable);
        return resultList;
    }

    public String execPython(){

        try {
            File directory = new File(".");
            String path = directory.getCanonicalPath();
            String scriptFileName = "app.py";
            String exe = "python";
            String command = path+File.separator+scriptFileName;
            String num1 = "1";
            String num2 = "2";
            String[] cmdArr = new String[] {exe,command, num1, num2};
            Process process = null;
            process = Runtime.getRuntime().exec(cmdArr);
            InputStream is = process.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            String str = dis.readLine();
            process.waitFor();
            System.out.println("exec python result:"+str);
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
