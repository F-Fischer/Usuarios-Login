package com.usuarioslogin.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.usuarioslogin.model.Grupo;

public class GrupoDAO {

	private Connection c;

	public GrupoDAO(Connection c) {
		this.c = c;
	}
	
	public Grupo guardar(Grupo grupo) throws SQLException  {
		if (grupo.getIdGrupo() != -1)
			return grupo;
		
		try{
			c.setAutoCommit(false);
			PreparedStatement pst = c
					.prepareStatement("INSERT INTO grupo (nombre, descripcion) VALUES (?,?)");
			pst.setString(1, grupo.getNombre());
			pst.setString(2, grupo.getDescripcion());
			pst.executeUpdate();

			pst = c.prepareStatement("SELECT LAST_INSERT_ID() as id");
			ResultSet rs = pst.executeQuery();
			rs.next();
			grupo.setIdGrupo(rs.getInt("id"));
			c.commit();
			
		}catch(Exception e){
			c.rollback();
			throw new SQLException(e);
		}finally{
			c.setAutoCommit(true);
		}
		
		
		return grupo;
	}
	
	public boolean modificar(Grupo grupo) throws SQLException {
		if (grupo.getIdGrupo() == -1)
			return false;

		boolean resultado = false;
		try {
			c.setAutoCommit(false);
			PreparedStatement pst = c
					.prepareStatement("UPDATE  grupo SET nombre=?, descripcion=? WHERE idGrupo=?");
			pst.setString(1, grupo.getNombre());
			pst.setString(2, grupo.getDescripcion());
			pst.setInt(3, grupo.getIdGrupo());
			pst.executeUpdate();
			c.commit();
			resultado = true;
		} catch (Exception e) {
			c.rollback();
			throw new SQLException(e);
		} finally {
			c.setAutoCommit(true);
		}
		return resultado;
	}
	
	public Grupo modificarGuardar(Grupo grupo) throws SQLException {
		if (grupo.getIdGrupo() == -1) {
			return guardar(grupo);
		} else {
			modificar(grupo);
			return grupo;
		}
	}
	
	public boolean eliminar(Grupo grupo) throws SQLException {
		if (grupo.getIdGrupo() == -1)
			return false;

		boolean resultado = false;
		try {
			c.setAutoCommit(false);
			PreparedStatement pst = c
					.prepareStatement("DELETE FROM grupo WHERE idGrupo=?");
			pst.setInt(1, grupo.getIdGrupo());
			pst.executeUpdate();
			c.commit();
			resultado = true;
		} catch (Exception e) {
			c.rollback();
			throw new SQLException(e);
		} finally {
			c.setAutoCommit(true);
		}
		return resultado;
	}
	
}
