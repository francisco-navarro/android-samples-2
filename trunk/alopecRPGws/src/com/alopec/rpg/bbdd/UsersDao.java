package com.alopec.rpg.bbdd;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.alopec.rpg.bbdd.bean.UserBean;


public class UsersDao {

	private static final String selectUserByName="users.selectUserByName";
	
	private static Logger logger = Logger.getLogger(UsersDao.class);
	
	public static UserBean loginUsuario(String name, String pass){
		try {
			
			ArrayList<UserBean> users =(ArrayList<UserBean>) SqlMapService.getSqlMap().queryForList(selectUserByName,name);
			if(users!=null)
				if(users.size()>0  )
				if(users.get(0).getPass().equals(pass))
					return users.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}
