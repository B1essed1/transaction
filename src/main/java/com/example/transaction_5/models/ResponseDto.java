package com.example.transaction_5.models;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private T data;
    private Boolean isError;
    private String message;

}
