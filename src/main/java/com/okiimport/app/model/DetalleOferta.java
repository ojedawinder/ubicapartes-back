package com.okiimport.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.okiimport.app.resource.model.AbstractEntity;

/**
 * The persistent class for the detalle_oferta database table.
 * 
 */
@Entity
@Table(name="detalle_oferta")
@NamedQuery(name="DetalleOferta.findAll", query="SELECT d FROM DetalleOferta d")
public class DetalleOferta extends AbstractEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="detalle_oferta_id_seq")
	@SequenceGenerator(name="detalle_oferta_id_seq", sequenceName="detalle_oferta_id_seq", initialValue=1, allocationSize=1)
	@Column(name="id_detalle_oferta")
	private Integer idDetalleOferta;
	
	@Enumerated(EnumType.STRING)
	private String estatus;
	
	private Boolean aprobado = false;
		
	//bi-directional many-to-one association to Oferta
	@ManyToOne
	@JoinColumn(name="id_oferta")
	private Oferta oferta;
	
	//bi-directional many-to-one association to Compra
	@ManyToOne
	@JoinColumn(name="id_compra")
	private Compra compra;
	
	@ManyToOne
	@JoinColumn(name="id_orden_compra")
	private OrdenCompra ordenCompra;
	
	//bi-directional many-to-one association to DetalleCotizacion
	@ManyToOne
	@JoinColumn(name="id_detalle_cotizacion")
	private DetalleCotizacion detalleCotizacion;
	
	@Column(name="cantidad_seleccionada")
	private Long cantidadSeleccionada= new Long(0);
	
	@Column(name="estatus_favorito")
	private Boolean estatusFavorito=new Boolean(false);
	
	@Transient
	private Float precioTotal;

	public DetalleOferta() {
	}
	
	public DetalleOferta(DetalleCotizacion detalleCotizacion){
		this.detalleCotizacion = detalleCotizacion;
	}

	public Integer getIdDetalleOferta() {
		return idDetalleOferta;
	}

	public void setIdDetalleOferta(Integer idDetalleOferta) {
		this.idDetalleOferta = idDetalleOferta;
	}
	
	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Boolean getAprobado() {
		return aprobado;
	}

	public void setAprobado(Boolean aprobado) {
		this.aprobado = aprobado;
	}
	
	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}
	
	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	
	public OrdenCompra getOrdenCompra() {
		return ordenCompra;
	}

	public void setOrdenCompra(OrdenCompra ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public DetalleCotizacion getDetalleCotizacion() {
		return detalleCotizacion;
	}

	public void setDetalleCotizacion(DetalleCotizacion detalleCotizacion) {
		this.detalleCotizacion = detalleCotizacion;
	}
	
	public Long getCantidadSeleccionada() {
		return cantidadSeleccionada;
	}

	public void setCantidadSeleccionada(Long cantidadSeleccionada) {
		this.cantidadSeleccionada = cantidadSeleccionada;
	}

	public Boolean getEstatusFavorito() {
		return estatusFavorito;
	}

	public void setEstatusFavorito(Boolean estatusFavorito) {
		this.estatusFavorito = estatusFavorito;
	}
	
	public Float getPrecioTotal() {
		Float costo = (float) 0;
		if(this.cantidadSeleccionada>0)
		   costo=this.detalleCotizacion.getPrecioVenta()*this.cantidadSeleccionada;
		Float porctGanancia = this.oferta.getPorctGanancia();
		return (porctGanancia!=0) ? (costo*(1+this.oferta.getPorctIva()))/(1-porctGanancia) : new Float(0);
	}

	public void setPrecioTotal(Float precioTotal) {
		this.precioTotal = precioTotal;
	}
	
	

	/**METODOS PROPIOS DE LA CLASE*/
	public Float calcularCosto(){
		return this.detalleCotizacion.calcularTotal();
	}
	
	public Float calcularCostoConverter(){
		return this.detalleCotizacion.getCotizacion().getHistoricoMoneda().convert(calcularCosto()).floatValue();
	}
	
	public Float calcularPrecioVenta(){
		Float costo = calcularCosto();
		Float porctGanancia = this.oferta.getPorctGanancia();
		Float porctIva=this.oferta.getPorctIva();
//		Float costo = (detalleCotizacion instanceof DetalleCotizacion) 
//				? this.detalleCotizacion.calcularTotal() : 
//					((DetalleCotizacionInternacional) this.detalleCotizacion).calcularTotal();
		return (porctGanancia!=0) ? (costo*(1+porctIva)/(1-porctGanancia)) : new Float(0);
	}
	
	public Float calcularPrecioVentaSinFlete(){
		Float costo = (float) 0;
		if(this.cantidadSeleccionada>0)
			costo=calcularPrecioVentaUnit()*this.cantidadSeleccionada;
		return costo;
		  // costo=this.detalleCotizacion.getPrecioVenta()*this.cantidadSeleccionada;
		//Float porctGanancia = this.oferta.getPorctGanancia();
		//Float porctIva=this.oferta.getPorctIva();
		//return (porctGanancia!=0) ? (costo*(1+porctIva)/(1-porctGanancia)) : new Float(0);
	}
	
	public Float calcularPrecioVentaConverter(){
		return this.detalleCotizacion.getCotizacion().getHistoricoMoneda().convert(calcularPrecioVenta()).floatValue();
	}
	
	public Float calcularPrecioVentaUnit(){
		float costo=this.detalleCotizacion.getPrecioVenta();
		Float porctGanancia = this.oferta.getPorctGanancia();
		Float porctIva=this.oferta.getPorctIva();
		return (porctGanancia!=0) ? (costo*(1+porctIva)/(1-porctGanancia)) : new Float(0);
		}
	
	@Transient
	public Proveedor getProveedor(){
		return this.getDetalleCotizacion().getCotizacion().getProveedor();
	}
	
	@Transient

	public DetalleCotizacion getDetalleCotizacionParaRecotizacion(boolean nullCotizacion){
		DetalleCotizacion detalleCotizacion = this.getDetalleCotizacion().clon();
		detalleCotizacion.setIdDetalleCotizacion(null);
		if(nullCotizacion)
			detalleCotizacion.setCotizacion(null);
		if(this.aprobado == null || !this.aprobado){
			detalleCotizacion.setCantidad(null);
			detalleCotizacion.setPrecioVenta(null);
			detalleCotizacion.setPrecioFlete(null);
		}

		return detalleCotizacion;
	}
	
	@Transient
	public boolean mostrarEsatusFavorito(){
		return this.getEstatusFavorito();
	}
	
}
