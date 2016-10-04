package com.okiimport.app.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.okiimport.app.model.enumerados.EEstatusGeneral;
import com.okiimport.app.resource.model.AbstractEntity;

/**
 * The persistent class for the banco database table.
 * 
 */
@Entity
@Table(name="banco")
@NamedQuery(name="Banco.findAll", query="SELECT b FROM Banco b")
@JsonIgnoreProperties({"pago"})
public class Banco extends AbstractEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="banco_id_seq")
	@SequenceGenerator(name="banco_id_seq", sequenceName="banco_id_seq", initialValue=1, allocationSize=1)
	@Column(name="id_banco")
	private Integer idBanco;
	
	private String nombre;
	
	private EEstatusGeneral estatus;
	
	//bi-directional one-to-many association to Pago
	@OneToMany(mappedBy="banco", fetch=FetchType.LAZY)
	private List<Pago> pago;

	public Banco() {
	}

	public Integer getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(Integer idBanco) {
		this.idBanco = idBanco;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public EEstatusGeneral getEstatus() {
		return estatus;
	}

	public void setEstatus(EEstatusGeneral estatus) {
		this.estatus = estatus;
	}

	public List<Pago> getPago() {
		return pago;
	}

	public void setPago(List<Pago> pago) {
		this.pago = pago;
	}
	
	public Pago addPago(Pago pago){
		getPago().add(pago);
		pago.setBanco(this);
		
		return pago;
	}
	
	public Pago removePago(Pago pago){
		getPago().remove(pago);
		pago.setBanco(null);
		
		return pago;
	}
}
