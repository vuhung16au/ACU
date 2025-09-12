package com.acu.eventsourcing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountE2ETest {
    @Autowired
    private TestRestTemplate rest;

    @Test
    void openDepositWithdrawAndQueryBalance() {
        ResponseEntity<Map> open = rest.postForEntity("/api/accounts?ownerName=Alice&initialBalance=100", null, Map.class);
        assertThat(open.getStatusCode().is2xxSuccessful()).isTrue();
        String id = (String) open.getBody().get("accountId");

        rest.postForEntity("/api/accounts/" + id + "/deposit?amount=50", null, Void.class);
        rest.postForEntity("/api/accounts/" + id + "/withdraw?amount=20", null, Void.class);

        ResponseEntity<Map> balance = rest.getForEntity("/api/accounts/" + id + "/balance", Map.class);
        assertThat(new BigDecimal(balance.getBody().get("balance").toString())).isEqualByComparingTo("130");
    }
}


