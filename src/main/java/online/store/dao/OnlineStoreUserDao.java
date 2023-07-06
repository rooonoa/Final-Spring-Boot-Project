package online.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import online.store.entity.User;

public interface OnlineStoreUserDao extends JpaRepository <User, Long> {

}
