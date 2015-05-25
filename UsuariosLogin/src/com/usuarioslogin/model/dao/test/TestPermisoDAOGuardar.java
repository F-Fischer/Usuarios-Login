package com.usuarioslogin.model.dao.test;

import java.sql.SQLException;

import com.usuarioslogin.conexion.Conexion;
import com.usuarioslogin.model.Permiso;
import com.usuarioslogin.model.dao.PermisoDAO;

public class TestPermisoDAOGuardar {
	
	public static void main(String[] args) throws SQLException {
		
		PermisoDAO pDAO = new PermisoDAO(Conexion.getConnection());
		Permiso p = new Permiso();
		
		p.setNombre("Permiso nombre");
		p.setDescripcion("descripcion del permiso1");
		
		Permiso pGuardardo = pDAO.guardar(p);
		
		System.out.println(pGuardardo);
	}

}
