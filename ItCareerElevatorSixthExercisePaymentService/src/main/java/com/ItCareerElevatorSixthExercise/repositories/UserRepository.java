package com.ItCareerElevatorSixthExercise.repositories;

import com.ItCareerElevatorSixthExercise.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByWalletAddress(String walletAddress);

    @Modifying
    @Query("UPDATE User u SET u.balance = u.balance - :orderTotalPrice WHERE u.id = :id")
    void payForOrder(@Param("orderTotalPrice") BigDecimal orderTotalPrice, @Param("id") String id);
}
// TODO: We are bending the versioning this way