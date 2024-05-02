package model.dao.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Departament;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDAO {
	
	private Connection connection;
	
	public SellerDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Seller seller) {
		PreparedStatement st = null;
		try {
			st = connection.prepareStatement(
					"INSERTO INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartamentId) "
					+ "VALUES"
					+ "(?, ?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartament().getId());
			
			int rowsAffect = st.executeUpdate();
			
			if(rowsAffect > 0 ) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Erro inesperado, nenhuma linha foi alterada!");
			} 		
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}		
	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(
					"SELECT seller.*,departament.Name as DepName " 
					+ "FROM seller INNER JOIN departament "
					+ "ON selle.DepartamentId = departament.id "
					+ "WHERE seller.id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				Departament departament = instantiateDepartament(rs);
				Seller seller = instantiateSeller(rs, departament);
				return seller;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Departament departament) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setDepartament(departament);
		return seller;
	}

	private Departament instantiateDepartament(ResultSet rs) throws SQLException {
		Departament departament = new Departament();
		departament.setId(rs.getInt("DepartamentId"));
		departament.setName(rs.getString("DepName"));
		return departament;
	}
	
	@Override
	public List<Seller> findAll() {
		PreparedStatement st= null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(
					"SELECT seller.*,departament.Name as DepName "
					+ "FROM seller INNER JOIN departament "
					+ "WHERE seller.DepartamentId =  departament.id"
					+ "ORDER BY Name");
			rs = st.executeQuery();
			
			List<Seller> lista = new ArrayList<>();
			Map<Integer, Departament> map = new HashMap<>();
			
			while(rs.next()) {
				Departament dep = map.get(rs.getInt("DepartametId"));
				
				if(dep == null) {
					dep = instantiateDepartament(rs);
					map.put(rs.getInt("DepartametId"), dep);
				}
				
				Seller seller = instantiateSeller(rs, dep);
				lista.add(seller);	
			}
			
			return lista;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Seller> findByDepartament(Departament departament) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = connection.prepareStatement(
					"SELECT seller.*,departament.Name as DepName "
					+ "FROM seller INNER JOIN departament "
					+ "WHERE DepartamentId = ? "
					+ "ORDER BY Name");
			st.setInt(1, departament.getId());
			rs = st.executeQuery();
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Departament> map = new HashMap<>();
			
			while (rs.next()){
				Departament dep = map.get(rs.getInt("DepartametId"));
				
				if(dep == null) {
					dep = instantiateDepartament(rs);
					map.put(rs.getInt("DepartametId"), dep);
				}
				
				Seller seller = instantiateSeller(rs, dep);
				list.add(seller);	
			}	
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
}
