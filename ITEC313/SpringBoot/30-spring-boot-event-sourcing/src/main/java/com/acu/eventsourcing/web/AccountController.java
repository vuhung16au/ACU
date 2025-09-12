package com.acu.eventsourcing.web;

import com.acu.eventsourcing.service.AccountCommandService;
import com.acu.eventsourcing.service.AccountQueryService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@Validated
public class AccountController {
    private final AccountCommandService commands;
    private final AccountQueryService queries;

    public AccountController(AccountCommandService commands, AccountQueryService queries) {
        this.commands = commands;
        this.queries = queries;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> open(@RequestParam @NotBlank String ownerName,
                                                    @RequestParam @Positive BigDecimal initialBalance) {
        String id = commands.openAccount(ownerName, initialBalance);
        return ResponseEntity.ok(Map.of("accountId", id));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable String id, @RequestParam @Positive BigDecimal amount) {
        commands.deposit(id, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable String id, @RequestParam @Positive BigDecimal amount) {
        commands.withdraw(id, amount);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Map<String, Object>> balance(@PathVariable String id) {
        return ResponseEntity.ok(Map.of("accountId", id, "balance", queries.currentBalance(id)));
    }
}


