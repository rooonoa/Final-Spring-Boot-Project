package online.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import online.store.entity.Category;

public interface OnlineStoreCategoryDao extends JpaRepository<Category, Long> {

}
