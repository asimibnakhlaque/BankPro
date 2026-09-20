package com.screenprog.application.service;

import com.screenprog.application.dtos.TransferDTO;
import com.screenprog.application.model.Account;
import com.screenprog.application.model.DebitCard;
import com.screenprog.application.repo.AccountRepository;
import com.screenprog.application.repo.TransactionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class  UserServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionsRepository transactionsRepository;

    // We don't need other repos for this specific test
    // But UserService constructor needs them, so we might need to mock them if Constructor Injection is used strict
    // Mockito @InjectMocks tries to handle this best effort.

    @InjectMocks
    private UserService userService;

    // Since your BCryptEncryption.encoder is static, we can't easily mock it.
    // However, if we set the pin in the card to be the ENCODED version of the input pin,
    // and the static encoder works as expected, it should match.
    // CAUTION: Unit testing with static dependencies is an anti-pattern (which you have in your code).
    // For this test, we assume the static block in BCryptEncryption has initialized the encoder.

    private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Test
    void transferAmount_ShouldFail_WhenInsufficientBalance() {
        // Arrange
        Long senderId = 1L;
        Long receiverId = 2L;
        String pin = "1234";
        String encodedPin = encoder.encode(pin); // We rely on real BCrypt for this test setup

        Account sender = new Account();
        sender.setAccountNumber(senderId);
        sender.setBalance(new BigDecimal("150.0000")); // Low balance
        DebitCard senderCard = new DebitCard();
        senderCard.setPin(encodedPin);
        sender.setCard(senderCard);

        Account receiver = new Account();
        receiver.setAccountNumber(receiverId);
        receiver.setBalance(new BigDecimal("100.0000"));

        when(accountRepository.findById(senderId)).thenReturn(Optional.of(sender));
        when(accountRepository.findById(receiverId)).thenReturn(Optional.of(receiver));

        TransferDTO transferDTO = new TransferDTO(senderId, receiverId, new BigDecimal("60.0000"), pin);

        // Act
        // 150 - 60 = 90. Rule says must keep 100. So this should fail.
        String result = userService.transferAmount(transferDTO);

        // Assert
        assertEquals("Insufficient balance", result);
        verify(transactionsRepository, never()).saveAll(any());
    }

    @Test
    void transferAmount_ShouldSucceed_WhenBalanceSufficient() {
        // Arrange
        Long senderId = 1L;
        Long receiverId = 2L;
        String pin = "1234";
        String encodedPin = encoder.encode(pin);

        Account sender = new Account();
        sender.setAccountNumber(senderId);
        sender.setBalance(new BigDecimal("500.0000")); // Healthy balance
        DebitCard senderCard = new DebitCard();
        senderCard.setPin(encodedPin);
        sender.setCard(senderCard);

        Account receiver = new Account();
        receiver.setAccountNumber(receiverId);
        receiver.setBalance(new BigDecimal("100.0000"));

        when(accountRepository.findById(senderId)).thenReturn(Optional.of(sender));
        when(accountRepository.findById(receiverId)).thenReturn(Optional.of(receiver));

        TransferDTO transferDTO = new TransferDTO(senderId, receiverId, new BigDecimal("100.0000"), pin);

        // Act
        // 500 - 100 = 400. 400 > 100. Success.
        String result = userService.transferAmount(transferDTO);

        // Assert
        assertEquals("Transaction Successful", result);
        assertEquals(new BigDecimal("400.0000"), sender.getBalance()); // 500 - 100
        assertEquals(new BigDecimal("200.0000"), receiver.getBalance()); // 100 + 100
    }
}
