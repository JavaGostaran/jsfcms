/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfcms.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ahmadi
 */
@Entity
@Table(name = "Compound")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compound.findAll", query = "SELECT c FROM Compound c"),
    @NamedQuery(name = "Compound.findById", query = "SELECT c FROM Compound c WHERE c.id = :id"),
    @NamedQuery(name = "Compound.findByIdc", query = "SELECT c FROM Compound c WHERE c.idc = :idc"),
    @NamedQuery(name = "Compound.findByRankContent", query = "SELECT c FROM Compound c WHERE c.rankContent = :rankContent")})
public class Compound implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "IDC")
    private Integer idc;
    @Column(name = "RankContent")
    private Integer rankContent;
    @JoinColumn(name = "Content_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Content contentID;
    @JoinColumn(name = "cms_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cms cmsID;

    public Compound() {
    }

    public Compound(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdc() {
        return idc;
    }

    public void setIdc(Integer idc) {
        this.idc = idc;
    }

    public Integer getRankContent() {
        return rankContent;
    }

    public void setRankContent(Integer rankContent) {
        this.rankContent = rankContent;
    }

    public Content getContentID() {
        return contentID;
    }

    public void setContentID(Content contentID) {
        this.contentID = contentID;
    }

    public Cms getCmsID() {
        return cmsID;
    }

    public void setCmsID(Cms cmsID) {
        this.cmsID = cmsID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compound)) {
            return false;
        }
        Compound other = (Compound) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jsfcms.dao.Compound[ id=" + id + " ]";
    }
    
}
