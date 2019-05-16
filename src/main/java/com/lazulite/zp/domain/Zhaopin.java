package com.lazulite.zp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Zhaopin.
 */
@Entity
@Table(name = "zhaopin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Zhaopin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zwmc")
    private String zwmc;

    @Column(name = "gsmc")
    private String gsmc;

    @Column(name = "gzdd")
    private String gzdd;

    @Column(name = "xz_low")
    private Long xzLow;

    @Column(name = "xz_height")
    private Long xzHeight;

    @Column(name = "ptime")
    private Instant ptime;

    @Column(name = "href")
    private String href;

    @Column(name = "jhi_cluster")
    private Long cluster;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZwmc() {
        return zwmc;
    }

    public Zhaopin zwmc(String zwmc) {
        this.zwmc = zwmc;
        return this;
    }

    public void setZwmc(String zwmc) {
        this.zwmc = zwmc;
    }

    public String getGsmc() {
        return gsmc;
    }

    public Zhaopin gsmc(String gsmc) {
        this.gsmc = gsmc;
        return this;
    }

    public void setGsmc(String gsmc) {
        this.gsmc = gsmc;
    }

    public String getGzdd() {
        return gzdd;
    }

    public Zhaopin gzdd(String gzdd) {
        this.gzdd = gzdd;
        return this;
    }

    public void setGzdd(String gzdd) {
        this.gzdd = gzdd;
    }

    public Long getXzLow() {
        return xzLow;
    }

    public Zhaopin xzLow(Long xzLow) {
        this.xzLow = xzLow;
        return this;
    }

    public void setXzLow(Long xzLow) {
        this.xzLow = xzLow;
    }

    public Long getXzHeight() {
        return xzHeight;
    }

    public Zhaopin xzHeight(Long xzHeight) {
        this.xzHeight = xzHeight;
        return this;
    }

    public void setXzHeight(Long xzHeight) {
        this.xzHeight = xzHeight;
    }

    public Instant getPtime() {
        return ptime;
    }

    public Zhaopin ptime(Instant ptime) {
        this.ptime = ptime;
        return this;
    }

    public void setPtime(Instant ptime) {
        this.ptime = ptime;
    }

    public String getHref() {
        return href;
    }

    public Zhaopin href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Long getCluster() {
        return cluster;
    }

    public Zhaopin cluster(Long cluster) {
        this.cluster = cluster;
        return this;
    }

    public void setCluster(Long cluster) {
        this.cluster = cluster;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zhaopin)) {
            return false;
        }
        return id != null && id.equals(((Zhaopin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Zhaopin{" +
            "id=" + getId() +
            ", zwmc='" + getZwmc() + "'" +
            ", gsmc='" + getGsmc() + "'" +
            ", gzdd='" + getGzdd() + "'" +
            ", xzLow=" + getXzLow() +
            ", xzHeight=" + getXzHeight() +
            ", ptime='" + getPtime() + "'" +
            ", href='" + getHref() + "'" +
            ", cluster=" + getCluster() +
            "}";
    }
}
