/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfcms.dao;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ahmadi
 */
@Embeddable
public class ContenthasTagPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "Content_ID")
    private int contentID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Tag_ID")
    private int tagID;

    public ContenthasTagPK() {
    }

    public ContenthasTagPK(int contentID, int tagID) {
        this.contentID = contentID;
        this.tagID = tagID;
    }

    public int getContentID() {
        return contentID;
    }

    public void setContentID(int contentID) {
        this.contentID = contentID;
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) contentID;
        hash += (int) tagID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContenthasTagPK)) {
            return false;
        }
        ContenthasTagPK other = (ContenthasTagPK) object;
        if (this.contentID != other.contentID) {
            return false;
        }
        if (this.tagID != other.tagID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jsfcms.dao.ContenthasTagPK[ contentID=" + contentID + ", tagID=" + tagID + " ]";
    }
    
}
