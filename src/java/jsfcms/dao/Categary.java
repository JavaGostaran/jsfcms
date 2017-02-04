/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfcms.dao;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ahmadi
 */
@Entity
@Table(name = "categary")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categary.findAll", query = "SELECT c FROM Categary c"),
    @NamedQuery(name = "Categary.findByName", query = "SELECT c FROM Categary c WHERE c.name = :name"),
    @NamedQuery(name = "Categary.findById", query = "SELECT c FROM Categary c WHERE c.id = :id"),
    @NamedQuery(name = "Categary.findByVisibility", query = "SELECT c FROM Categary c WHERE c.visibility = :visibility"),
    @NamedQuery(name = "Categary.findByInformation", query = "SELECT c FROM Categary c WHERE c.information = :information")})
public class Categary implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "Name")
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Visibility")
    private boolean visibility;
    @Size(max = 50)
    @Column(name = "Information")
    private String information;
    @OneToMany(mappedBy = "categaryID")
    private List<Content> contentList;
    @JoinColumn(name = "cms_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cms cmsID;
    @OneToMany(mappedBy = "parentID")
    private List<Categary> categaryList;
    @JoinColumn(name = "Parent_ID", referencedColumnName = "ID")
    @ManyToOne
    private Categary parentID;

    public Categary() {
    }

    public Categary(Integer id) {
        this.id = id;
    }

    public Categary(Integer id, String name, boolean visibility) {
        this.id = id;
        this.name = name;
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @XmlTransient
    public List<Content> getContentList() {
        return contentList;
    }

    public void setContentList(List<Content> contentList) {
        this.contentList = contentList;
    }

    public Cms getCmsID() {
        return cmsID;
    }

    public void setCmsID(Cms cmsID) {
        this.cmsID = cmsID;
    }

    @XmlTransient
    public List<Categary> getCategaryList() {
        return categaryList;
    }

    public void setCategaryList(List<Categary> categaryList) {
        this.categaryList = categaryList;
    }

    public Categary getParentID() {
        return parentID;
    }

    public void setParentID(Categary parentID) {
        this.parentID = parentID;
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
        if (!(object instanceof Categary)) {
            return false;
        }
        Categary other = (Categary) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jsfcms.dao.Categary[ id=" + id + " ]";
    }
    
}
