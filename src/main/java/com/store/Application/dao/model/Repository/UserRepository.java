package com.store.Application.dao.model.Repository;

import com.store.Application.dao.model.sql.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findUserEntityByUserName(String name);

    @Query("SELECT u from UserEntity u where u.userId=:uuid")
    List<UserEntity> findAll(UUID uuid);

    Optional<UserEntity> findById(UUID uuid);


}
