package me.johnniang.websocketdemo.data;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    Collection<User> findAllUsers();

    Optional<User> getUserById(int id);
}
