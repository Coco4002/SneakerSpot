package com.sneakerspot.model;

/**
 * Enumerare care definește statusurile posibile ale unei comenzi.
 * Urmărește ciclul de viață al unei comenzi în sistem.
 */

public enum OrderStatus {
    PENDING,
    NEGOTIATION,
    CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
