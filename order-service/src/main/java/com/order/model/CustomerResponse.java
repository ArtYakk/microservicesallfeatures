package com.order.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CustomerResponse {
    private Customer customer;
    private Boolean isError;
    private String errorMessage;
}