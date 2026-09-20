package com.aditya.job;
import jakarta.validation.constraints.NotBlank;
public record CreateJobRequest(@NotBlank String type,@NotBlank String payload) {}
