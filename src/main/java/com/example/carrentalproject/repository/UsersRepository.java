package com.example.carrentalproject.repository;

import com.example.carrentalproject.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// JpaRepository extends both Crud and PagingAndSortingRepository, so it has everything from both
// might change this back to CrudRepository since I don't think the paging/sorting
// will be needed.
// https://stackoverflow.com/questions/14014086/what-is-difference-between-crudrepository-and-jparepository-interfaces-in-spring
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUsername(String username);

}