package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Departament;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Departament departament = new Departament(1, "Books");
		Seller seller = new Seller(1, "Gustavo Lira", "gustavo@gmail.com", new Date(), 3000.00, departament);
		SellerDAO sellerDAO = DaoFactory.createSellerDAO();
		System.out.println(seller);

	}

}
