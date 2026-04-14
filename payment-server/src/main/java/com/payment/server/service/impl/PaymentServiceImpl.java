package com.payment.server.service.impl;

import com.payment.server.domain.PaymentMethod;
import com.payment.server.domain.PaymentOrderStatus;
import com.payment.server.dto.BookingDto;
import com.payment.server.dto.UserDto;
import com.payment.server.dto.UserResponseClientDto;
import com.payment.server.entity.Payment;
import com.payment.server.globalExceptionHandler.CustomException;
import com.payment.server.payloadResponseStructure.PaymentLinkResponse;
import com.payment.server.repository.PaymentRepository;
import com.payment.server.service.PaymentService;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.model.checkout.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RazorpayClient razorpayClient;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public PaymentLinkResponse createOrderAndGenerateNewPayment(UserResponseClientDto user, BookingDto booking, PaymentMethod paymentMethod) {

        Long amount = (long) booking.getTotalPrice();
        Payment createPaymentOrder = new Payment();

        createPaymentOrder.setUserId(user.getId());
        createPaymentOrder.setBookingId(booking.getId());
        createPaymentOrder.setSalonId(booking.getSalonId());
        createPaymentOrder.setAmount(amount);
        createPaymentOrder.setPaymentMethod(paymentMethod);

        Payment saveNewPaymentOrder = paymentRepository.save(createPaymentOrder);

        PaymentLinkResponse paymentLinkResponse = new PaymentLinkResponse();

        if (paymentMethod.equals(PaymentMethod.RAZORPAY)) {
            PaymentLink paymentLink = createRazorpayPaymentLink(user, saveNewPaymentOrder.getAmount(), saveNewPaymentOrder.getId());

            String paymentUrl = paymentLink.get("short_url");
            String paymentUrlId = paymentLink.get("id");

            paymentLinkResponse.setPayment_link_url(paymentUrl);
            paymentLinkResponse.setGetPayment_link_id(paymentUrlId);

            saveNewPaymentOrder.setPaymentLinkId(paymentUrlId);
            paymentRepository.save(saveNewPaymentOrder);
        } else if (paymentMethod.equals(PaymentMethod.STRIPE)) {
            String paymentUrl = createStripePaymentLink(user, saveNewPaymentOrder.getAmount(), saveNewPaymentOrder.getId());
            paymentLinkResponse.setPayment_link_url(paymentUrl);

        }
        return paymentLinkResponse;
    }

    @Override
    public Payment getSinglePaymentById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new CustomException("Payment order not found"));
    }

    @Override
    public Payment getSinglePaymentByPaymentId(String paymentId) {
        return paymentRepository.findByPaymentLinkId(paymentId);
    }

    @Override
    public PaymentLink createRazorpayPaymentLink(UserResponseClientDto user, Long amount, Long orderId) {

        try {
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount * 100);
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("description", "Booking payment for order " + orderId);

            JSONObject customer = new JSONObject();
            customer.put("name", user.getFullName());
            customer.put("email", user.getEmail());
            customer.put("sms", user.getPhone());
            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("email", true);
            notify.put("sms", true);
            paymentLinkRequest.put("notify", notify);
            paymentLinkRequest.put("reminder_enable", true);

            paymentLinkRequest.put("callback_url", "https://sanjeev-razor-pay.free.beeceptor.com");
            paymentLinkRequest.put("callback_method", "get");
           return razorpayClient.paymentLink.create(paymentLinkRequest);

        } catch (Exception e) {
            throw new RuntimeException("Error creating Razorpay payment link: " + e.getMessage());
        }
    }

    @Override
    public String createStripePaymentLink(UserResponseClientDto user, Long amount, Long orderId) {
        try {
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)

                            // Success & Cancel URLs
                            .setSuccessUrl("https://sanjeev-razor-pay.free.beeceptor.com" + orderId)
                            .setCancelUrl("https://abc.free.beeceptor.com/payment-callback")

                            // Customer email
                            .setCustomerEmail(user.getEmail())

                            // Line item (product)
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setQuantity(1L)
                                            .setPriceData(
                                                    SessionCreateParams.LineItem.PriceData.builder()
                                                            .setCurrency("inr")
                                                            .setUnitAmount(amount * 100) // paisa
                                                            .setProductData(
                                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                            .setName("Salon Booking - Order " + orderId)
                                                                            .build()
                                                            )
                                                            .build()
                                            )
                                            .build()
                            )
                            .build();

            Session session = Session.create(params);
            return session.getUrl();
        } catch (Exception e) {
            throw new RuntimeException("Error creating Stripe payment link: " + e.getMessage());
        }
    }

    @Override
    public Boolean proceedPayment(Payment paymentOrder, String paymentId, String paymentLinkId) throws RazorpayException {

        if (paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {
            if (paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)) {
                com.razorpay.Payment payment = razorpayClient.payments.fetch(paymentId);
                Integer amount = payment.get("amount");
                String status = payment.get("status");

                if (status.equals("captured")) {
                    paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                    paymentRepository.save(paymentOrder);
                    return true;
                }
                return false;

            } else if (paymentOrder.getPaymentMethod().equals(PaymentMethod.STRIPE)) {
                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentRepository.save(paymentOrder);
                return true;
            }
        }
        return false;
    }


}
