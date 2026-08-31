package com.fintech.cashit.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class OrderRequestDTO {

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z]{3}$",
            message = "Currency must be a 3-letter ISO code, for example INR")
    private String currency;

    private String description;

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getDescription() { return description; }
    public void setDescription(String description) {
        this.description = description;
    }
}