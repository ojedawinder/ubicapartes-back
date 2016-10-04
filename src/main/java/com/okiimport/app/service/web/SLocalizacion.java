package com.okiimport.app.service.web;

import java.util.List;

import com.okiimport.app.model.Ciudad;
import com.okiimport.app.model.Compra;
import com.okiimport.app.model.Pais;
import com.okiimport.app.resource.service.model.Oficina;

public interface SLocalizacion {
	double calcularDistancia(Ciudad ciudadOrigen, Ciudad ciudadDestino);
	double calcularDistancia(Pais paisOrigen, Pais paisDestino);
	float calcularFleteZoomConPesoYDistancia(Compra compra, Ciudad ciudadDestino);
	Ciudad obtenerCiudadZoomDestinatario(Ciudad ciudadDestino);
	List<Oficina> obtenerOficinasARetirarZoom(Ciudad ciudadZoomDestinatario);
}
