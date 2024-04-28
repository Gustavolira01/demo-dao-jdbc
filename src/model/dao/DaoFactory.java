package model.dao;

import model.dao.JDBC.SellerDaoJDBC;

public class DaoFactory {
	
	public static SellerDAO createSellerDAO() {
		return new SellerDaoJDBC();
	}

}
