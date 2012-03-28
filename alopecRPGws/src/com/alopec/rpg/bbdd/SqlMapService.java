package com.alopec.rpg.bbdd;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;


import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlMapService {


	private static Logger logger = Logger.getLogger(SqlMapService.class);
	private static SqlMapClient sqlMapClient = null;
	
	private static void loadSqlMap() 
	{
		try 
		{
			if(sqlMapClient == null)
			{
				logger.info("Iniciando la configuración de Ibatis");
				Reader lector = Resources.getResourceAsReader( "../bbdd/sqlmapconfig.xml");
				logger.debug("Leido el fichero de configuración");
				
				sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(lector);
				logger.info("Configuración de Ibatis cargada correctamente");
			}
		}
		catch (IOException e) 
		{
			logger.error("Problema al cargar la configuracion de IBATIS");				
			logger.error(e.getMessage());
		}
	}
	
	public static SqlMapClient getSqlMap() throws SQLException
	{
		if (sqlMapClient==null)
			loadSqlMap();
		return sqlMapClient;
	}
	
	public static Connection getConnection() throws SQLException
	{
		try
		{
			if (sqlMapClient == null)
				getSqlMap();
			DataSource ds = sqlMapClient.getDataSource();
			return ds.getConnection();
		}
		catch(SQLException ex)
		{
			logger.error("getConnection():Error al coger la conexión.");
			throw ex;
		}
	}
}
