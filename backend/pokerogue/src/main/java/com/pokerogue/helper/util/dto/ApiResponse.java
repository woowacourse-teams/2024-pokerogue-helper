package com.pokerogue.helper.util.dto;

public record ApiResponse<T>(String message, T data) {
}
