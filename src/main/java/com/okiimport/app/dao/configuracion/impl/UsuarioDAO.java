package com.okiimport.app.dao.configuracion.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.okiimport.app.model.Usuario;
import com.okiimport.app.resource.dao.AbstractJpaDao;

public class UsuarioDAO extends AbstractJpaDao<Usuario> {
	
	public Specification<Usuario> consultarUsuarios(final Usuario usuarioF){
		
		return new Specification<Usuario>() {
			public Predicate toPredicate(Root<Usuario> entity, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				// 1. Inicializar Variables
				inicializar(entity, criteriaQuery, criteriaBuilder);
				
				// 2. Generamos los Joins
				
				// 3. Creamos las Restricciones de la busqueda
				List<Predicate> restricciones = new ArrayList<Predicate>();
				
				if(usuarioF.getId()!=null)
					restricciones.add(criteriaBuilder.like(
							entity.<Long>get("id").as(String.class), "%"+String.valueOf(usuarioF.getId()).toLowerCase()+"%"
					));
				
				if(usuarioF.getUsername()!=null)
					restricciones.add(criteriaBuilder.like(
							criteriaBuilder.lower(entity.<String>get("username")),
							"%"+usuarioF.getUsername().toLowerCase()+"%"
					));
				
				if(usuarioF.getActivo()!=null)
					restricciones.add(criteriaBuilder.equal(
							entity.get("activo"), 
							usuarioF.getActivo()
					));
				
				// 4. Ejecutamos				
				return crearPredicate(restricciones);
			}
		};
	}
}
