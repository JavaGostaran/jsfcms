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
@Table(name = "User_has_Settings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserhasSettings.findAll", query = "SELECT u FROM UserhasSettings u"),
    @NamedQuery(name = "UserhasSettings.findBySettingsID", query = "SELECT u FROM UserhasSettings u WHERE u.userhasSettingsPK.settingsID = :settingsID"),
    @NamedQuery(name = "UserhasSettings.findByDt", query = "SELECT u FROM UserhasSettings u WHERE u.dt = :dt"),
    @NamedQuery(name = "UserhasSettings.findByUserID", query = "SELECT u FROM UserhasSettings u WHERE u.userhasSettingsPK.userID = :userID")})
public class UserhasSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserhasSettingsPK userhasSettingsPK;
    @Column(name = "dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;
    @JoinColumn(name = "User_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "Settings_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Settings settings;
    @JoinColumn(name = "cms_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cms cmsID;

    public UserhasSettings() {
    }

    public UserhasSettings(UserhasSettingsPK userhasSettingsPK) {
        this.userhasSettingsPK = userhasSettingsPK;
    }

    public UserhasSettings(int settingsID, int userID) {
        this.userhasSettingsPK = new UserhasSettingsPK(settingsID, userID);
    }

    public UserhasSettingsPK getUserhasSettingsPK() {
        return userhasSettingsPK;
    }

    public void setUserhasSettingsPK(UserhasSettingsPK userhasSettingsPK) {
        this.userhasSettingsPK = userhasSettingsPK;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
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
        hash += (userhasSettingsPK != null ? userhasSettingsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserhasSettings)) {
            return false;
        }
        UserhasSettings other = (UserhasSettings) object;
        if ((this.userhasSettingsPK == null && other.userhasSettingsPK != null) || (this.userhasSettingsPK != null && !this.userhasSettingsPK.equals(other.userhasSettingsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jsfcms.dao.UserhasSettings[ userhasSettingsPK=" + userhasSettingsPK + " ]";
    }
    
}
