package com.example.jakartaee.domain.values;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    CREATED, REQUESTED, APPROVED, CANCELED, STARTED, FINISHED, CLOSED
}
