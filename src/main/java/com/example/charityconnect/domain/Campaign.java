package com.example.charityconnect.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Campaign {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  private String description;

  @Column(nullable = false)
  private BigDecimal goalAmount;

  @Column(nullable = false)
  private BigDecimal raisedAmount = BigDecimal.ZERO;

  @Column(nullable = false)
  private LocalDate startDate;

  private LocalDate endDate;

  private boolean archived;

  public Campaign() {}

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public BigDecimal getGoalAmount() { return goalAmount; }
  public void setGoalAmount(BigDecimal goalAmount) { this.goalAmount = goalAmount; }

  public BigDecimal getRaisedAmount() { return raisedAmount; }
  public void setRaisedAmount(BigDecimal raisedAmount) { this.raisedAmount = raisedAmount; }

  public LocalDate getStartDate() { return startDate; }
  public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

  public LocalDate getEndDate() { return endDate; }
  public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

  public boolean isArchived() { return archived; }
  public void setArchived(boolean archived) { this.archived = archived; }
}
