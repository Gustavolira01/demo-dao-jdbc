package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Departament;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDAO sellerDAO = DaoFactory.createSellerDAO();
		
		System.out.println("=== TEST 1: seller findbtId ===");
		Seller seller = sellerDAO.findById(3);		
		System.out.println(seller);
		
		System.out.println("/n=== TEST 2: seller findbtId ===");
		Departament departament = new Departament(2, null);
		List<Seller> lista = sellerDAO.findByDepartament(departament);
		for(Seller obj: lista) {
			System.out.println(obj);
		}
	}

}
