package com.sbrf.reboot.serialize.utils.host;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbrf.reboot.serialize.utils.Score;
import com.sbrf.reboot.serialize.utils.User;
import com.sbrf.reboot.serialize.utils.host.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

public class BankServer {
    private Set<User> users;
    private UserRepository userRepository;


    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        users = userRepository.getAllUsersFromRepo();
    }

    public void getScoreByCardNumberJson(long cardNumber) {

        Optional<Score> foundScore = users.stream()
                .flatMap((user) -> user.getScores().stream())
                .filter((score) -> score.getCardNumber() == cardNumber)
                .findFirst();
        Score score = foundScore.orElseGet(Score::new);
        // serialize object
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("score.json"), score);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

}
