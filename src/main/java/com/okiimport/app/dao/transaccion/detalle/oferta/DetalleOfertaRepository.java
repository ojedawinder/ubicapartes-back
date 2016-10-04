package com.okiimport.app.dao.transaccion.detalle.oferta;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.okiimport.app.model.DetalleOferta;
import com.okiimport.app.model.Oferta;
import com.okiimport.app.model.OrdenCompra;
import com.okiimport.app.model.Proveedor;
import com.okiimport.app.model.Requerimiento;
import com.okiimport.app.resource.dao.IGenericJPARepository;

@Repository
public interface DetalleOfertaRepository extends IGenericJPARepository<DetalleOferta, Integer> {
	List<DetalleOferta> findByOferta(Oferta oferta);
	List<DetalleOferta> findByOrdenCompra(OrdenCompra ordenCompra);
	List<DetalleOferta> findByCompra_IdCompra(Integer idCompra, Sort sort);
	Page<DetalleOferta> findByCompra_IdCompra(Integer idCompra, Pageable pageable);
	List<DetalleOferta> findByCompraRequerimientoAndDetalleCotizacion_Cotizacion_Proveedor(Requerimiento requerimiento, Proveedor proveedor);
	Page<DetalleOferta> findByCompraRequerimientoAndDetalleCotizacion_Cotizacion_Proveedor(Requerimiento requerimiento, Proveedor proveedor, Pageable pageable);
}
