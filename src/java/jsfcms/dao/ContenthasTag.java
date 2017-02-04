/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfcms.dao;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ahmadi
 */
@Entity
@Table(name = "Content_has_Tag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContenthasTag.findAll", query = "SELECT c FROM ContenthasTag c"),
    @NamedQuery(name = "ContenthasTag.findByContentID", query = "SELECT c FROM ContenthasTag c WHERE c.contenthasTagPK.contentID = :contentID"),
    @NamedQuery(name = "ContenthasTag.findByTagID", query = "SELECT c FROM ContenthasTag c WHERE c.contenthasTagPK.tagID = :tagID"),
    @NamedQuery(name = "ContenthasTag.findByDate", query = "SELECT c FROM ContenthasTag c WHERE c.date = :date")})
public class ContenthasTag implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ContenthasTagPK contenthasTagPK;
    @Column(name = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "Tag_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tag tag;
    @JoinColumn(name = "Content_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Content content;
    @JoinColumn(name = "cms_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cms cmsID;

    public ContenthasTag() {
    }

    public ContenthasTag(ContenthasTagPK contenthasTagPK) {
        this.contenthasTagPK = contenthasTagPK;
    }

    public ContenthasTag(int contentID, int tagID) {
        this.contenthasTagPK = new ContenthasTagPK(contentID, tagID);
    }

    public ContenthasTagPK getContenthasTagPK() {
        return contenthasTagPK;
    }

    public void setContenthasTagPK(ContenthasTagPK contenthasTagPK) {
        this.contenthasTagPK = contenthasTagPK;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
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
        hash += (contenthasTagPK != null ? contenthasTagPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContenthasTag)) {
            return false;
        }
        ContenthasTag other = (ContenthasTag) object;
        if ((this.contenthasTagPK == null && other.contenthasTagPK != null) || (this.contenthasTagPK != null && !this.contenthasTagPK.equals(other.contenthasTagPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jsfcms.dao.ContenthasTag[ contenthasTagPK=" + contenthasTagPK + " ]";
    }
    
}
