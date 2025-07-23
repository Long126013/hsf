package com.example.demo.service;

import com.example.demo.model.Receipt;
import com.example.demo.model.ReceiptItem;
import com.example.demo.model.User;
import com.example.demo.repository.ReceiptRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final UserRepository userRepository;

    public Receipt saveReceipt(Receipt receipt) {
        return receiptRepository.save(receipt);
    }

    public List<Receipt> getReceiptsByUser(User user) {
        return receiptRepository.findByUser(user);
    }

    public List<Receipt> getReceiptsByUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return receiptRepository.findByUser(user);
    }

    public Receipt getReceiptById(UUID id) {
        return receiptRepository.findById(id).orElse(null);
    }
} 