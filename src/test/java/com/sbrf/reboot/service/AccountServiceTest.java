package com.sbrf.reboot.service;

import com.sbrf.reboot.AccountService;
import com.sbrf.reboot.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = Mockito.mock(AccountRepository.class);

        accountService = new AccountService(accountRepository);
    }

    @Test
    void contractExist() {
        Set<Long> accounts = new HashSet();
        accounts.add(111L);

        long clientId = 1L;
        long contractNumber = 111L;


        when(accountRepository.getAllAccountsByClientId(clientId)).thenReturn(accounts);

        assertTrue(accountService.isClientHasContract(clientId, contractNumber));
    }

    @Test
    void contractNotExist() {
        Set<Long> accounts = new HashSet();
        accounts.add(222L);

        long clientId = 1L;
        long contractNumber = 111L;

        when(accountRepository.getAllAccountsByClientId(clientId)).thenReturn(accounts);

        assertFalse(accountService.isClientHasContract(clientId, contractNumber));
    }

    @Test
    void foundClientIdByContractNumber() {
        // all accounts
        Set<Long> accounts = new HashSet();
        accounts.add(222L);
        accounts.add(99L);
        // contracts of 222L account
        Set<Long> contactsOf222Account = new HashSet<>();
        contactsOf222Account.add(222_100L);
        contactsOf222Account.add(222_300L);
        // contracts of 99L account
        Set<Long> contactsOf99Account = new HashSet<>();
        contactsOf99Account.add(99_200L);
        contactsOf99Account.add(99_400L);

        when(accountRepository.getAllAccounts()).thenReturn(accounts);
        when(accountRepository.getAllAccountsByClientId(222L)).thenReturn(contactsOf222Account);
        when(accountRepository.getAllAccountsByClientId(99L)).thenReturn(contactsOf99Account);

        assertEquals(99L, accountService.getClientIdByContractNumber(99_200L));
    }

    @Test
    void repositoryHasTreeMethods() {
        assertEquals(2, AccountRepository.class.getMethods().length);
    }

    @Test
    void serviceHasTreeMethods() {
        assertEquals(2, AccountService.class.getMethods().length - Object.class.getMethods().length);
    }

}