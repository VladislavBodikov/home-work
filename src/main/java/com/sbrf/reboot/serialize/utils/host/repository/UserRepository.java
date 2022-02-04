package com.sbrf.reboot.serialize.utils.host.repository;

import com.sbrf.reboot.serialize.utils.User;

import java.util.Set;

public interface UserRepository {
    Set<User> getAllUsersFromRepo();
    User getUserByCardNumber();
}
