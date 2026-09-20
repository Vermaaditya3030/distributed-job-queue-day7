package com.aditya.job;
public record Job(String id,String type,String payload,String status,int attempts,long createdAt,long updatedAt) {}
