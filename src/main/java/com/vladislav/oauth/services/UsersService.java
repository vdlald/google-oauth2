package com.vladislav.oauth.services;

import com.vladislav.oauth.models.UserEntity;
import com.vladislav.oauth.repositories.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

  private final UserRepository userRepository;

  public Optional<UserEntity> findByGoogleAccountId(String id) {
    return userRepository.findByGoogleAccountId(id);
  }

  public UserEntity save(UserEntity entity) {
    return userRepository.save(entity);
  }
}
