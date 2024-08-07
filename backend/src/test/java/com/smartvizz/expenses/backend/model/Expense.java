package com.smartvizz.expenses.backend.model;

public class Expense {
    private Long id;
    private String title;
    private String description;
    private double amount;
    private String currency;

    // Default constructor, getters, and setters
    public Expense() {
    }

    public Expense(String title, String description, double amount, String currency) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
