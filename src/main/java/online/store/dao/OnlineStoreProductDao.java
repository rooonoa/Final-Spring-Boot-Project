package online.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import online.store.entity.Product;

public interface OnlineStoreProductDao extends JpaRepository <Product, Long> {

}
