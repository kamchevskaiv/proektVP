package com.skin.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.skin.common.entity.Product;
import com.skin.common.entity.Brand;
import com.skin.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {
	@Autowired
	private ProductRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateProduct() {
		
		Brand brand=entityManager.find(Brand.class,5);
		Category category=entityManager.find(Category.class, 5);
		
		Product product=new Product();
		product.setName("AXIS-Y Mugwort Pore Clarifying Wash Off Pack 100ml");
		product.setShortDescription("Mugwort mask");
		product.setAlias("axis-y-mugwort");
		product.setFullDescription("Mugwort Pore Clarifying Wash Off mask packiging 100ml");
		
		product.setBrand(brand);
		product.setCategory(category);
		
		product.setPrice(1259);
		product.setEnabled(true);
		product.setInStock(true);
		product.setCreatedTime(new Date());
		product.setUpdatedTime(new Date());
		
		Product savedProduct=repo.save(product);
		
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isGreaterThan(0);
		
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isGreaterThan(0);
	}
	@Test
	public void testListAllProducts() {
		Iterable<Product> iterableProducts=repo.findAll();
		iterableProducts.forEach(System.out::println);
	}
	@Test
	public void testGetProduct() {
		Integer id=3;
		Product product=repo.findById(id).get();
		System.out.println(product);
		assertThat(product).isNotNull();	
			
	}
	@Test
	public void testUpdateProduct() {
		Integer id=3;
		Product product=repo.findById(id).get();
		product.setPrice(1199);
		Product updatedProduct=entityManager.find(Product.class,id);
		assertThat(updatedProduct.getPrice()).isEqualTo(1199);
	}
	@Test
	public void testDeleteProduct() {
		Integer id=1;
		repo.deleteById(id);
		
		Optional<Product> result=repo.findById(id);
		assertThat(!result.isPresent());
	}
	@Test
	public void testSaveProductWithImages() {
		Integer productId=3;
		Product product=repo.findById(productId).get();
		product.setMainImage("main image.jpg");
		product.addExtraImage("extra image 1.jpg");
		product.addExtraImage("extra_image_2.jpg");
		product.addExtraImage("extra-image-3.jpg");
		
		Product savedProduct=repo.save(product);
		assertThat(savedProduct.getImages().size()).isEqualTo(3);
	}

}