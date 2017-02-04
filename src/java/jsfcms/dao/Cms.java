/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfcms.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ahmadi
 */
@Entity
@Table(name = "CMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cms.findAll", query = "SELECT c FROM Cms c"),
    @NamedQuery(name = "Cms.findById", query = "SELECT c FROM Cms c WHERE c.id = :id"),
    @NamedQuery(name = "Cms.findByDomain", query = "SELECT c FROM Cms c WHERE c.domain = :domain")})
public class Cms implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 100)
    @Column(name = "Domain")
    private String domain;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cmsID")
    private List<User> userList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cmsID")
    private List<ContenthasTag> contenthasTagList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cmsID")
    private List<Settings> settingsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cmsID")
    private List<Template> templateList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cmsID")
    private List<UserRoles> userRolesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cmsID")
    private List<Compound> compoundList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cmsID")
    private List<Tag> tagList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cmsID")
    private List<TypePageNews> typePageNewsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cmsID")
    private List<UserhasSettings> userhasSettingsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cmsID")
    private List<Setting> settingList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cmsID")
    private List<Content> contentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cmsID")
    private List<Categary> categaryList;

    public Cms() {
    }

    public Cms(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @XmlTransient
    public List<ContenthasTag> getContenthasTagList() {
        return contenthasTagList;
    }

    public void setContenthasTagList(List<ContenthasTag> contenthasTagList) {
        this.contenthasTagList = contenthasTagList;
    }

    @XmlTransient
    public List<Settings> getSettingsList() {
        return settingsList;
    }

    public void setSettingsList(List<Settings> settingsList) {
        this.settingsList = settingsList;
    }

    @XmlTransient
    public List<Template> getTemplateList() {
        return templateList;
    }

    public void setTemplateList(List<Template> templateList) {
        this.templateList = templateList;
    }

    @XmlTransient
    public List<UserRoles> getUserRolesList() {
        return userRolesList;
    }

    public void setUserRolesList(List<UserRoles> userRolesList) {
        this.userRolesList = userRolesList;
    }

    @XmlTransient
    public List<Compound> getCompoundList() {
        return compoundList;
    }

    public void setCompoundList(List<Compound> compoundList) {
        this.compoundList = compoundList;
    }

    @XmlTransient
    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @XmlTransient
    public List<TypePageNews> getTypePageNewsList() {
        return typePageNewsList;
    }

    public void setTypePageNewsList(List<TypePageNews> typePageNewsList) {
        this.typePageNewsList = typePageNewsList;
    }

    @XmlTransient
    public List<UserhasSettings> getUserhasSettingsList() {
        return userhasSettingsList;
    }

    public void setUserhasSettingsList(List<UserhasSettings> userhasSettingsList) {
        this.userhasSettingsList = userhasSettingsList;
    }

    @XmlTransient
    public List<Setting> getSettingList() {
        return settingList;
    }

    public void setSettingList(List<Setting> settingList) {
        this.settingList = settingList;
    }

    @XmlTransient
    public List<Content> getContentList() {
        return contentList;
    }

    public void setContentList(List<Content> contentList) {
        this.contentList = contentList;
    }

    @XmlTransient
    public List<Categary> getCategaryList() {
        return categaryList;
    }

    public void setCategaryList(List<Categary> categaryList) {
        this.categaryList = categaryList;
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
        if (!(object instanceof Cms)) {
            return false;
        }
        Cms other = (Cms) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jsfcms.dao.Cms[ id=" + id + " ]";
    }
    
}
