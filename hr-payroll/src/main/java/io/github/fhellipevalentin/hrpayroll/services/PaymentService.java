package io.github.fhellipevalentin.hrpayroll.services;

import io.github.fhellipevalentin.hrpayroll.entities.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public Payment getPayment(long workerId, int days) {
        return new Payment("Bob", 200.0, days);
    }
}