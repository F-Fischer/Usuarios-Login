package com.usuarioslogin.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.usuarioslogin.model.Permiso;

public class PermisoDAO {

	private Connection c;

	public PermisoDAO(Connection c) {
		this.c = c;
	}

	public Permiso guardar(Permiso permiso) throws SQLException{
		
		if(permiso.getIdPermiso() != -1){
			return permiso;
		}
		
		try{
			c.setAutoCommit(false);
			PreparedStatement pst = c.prepareStatement("INSERT INTO permiso (nombre, descripcion) VALUES (?,?)");
			
			pst.setString(1, permiso.getNombre());
			pst.setString(2, permiso.getDescripcion());
			pst.executeUpdate();

			pst = c.prepareStatement("SELECT LAST_INSERT_ID() as id");
			ResultSet rs = pst.executeQuery();
			rs.next();
			permiso.setIdPermiso(rs.getInt("id"));
			c.commit();
		}catch(Exception e){
			c.rollback();
		} finally {
			c.setAutoCommit(true);
		}
		
		return null;
	}
}
