package com.sbrf.reboot.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbrf.reboot.serialize.utils.Score;
import com.sbrf.reboot.serialize.utils.User;
import com.sbrf.reboot.serialize.utils.client.Card;
import com.sbrf.reboot.serialize.utils.client.CashMachine;
import com.sbrf.reboot.serialize.utils.host.BankServer;
import com.sbrf.reboot.serialize.utils.host.repository.FileUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientServerDomainSerializeTest {
    static User user1;
    static User user2;
    static Set<Score> scoresUser1 = new HashSet<>();
    static Set<Score> scoresUser2 = new HashSet<>();
    static File scoreJson = new File("score.json");
    static FileUserRepository fileUserRepository = new FileUserRepository("userRepository.json");

    @Test
    @DisplayName("Инициализация хранилища пользователей JSON")
    void fillUserRepository() {
        // init Scores
        Score score1 = new Score();
        score1.setAmount(new BigDecimal(10_000));
        score1.setCardNumber(1111_1111_1111_1111L);
        Score score2 = new Score();
        score2.setAmount(new BigDecimal(15_000));
        score2.setCardNumber(2222_2222_2222_2222L);
        Score score3 = new Score();
        score3.setAmount(new BigDecimal(35_000));
        score3.setCardNumber(3333_3333_3333_3333L);
        Score score4 = new Score();
        score4.setAmount(new BigDecimal(45_000));
        score4.setCardNumber(4444_4444_4444_4444L);

        scoresUser1 = new HashSet<>();
        scoresUser2 = new HashSet<>();
        scoresUser1.add(score1);
        scoresUser1.add(score2);
        scoresUser2.add(score3);
        scoresUser2.add(score4);
        // init User1
        user1 = new User();
        user1.setId(1);
        user1.setName("Vlad");
        user1.setScores(scoresUser1);
        // init User2
        user2 = new User();
        user2.setId(2);
        user2.setName("Alex");
        user2.setScores(scoresUser2);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader br = null;
        String jsonString = "";

        try {
            objectMapper.writeValue(fileUserRepository.getRepository(), users);
            br = new BufferedReader(new FileReader(fileUserRepository.getRepository()));
            jsonString = br.readLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(jsonString.contains("\"cardNumber\":1111111111111111,\"amount\":10000"));
    }

    @Test
    @DisplayName("Получение счета (Score) с сервера на банкомат через JSON")
    void getScoreAmountFromServerJsonTest() {
        // init server
        BankServer bankServer = new BankServer();
        bankServer.setUserRepository(fileUserRepository);
        // init cashMachine
        CashMachine cashMachine = new CashMachine(bankServer);
        // init card
        Card card = new Card();
        card.setNumber(3333_3333_3333_3333L);
        // put card in cashMachine
        cashMachine.insertCard(card);

        Assertions.assertEquals(cashMachine.getCard().getScore().getAmount(), new BigDecimal(35000));
    }
}
