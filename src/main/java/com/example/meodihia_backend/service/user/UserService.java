package com.example.meodihia_backend.service.user;

import com.example.meodihia_backend.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService implements IUserService{
    @Override
    public Optional<User> findByUsername(String name) {
        return Optional.empty();
    }

    @Override
    public Boolean existsByUsername(String username) {
        return null;
    }

    @Override
    public Boolean existsByEmail(String email) {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }
}
