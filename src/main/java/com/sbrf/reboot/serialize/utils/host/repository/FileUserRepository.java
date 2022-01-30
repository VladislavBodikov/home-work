package com.sbrf.reboot.serialize.utils.host.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbrf.reboot.serialize.utils.User;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
public class FileUserRepository implements UserRepository {

    private File repository;

    public FileUserRepository(String fileName) {
        repository = new File(fileName);
    }

    @Override
    public Set<User> getAllUsersFromRepo() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> listUser = null;
        try {
            listUser = objectMapper.readValue(repository, new TypeReference<List<User>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashSet<>(listUser);
    }

    @Override
    public User getUserByCardNumber() {
        return null;
    }
}
