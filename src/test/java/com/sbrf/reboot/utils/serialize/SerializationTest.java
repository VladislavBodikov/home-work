package com.sbrf.reboot.utils.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sbrf.reboot.serialize.utils.Score;
import com.sbrf.reboot.serialize.utils.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SerializationTest {
    static Set<Score> scores = new HashSet<>();
    static User user;
    static File userJson = new File("user.json");
    static File userXml = new File("user.xml");

    @BeforeEach
    void setUser() {
        // init Scores
        Score score1 = new Score();
        score1.setAmount(new BigDecimal(10_000));
        Score score2 = new Score();
        score2.setAmount(new BigDecimal(15_000));

        scores = new HashSet<>();
        scores.add(score1);
        scores.add(score2);
        // init User
        user = new User();
        user.setId(1);
        user.setName("Vlad");
        // add scores to user
        user.setScores(scores);
    }

    @Test
    @DisplayName("Cериализация - JSON")
    void serializeUserJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader br = null;
        String strFromFile = "";
        try {
            // write object to file
            objectMapper.writeValue(userJson, user);
            // read string from file
            br = new BufferedReader(new FileReader(userJson));
            strFromFile = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assertions.assertTrue(strFromFile.contains("\"name\":\"Vlad\""));
    }

    @Test
    @DisplayName("Десериализация - JSON")
    void deserializeUserJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            user = objectMapper.readValue(userJson, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String userName = user.getName();

        // filter to get scores with amount >= than 13_000
        List<Score> scoresGreaterThan13000 = user.getScores().stream()
                .filter((s) -> s.compareAmount(13_000) > 0)
                .collect(Collectors.toList());

        Assertions.assertAll(
                () -> Assertions.assertEquals(userName, "Vlad"),
                () -> Assertions.assertEquals(scoresGreaterThan13000.get(0).getAmount(), new BigDecimal(15000))
        );
    }

    @Test
    @DisplayName("Cериализация - XML")
    void serializeUserXML() {
        XmlMapper xmlMapper = new XmlMapper();
        BufferedReader br = null;
        String strFromFile = "";

        try {
            // write object to file
            xmlMapper.writeValue(userXml, user);
            // read string from file
            br = new BufferedReader(new FileReader(userXml));
            strFromFile = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(strFromFile.contains("<amount>15000</amount>"));
    }

    @Test
    @DisplayName("Десериализация - XML")
    void deserializeUserXml() {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            user = xmlMapper.readValue(userXml, User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String userName = user.getName();

        // filter to get scores with amount more than 13_000
        List<Score> scoresGreaterThan13000 = user.getScores().stream()
                .filter((s) -> s.compareAmount(13_000) > 0)
                .collect(Collectors.toList());

        Assertions.assertAll(
                () -> Assertions.assertEquals(userName, "Vlad"),
                () -> Assertions.assertEquals(scoresGreaterThan13000.get(0).getAmount(), new BigDecimal(15000))
        );
    }
}
