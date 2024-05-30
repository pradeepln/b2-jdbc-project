package com.ibm.training.jdbc;

import java.util.List;
import java.util.Optional;

public class ProductDAOTest {

	public static void main(String[] args) {
		ProductDAO dao = new ProductDAO();

//		Optional<Product> o = dao.findById(10);
//		
//		if(o.isPresent()) {
//			Product p = o.get();
//			System.out.println(p);
//		}
		
//		List<Product> all = dao.findAll();
//		all.forEach(p -> System.out.println(p));
		
//		Product sample = new Product("sampleData", 838f, 123);
//		int id = dao.save(sample);
//		System.out.println("Saved with id: "+id);
		
//		Product p = dao.findById(3).get();
//		
//		p.setName("changed!!!");
//		p.setPrice(9999f);
//		dao.update(p);
		
		System.out.println("Deleted product with id 3? "+dao.deleteById(3));
		
	}

}
