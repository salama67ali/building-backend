package com.computing.BuildingsPermitted.dto;

import jakarta.validation.constraints.NotBlank;

public class DecisionRequest {
    @NotBlank
    private String decision; // APPROVE or REJECT
    private String notes;

    public String getDecision() { return decision; }
    public void setDecision(String decision) { this.decision = decision; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
