package com.videoclub;

import java.util.List;

import org.junit.Test;

import com.videoclub.dao.UserDAO;
import com.videoclub.pojo.User;

public class test {
	
	@Test
	public void aloja() {
		List<User> lista = UserDAO.getInstance().getAll();
		
	}

}
