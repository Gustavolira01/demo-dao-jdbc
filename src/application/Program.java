package application;

import java.util.Date;
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
		
		System.out.println("/n === TEST 2: seller findbtId ===");
		Departament departament = new Departament(2, null);
		List<Seller> lista = sellerDAO.findByDepartament(departament);
		for(Seller obj: lista) {
			System.out.println(obj);
		}
		
		System.out.println("/n === TEST 3: seller findAll ===");
		List<Seller> listaSeller = sellerDAO.findAll();
		for(Seller obj: listaSeller) {
			System.out.println(obj);
		}
		
		System.out.println("/n === TEST 4: seller insert ===");
		Seller obj = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, departament);
		sellerDAO.insert(obj);
		System.out.println("Inserted! new id = " + obj.getId());
		
		System.out.println("/n === TEST 5: seller update ===");
		seller = sellerDAO.findById(1);
		seller.setName("Martha Waine");
		sellerDAO.update(seller);
		System.out.println("Update completed!");
		
		System.out.println("/n === TEST 6: seller delete ===");
		sellerDAO.deleteById(1);
		System.out.println("Delete completed");	
	}

}
