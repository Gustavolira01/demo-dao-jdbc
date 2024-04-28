package model.dao;

import java.util.List;

import model.entities.Departament;

public interface DepartamentDAO {
	
	void insert(Departament departament);
	void update(Departament departament);
	void deleteById(Integer id);
	Departament findById(Integer id);
	List<Departament> findAll();

}
