package com.example.bankingsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public record TransferRequest(
 @Schema(example = "1") Long sourceAccountId,
 @Schema(example = "2") Long targetAccountId,
 @Schema(example = "500.00") BigDecimal amount
) {}