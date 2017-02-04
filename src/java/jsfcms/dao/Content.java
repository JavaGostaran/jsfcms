/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfcms.dao;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ahmadi
 */
@Entity
@Table(name = "Content")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Content.findAll", query = "SELECT c FROM Content c"),
    @NamedQuery(name = "Content.findByHeader", query = "SELECT c FROM Content c WHERE c.header = :header"),
    @NamedQuery(name = "Content.findByTitle", query = "SELECT c FROM Content c WHERE c.title = :title"),
    @NamedQuery(name = "Content.findById", query = "SELECT c FROM Content c WHERE c.id = :id"),
    @NamedQuery(name = "Content.findByFile", query = "SELECT c FROM Content c WHERE c.file = :file"),
    @NamedQuery(name = "Content.findByVisibility", query = "SELECT c FROM Content c WHERE c.visibility = :visibility"),
    @NamedQuery(name = "Content.findByIsStatic", query = "SELECT c FROM Content c WHERE c.isStatic = :isStatic"),
    @NamedQuery(name = "Content.findByNameStatic", query = "SELECT c FROM Content c WHERE c.nameStatic = :nameStatic"),
    @NamedQuery(name = "Content.findByIsCompound", query = "SELECT c FROM Content c WHERE c.isCompound = :isCompound"),
    @NamedQuery(name = "Content.findByWriter", query = "SELECT c FROM Content c WHERE c.writer = :writer"),
    @NamedQuery(name = "Content.findByDate", query = "SELECT c FROM Content c WHERE c.date = :date")})
public class Content implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 200)
    @Column(name = "Header")
    private String header;
    @Size(max = 200)
    @Column(name = "Title")
    private String title;
    @Lob
    @Size(max = 65535)
    @Column(name = "Body")
    private String body;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 500)
    @Column(name = "File")
    private String file;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Visibility")
    private boolean visibility;
    @Column(name = "IsStatic")
    private Short isStatic;
    @Size(max = 100)
    @Column(name = "NameStatic")
    private String nameStatic;
    @Column(name = "IsCompound")
    private Short isCompound;
    @Size(max = 100)
    @Column(name = "Writer")
    private String writer;
    @Column(name = "Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
    private List<ContenthasTag> contenthasTagList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contentID")
    private List<Compound> compoundList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contentID")
    private List<Comment> commentList;
    @JoinColumn(name = "TypePageNews_ID", referencedColumnName = "ID")
    @ManyToOne
    private TypePageNews typePageNewsID;
    @JoinColumn(name = "TypePage_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TypePage typePageID;
    @JoinColumn(name = "cms_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Cms cmsID;
    @JoinColumn(name = "categary_ID", referencedColumnName = "ID")
    @ManyToOne
    private Categary categaryID;

    public Content() {
    }

    public Content(Integer id) {
        this.id = id;
    }

    public Content(Integer id, boolean visibility) {
        this.id = id;
        this.visibility = visibility;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public Short getIsStatic() {
        return isStatic;
    }

    public void setIsStatic(Short isStatic) {
        this.isStatic = isStatic;
    }

    public String getNameStatic() {
        return nameStatic;
    }

    public void setNameStatic(String nameStatic) {
        this.nameStatic = nameStatic;
    }

    public Short getIsCompound() {
        return isCompound;
    }

    public void setIsCompound(Short isCompound) {
        this.isCompound = isCompound;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @XmlTransient
    public List<ContenthasTag> getContenthasTagList() {
        return contenthasTagList;
    }

    public void setContenthasTagList(List<ContenthasTag> contenthasTagList) {
        this.contenthasTagList = contenthasTagList;
    }

    @XmlTransient
    public List<Compound> getCompoundList() {
        return compoundList;
    }

    public void setCompoundList(List<Compound> compoundList) {
        this.compoundList = compoundList;
    }

    @XmlTransient
    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public TypePageNews getTypePageNewsID() {
        return typePageNewsID;
    }

    public void setTypePageNewsID(TypePageNews typePageNewsID) {
        this.typePageNewsID = typePageNewsID;
    }

    public TypePage getTypePageID() {
        return typePageID;
    }

    public void setTypePageID(TypePage typePageID) {
        this.typePageID = typePageID;
    }

    public Cms getCmsID() {
        return cmsID;
    }

    public void setCmsID(Cms cmsID) {
        this.cmsID = cmsID;
    }

    public Categary getCategaryID() {
        return categaryID;
    }

    public void setCategaryID(Categary categaryID) {
        this.categaryID = categaryID;
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
        if (!(object instanceof Content)) {
            return false;
        }
        Content other = (Content) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jsfcms.dao.Content[ id=" + id + " ]";
    }
    
}
