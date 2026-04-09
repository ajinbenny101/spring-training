package com.training.banking;

/**
 * Enum representing the types of bank accounts available.
 *
 * Think of this like a vending machine with exactly three buttons.
 * You can only press SAVINGS, CURRENT, or ISA - nothing else.
 * If someone tries to press a button that doesn't exist, the machine rejects it.
 */
public enum AccountType {
    SAVINGS,
    CURRENT,
    ISA
}
