package idat.pcds2.grupo3.sistemavigeeks.controllers;



import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import idat.pcds2.grupo3.sistemavigeeks.DTO.OrdeDTO;

import idat.pcds2.grupo3.sistemavigeeks.services.PaypalService;

@Controller
@RequestMapping("/")
public class PaypalController {

    private PaypalService paypalService;

    public PaypalController(PaypalService paypalService) {
        this.paypalService = paypalService;
    }

    public static final String SUCCESS_URL = "/pay/success";
    public static final String CANCEL_URL = "/pay/cancel";

    @GetMapping("/pay/pasarela")
    public String pasarela() {
        return "Pago/pasarela";
    }

    @PostMapping("/pay/pasarela")
    public String payment(@ModelAttribute("order") OrdeDTO orderDTO) {
        try {
            Payment payment = paypalService.createPayment(
                orderDTO.getPrice(), 
                orderDTO.getCurrency(), 
                orderDTO.getMethod(),
                orderDTO.getIntent(), 
                orderDTO.getDescription(), 
                "http://localhost:8080" + CANCEL_URL,
                "http://localhost:8080" + SUCCESS_URL
            );
            for (Links link : payment.getLinks()) {
                if ("approval_url".equals(link.getRel())) {
                    return "redirect:" + link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return "redirect:/pay/pasarela";
    }

    @GetMapping("/pay/cancel")
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping("/pay/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if ("approved".equals(payment.getState())) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/cart/view";
    }
}

