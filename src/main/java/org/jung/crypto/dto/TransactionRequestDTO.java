package org.jung.crypto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jung.crypto.domain.TransactionType;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {
    private String symbol;
    private Double amount;
    private TransactionType type;
}
