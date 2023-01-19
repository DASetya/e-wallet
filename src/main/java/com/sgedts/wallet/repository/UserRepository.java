package com.sgedts.wallet.repository;

import com.sgedts.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query(value = "UPDATE users u set u.ktp = :ktp where u.username = :username", nativeQuery = true)
    void addKtp(@Param(value = "ktp") String ktp, @Param(value = "username")String username);

    User findByUsername(String username);
    User findByKtp(String ktp);
}
