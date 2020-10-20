package com.manishtraders.repository;


import com.manishtraders.model.Order;
import com.manishtraders.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

}
