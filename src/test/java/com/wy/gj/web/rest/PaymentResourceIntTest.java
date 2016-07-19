package com.wy.gj.web.rest;

import com.wy.gj.CgApp;
import com.wy.gj.domain.Payment;
import com.wy.gj.repository.PaymentRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PaymentResource REST controller.
 *
 * @see PaymentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CgApp.class)
@WebAppConfiguration
@IntegrationTest
public class PaymentResourceIntTest {


    private static final Long DEFAULT_PAYMENT_ID = 1L;
    private static final Long UPDATED_PAYMENT_ID = 2L;

    private static final Long DEFAULT_PAYMENT_TIME = 1L;
    private static final Long UPDATED_PAYMENT_TIME = 2L;

    private static final Long DEFAULT_PAYMENT_AMOUNT = 1L;
    private static final Long UPDATED_PAYMENT_AMOUNT = 2L;
    private static final String DEFAULT_PAYMENT_STAFF = "AAAAA";
    private static final String UPDATED_PAYMENT_STAFF = "BBBBB";
    private static final String DEFAULT_PAYMENT_MODE = "AAAAA";
    private static final String UPDATED_PAYMENT_MODE = "BBBBB";

    @Inject
    private PaymentRepository paymentRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPaymentMockMvc;

    private Payment payment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PaymentResource paymentResource = new PaymentResource();
        ReflectionTestUtils.setField(paymentResource, "paymentRepository", paymentRepository);
        this.restPaymentMockMvc = MockMvcBuilders.standaloneSetup(paymentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        payment = new Payment();
        payment.setPaymentId(DEFAULT_PAYMENT_ID);
        payment.setPaymentTime(DEFAULT_PAYMENT_TIME);
        payment.setPaymentAmount(DEFAULT_PAYMENT_AMOUNT);
        payment.setPaymentStaff(DEFAULT_PAYMENT_STAFF);
        payment.setPaymentMode(DEFAULT_PAYMENT_MODE);
    }

    @Test
    @Transactional
    public void createPayment() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // Create the Payment

        restPaymentMockMvc.perform(post("/api/payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payment)))
                .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> payments = paymentRepository.findAll();
        assertThat(payments).hasSize(databaseSizeBeforeCreate + 1);
        Payment testPayment = payments.get(payments.size() - 1);
        assertThat(testPayment.getPaymentId()).isEqualTo(DEFAULT_PAYMENT_ID);
        assertThat(testPayment.getPaymentTime()).isEqualTo(DEFAULT_PAYMENT_TIME);
        assertThat(testPayment.getPaymentAmount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
        assertThat(testPayment.getPaymentStaff()).isEqualTo(DEFAULT_PAYMENT_STAFF);
        assertThat(testPayment.getPaymentMode()).isEqualTo(DEFAULT_PAYMENT_MODE);
    }

    @Test
    @Transactional
    public void getAllPayments() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the payments
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
                .andExpect(jsonPath("$.[*].paymentId").value(hasItem(DEFAULT_PAYMENT_ID.intValue())))
                .andExpect(jsonPath("$.[*].paymentTime").value(hasItem(DEFAULT_PAYMENT_TIME.intValue())))
                .andExpect(jsonPath("$.[*].paymentAmount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].paymentStaff").value(hasItem(DEFAULT_PAYMENT_STAFF.toString())))
                .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())));
    }

    @Test
    @Transactional
    public void getPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(payment.getId().intValue()))
            .andExpect(jsonPath("$.paymentId").value(DEFAULT_PAYMENT_ID.intValue()))
            .andExpect(jsonPath("$.paymentTime").value(DEFAULT_PAYMENT_TIME.intValue()))
            .andExpect(jsonPath("$.paymentAmount").value(DEFAULT_PAYMENT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.paymentStaff").value(DEFAULT_PAYMENT_STAFF.toString()))
            .andExpect(jsonPath("$.paymentMode").value(DEFAULT_PAYMENT_MODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayment() throws Exception {
        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment
        Payment updatedPayment = new Payment();
        updatedPayment.setId(payment.getId());
        updatedPayment.setPaymentId(UPDATED_PAYMENT_ID);
        updatedPayment.setPaymentTime(UPDATED_PAYMENT_TIME);
        updatedPayment.setPaymentAmount(UPDATED_PAYMENT_AMOUNT);
        updatedPayment.setPaymentStaff(UPDATED_PAYMENT_STAFF);
        updatedPayment.setPaymentMode(UPDATED_PAYMENT_MODE);

        restPaymentMockMvc.perform(put("/api/payments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPayment)))
                .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> payments = paymentRepository.findAll();
        assertThat(payments).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = payments.get(payments.size() - 1);
        assertThat(testPayment.getPaymentId()).isEqualTo(UPDATED_PAYMENT_ID);
        assertThat(testPayment.getPaymentTime()).isEqualTo(UPDATED_PAYMENT_TIME);
        assertThat(testPayment.getPaymentAmount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
        assertThat(testPayment.getPaymentStaff()).isEqualTo(UPDATED_PAYMENT_STAFF);
        assertThat(testPayment.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
    }

    @Test
    @Transactional
    public void deletePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        int databaseSizeBeforeDelete = paymentRepository.findAll().size();

        // Get the payment
        restPaymentMockMvc.perform(delete("/api/payments/{id}", payment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Payment> payments = paymentRepository.findAll();
        assertThat(payments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
