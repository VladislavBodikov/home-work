package com.sbrf.reboot.repository.impl;

import com.sbrf.reboot.repository.AccountRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FileAccountRepository implements AccountRepository {

    private final String pathToRepo;

    public FileAccountRepository(String pathToRepo) {
        this.pathToRepo = pathToRepo;
    }

    @Override
    public Set<Long> getAllAccountsByClientId(long clientId) throws IOException {
        // read file to StringBuilder
        String sb = readFileToString(pathToRepo);

        // parse clientsId and numbers from StringBuilder(Accounts.txt)
        List<String> clientsId = new ArrayList<>();
        List<String> numbers = new ArrayList<>();
        String[] array = sb.split("\n");
        for (int i = 0; i < array.length; i++) {
            if (array[i].contains("clientId")) {
                clientsId.add(array[i].trim());
            }
            if (array[i].contains("number")) {
                numbers.add(array[i].trim());
            }
        }
        // find this client
        return findNumbersByClientId(clientsId, numbers, clientId);
    }

    private String readFileToString(String pathToFile) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(pathToFile));
        while (bufferedReader.ready()) {
            sb.append(bufferedReader.readLine()).append("\n");
        }
        return sb.toString();
    }

    private Set<Long> findNumbersByClientId(List<String> clients, List<String> numbers, long targetClientId) {
        String client = String.format("\"clientId\": %d", targetClientId);
        Set<Long> numbersForClientId = new HashSet<>();
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).contains(client)) {
                long num = Long.parseLong(numbers.get(i).split(" ")[1]);
                numbersForClientId.add(num);
            }
        }
        return numbersForClientId;
    }

    public void updateAccountNumber(Long clientId, Long previousNumber, Long newNumber) throws IOException {
        String strClient = String.format("\"clientId\": %d,", clientId);
        String strNumber = String.format("\"number\": %d", previousNumber);
        String findStr = "    " + strClient + "\n" + "    " + strNumber;
        String replaceStr = "    " + strClient + "\n" + "    " + String.format("\"number\": %d", newNumber);

        String data = readFileToString(pathToRepo);
        data = data.replace(findStr, replaceStr);

        try(
            FileWriter fw = new FileWriter(new File(pathToRepo), false);
            BufferedWriter bw = new BufferedWriter(fw);
        ){
            bw.write(data);
        }
    }

}
