/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.universidad.dominio.unibi;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Paul Mendoza del Carpio
 */
@Entity
@Table(name = "tbl_prestamos_solicitudes_detalle", catalog = "unidb", schema = "sch_unibi")
@SequenceGenerator(sequenceName="seq_prestamos_solicitudes_detalle", name = "seq_prestamos_solicitudes_detalle", catalog ="unidb", schema = "sch_unibi",allocationSize=1)
@NamedQueries({
    @NamedQuery(name = "TblPrestamosSolicitudesDetalle.consultarPorSolicitud", query = "SELECT t FROM TblPrestamosSolicitudesDetalle t WHERE t.prestamosSolicitudesId = :prestamosSolicitudesId")})
public class TblPrestamosSolicitudesDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_prestamos_solicitudes_detalle" )
    private Integer id;
    @JoinColumn(name = "prestamos_solicitudes_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblPrestamosSolicitudes prestamosSolicitudesId;
    @JoinColumn(name = "ejemplar_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TblEjemplares ejemplarId;

    public TblPrestamosSolicitudesDetalle() {
    }

    public TblPrestamosSolicitudesDetalle(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TblPrestamosSolicitudes getPrestamosSolicitudesId() {
        return prestamosSolicitudesId;
    }

    public void setPrestamosSolicitudesId(TblPrestamosSolicitudes prestamosSolicitudedId) {
        this.prestamosSolicitudesId = prestamosSolicitudesId;
    }

    public TblEjemplares getEjemplarId() {
        return ejemplarId;
    }

    public void setEjemplarId(TblEjemplares ejemplarId) {
        this.ejemplarId = ejemplarId;
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
        if (!(object instanceof TblPrestamosSolicitudesDetalle)) {
            return false;
        }
        TblPrestamosSolicitudesDetalle other = (TblPrestamosSolicitudesDetalle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.universidad.dominio.unibi.TblPrestamoSolicitudDetalle[ id=" + id + " ]";
    }

}
