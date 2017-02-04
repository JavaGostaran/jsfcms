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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "Settings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Settings.findAll", query = "SELECT s FROM Settings s"),
    @NamedQuery(name = "Settings.findById", query = "SELECT s FROM Settings s WHERE s.id = :id"),
    @NamedQuery(name = "Settings.findBySiteTitle", query = "SELECT s FROM Settings s WHERE s.siteTitle = :siteTitle"),
    @NamedQuery(name = "Settings.findBySiteIcon", query = "SELECT s FROM Settings s WHERE s.siteIcon = :siteIcon"),
    @NamedQuery(name = "Settings.findBySiteLogo", query = "SELECT s FROM Settings s WHERE s.siteLogo = :siteLogo")})
public class Settings implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 15)
    @Column(name = "SiteTitle")
    private String siteTitle;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteMenuTitle")
    private String siteMenuTitle;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteAdvertiseTitle")
    private String siteAdvertiseTitle;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteAdvertiseMessage")
    private String siteAdvertiseMessage;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteNewsTitle")
    private String siteNewsTitle;
    @Lob
    @Size(max = 65535)
    @Column(name = "SitePhotosTitle")
    private String sitePhotosTitle;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteUpperTitle")
    private String siteUpperTitle;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteFooterTitle")
    private String siteFooterTitle;
    @Size(max = 200)
    @Column(name = "SiteIcon")
    private String siteIcon;
    @Size(max = 200)
    @Column(name = "SiteLogo")
    private String siteLogo;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteCommentSend")
    private String siteCommentSend;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteCommentTitle")
    private String siteCommentTitle;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteCommentMessageAlert")
    private String siteCommentMessageAlert;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteCommentMessageSuccess")
    private String siteCommentMessageSuccess;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteCommentSender")
    private String siteCommentSender;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteCommentSended")
    private String siteCommentSended;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteCommentSendButton")
    private String siteCommentSendButton;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteWriterPost")
    private String siteWriterPost;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteDatePost")
    private String siteDatePost;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteLogin")
    private String siteLogin;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteLogout")
    private String siteLogout;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteRegister")
    private String siteRegister;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteRegisterName")
    private String siteRegisterName;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteRegisterUserName")
    private String siteRegisterUserName;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteRegisterPassword")
    private String siteRegisterPassword;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteRegisterEmail")
    private String siteRegisterEmail;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteRegisterButton")
    private String siteRegisterButton;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteWelcomeTitle")
    private String siteWelcomeTitle;
    @Lob
    @Size(max = 65535)
    @Column(name = "SiteWelcomeMessage")
    private String siteWelcomeMessage;
    @JoinColumn(name = "cms_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cms cmsID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "settings")
    private List<UserhasSettings> userhasSettingsList;

    public Settings() {
    }

    public Settings(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
    }

    public String getSiteMenuTitle() {
        return siteMenuTitle;
    }

    public void setSiteMenuTitle(String siteMenuTitle) {
        this.siteMenuTitle = siteMenuTitle;
    }

    public String getSiteAdvertiseTitle() {
        return siteAdvertiseTitle;
    }

    public void setSiteAdvertiseTitle(String siteAdvertiseTitle) {
        this.siteAdvertiseTitle = siteAdvertiseTitle;
    }

    public String getSiteAdvertiseMessage() {
        return siteAdvertiseMessage;
    }

    public void setSiteAdvertiseMessage(String siteAdvertiseMessage) {
        this.siteAdvertiseMessage = siteAdvertiseMessage;
    }

    public String getSiteNewsTitle() {
        return siteNewsTitle;
    }

    public void setSiteNewsTitle(String siteNewsTitle) {
        this.siteNewsTitle = siteNewsTitle;
    }

    public String getSitePhotosTitle() {
        return sitePhotosTitle;
    }

    public void setSitePhotosTitle(String sitePhotosTitle) {
        this.sitePhotosTitle = sitePhotosTitle;
    }

    public String getSiteUpperTitle() {
        return siteUpperTitle;
    }

    public void setSiteUpperTitle(String siteUpperTitle) {
        this.siteUpperTitle = siteUpperTitle;
    }

    public String getSiteFooterTitle() {
        return siteFooterTitle;
    }

    public void setSiteFooterTitle(String siteFooterTitle) {
        this.siteFooterTitle = siteFooterTitle;
    }

    public String getSiteIcon() {
        return siteIcon;
    }

    public void setSiteIcon(String siteIcon) {
        this.siteIcon = siteIcon;
    }

    public String getSiteLogo() {
        return siteLogo;
    }

    public void setSiteLogo(String siteLogo) {
        this.siteLogo = siteLogo;
    }

    public String getSiteCommentSend() {
        return siteCommentSend;
    }

    public void setSiteCommentSend(String siteCommentSend) {
        this.siteCommentSend = siteCommentSend;
    }

    public String getSiteCommentTitle() {
        return siteCommentTitle;
    }

    public void setSiteCommentTitle(String siteCommentTitle) {
        this.siteCommentTitle = siteCommentTitle;
    }

    public String getSiteCommentMessageAlert() {
        return siteCommentMessageAlert;
    }

    public void setSiteCommentMessageAlert(String siteCommentMessageAlert) {
        this.siteCommentMessageAlert = siteCommentMessageAlert;
    }

    public String getSiteCommentMessageSuccess() {
        return siteCommentMessageSuccess;
    }

    public void setSiteCommentMessageSuccess(String siteCommentMessageSuccess) {
        this.siteCommentMessageSuccess = siteCommentMessageSuccess;
    }

    public String getSiteCommentSender() {
        return siteCommentSender;
    }

    public void setSiteCommentSender(String siteCommentSender) {
        this.siteCommentSender = siteCommentSender;
    }

    public String getSiteCommentSended() {
        return siteCommentSended;
    }

    public void setSiteCommentSended(String siteCommentSended) {
        this.siteCommentSended = siteCommentSended;
    }

    public String getSiteCommentSendButton() {
        return siteCommentSendButton;
    }

    public void setSiteCommentSendButton(String siteCommentSendButton) {
        this.siteCommentSendButton = siteCommentSendButton;
    }

    public String getSiteWriterPost() {
        return siteWriterPost;
    }

    public void setSiteWriterPost(String siteWriterPost) {
        this.siteWriterPost = siteWriterPost;
    }

    public String getSiteDatePost() {
        return siteDatePost;
    }

    public void setSiteDatePost(String siteDatePost) {
        this.siteDatePost = siteDatePost;
    }

    public String getSiteLogin() {
        return siteLogin;
    }

    public void setSiteLogin(String siteLogin) {
        this.siteLogin = siteLogin;
    }

    public String getSiteLogout() {
        return siteLogout;
    }

    public void setSiteLogout(String siteLogout) {
        this.siteLogout = siteLogout;
    }

    public String getSiteRegister() {
        return siteRegister;
    }

    public void setSiteRegister(String siteRegister) {
        this.siteRegister = siteRegister;
    }

    public String getSiteRegisterName() {
        return siteRegisterName;
    }

    public void setSiteRegisterName(String siteRegisterName) {
        this.siteRegisterName = siteRegisterName;
    }

    public String getSiteRegisterUserName() {
        return siteRegisterUserName;
    }

    public void setSiteRegisterUserName(String siteRegisterUserName) {
        this.siteRegisterUserName = siteRegisterUserName;
    }

    public String getSiteRegisterPassword() {
        return siteRegisterPassword;
    }

    public void setSiteRegisterPassword(String siteRegisterPassword) {
        this.siteRegisterPassword = siteRegisterPassword;
    }

    public String getSiteRegisterEmail() {
        return siteRegisterEmail;
    }

    public void setSiteRegisterEmail(String siteRegisterEmail) {
        this.siteRegisterEmail = siteRegisterEmail;
    }

    public String getSiteRegisterButton() {
        return siteRegisterButton;
    }

    public void setSiteRegisterButton(String siteRegisterButton) {
        this.siteRegisterButton = siteRegisterButton;
    }

    public String getSiteWelcomeTitle() {
        return siteWelcomeTitle;
    }

    public void setSiteWelcomeTitle(String siteWelcomeTitle) {
        this.siteWelcomeTitle = siteWelcomeTitle;
    }

    public String getSiteWelcomeMessage() {
        return siteWelcomeMessage;
    }

    public void setSiteWelcomeMessage(String siteWelcomeMessage) {
        this.siteWelcomeMessage = siteWelcomeMessage;
    }

    public Cms getCmsID() {
        return cmsID;
    }

    public void setCmsID(Cms cmsID) {
        this.cmsID = cmsID;
    }

    @XmlTransient
    public List<UserhasSettings> getUserhasSettingsList() {
        return userhasSettingsList;
    }

    public void setUserhasSettingsList(List<UserhasSettings> userhasSettingsList) {
        this.userhasSettingsList = userhasSettingsList;
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
        if (!(object instanceof Settings)) {
            return false;
        }
        Settings other = (Settings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jsfcms.dao.Settings[ id=" + id + " ]";
    }
    
}
