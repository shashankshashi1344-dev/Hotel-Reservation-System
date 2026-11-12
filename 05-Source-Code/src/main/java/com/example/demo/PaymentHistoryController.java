
package com.hotel.hotelreservation;

import com.hotel.hotelreservation.repository.PaymentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentHistoryController {

    private final PaymentRepository paymentRepository;

    public PaymentHistoryController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/payment-history")
    public String showPaymentHistory(Model model) {
        model.addAttribute(
            "payments",
            paymentRepository.findAll(
                Sort.by(Sort.Direction.DESC, "paymentId")
            )
        );

        return "payment-history";
    }
}
