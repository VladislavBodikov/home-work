package com.sbrf.reboot;

import com.sbrf.reboot.repository.AccountRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class AccountService {

    @NonNull
    private AccountRepository accountRepository;

    public boolean isClientHasContract(long clientId, long contractNumber) {
        return accountRepository.getAllAccountsByClientId(clientId).contains(contractNumber);
    }

    public long getClientIdByContractNumber(long contractNumber) {
        Set<Long> allAccounts = accountRepository.getAllAccounts();
        return allAccounts
                .stream()
                .filter((x) -> accountRepository.getAllAccountsByClientId(x).contains(contractNumber))
                .findFirst()
                .get();
    }
}
