package com.example.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "There is no such available coin at the moment")
public class CoinNotFoundException extends Throwable {
}


