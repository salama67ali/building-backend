package com.suzasob.building_permission.repository;

import com.suzasob.building_permission.model.User;
import com.suzasob.building_permission.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(UserRole role);
    Optional<User> findByUserId(Long userId);
    boolean existsByEmail(String email);
    boolean existsByUserId(Long userId);
    
    List<User> findAllByOrderByNameAsc();

    List<User> findAllByOrderByRoleAsc();
    List<User> findAllByOrderByEmailAsc();
    List<User> findAllByOrderByUserIdAsc();
    List<User> findAllByOrderByNameDesc();

}