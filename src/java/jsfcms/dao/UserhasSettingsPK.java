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
public class UserhasSettingsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "Settings_ID")
    private int settingsID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "User_ID")
    private int userID;

    public UserhasSettingsPK() {
    }

    public UserhasSettingsPK(int settingsID, int userID) {
        this.settingsID = settingsID;
        this.userID = userID;
    }

    public int getSettingsID() {
        return settingsID;
    }

    public void setSettingsID(int settingsID) {
        this.settingsID = settingsID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) settingsID;
        hash += (int) userID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserhasSettingsPK)) {
            return false;
        }
        UserhasSettingsPK other = (UserhasSettingsPK) object;
        if (this.settingsID != other.settingsID) {
            return false;
        }
        if (this.userID != other.userID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jsfcms.dao.UserhasSettingsPK[ settingsID=" + settingsID + ", userID=" + userID + " ]";
    }
    
}
