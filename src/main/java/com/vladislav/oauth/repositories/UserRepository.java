package com.vladislav.oauth.repositories;

import com.vladislav.oauth.models.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByGoogleAccountId(String id);
}
