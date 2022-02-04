package com.sbrf.reboot.serialize.utils.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbrf.reboot.serialize.utils.Score;
import com.sbrf.reboot.serialize.utils.host.BankServer;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

//----- банкомат
@Data
public class CashMachine {
    // init plug card
    private static Card emptyCard = new Card();
    private final UUID machineId;
    private Card card = emptyCard;
    private BankServer bankServer;
    private File scoreFile = new File("score.json");


    public CashMachine(BankServer bankServer) {
        machineId = UUID.randomUUID();
        this.bankServer = bankServer;
    }

    public boolean insertCard(Card card) {
        if (this.card.equals(emptyCard)) {
            this.card = card;
            this.getScoreFromServerJson();
            return true;
        } else {
            System.out.println("Картоприемник занят: требуется извлечь предыдущую карту");
            return false;
        }
    }

    public Card extractCard() {
        Card cardOut = card;
        card = emptyCard;
        return cardOut;
    }

    private void getScoreFromServerJson() {
        Score scoreFromServer = null;
        bankServer.getScoreByCardNumberJson(card.getNumber());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            scoreFromServer = objectMapper.readValue(scoreFile, Score.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        card.setScore(scoreFromServer);
    }

}
