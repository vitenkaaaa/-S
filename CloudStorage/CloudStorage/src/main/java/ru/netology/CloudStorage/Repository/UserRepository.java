package ru.netology.CloudStorage.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.CloudStorage.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

