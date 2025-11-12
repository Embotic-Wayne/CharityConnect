package com.example.charityconnect.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
public class Donation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  private Campaign campaign;

  @ManyToOne(optional = false)
  private User donor;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(nullable = false)
  private Instant createdAt;

  public Donation() {}

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Campaign getCampaign() { return campaign; }
  public void setCampaign(Campaign campaign) { this.campaign = campaign; }

  public User getDonor() { return donor; }
  public void setDonor(User donor) { this.donor = donor; }

  public BigDecimal getAmount() { return amount; }
  public void setAmount(BigDecimal amount) { this.amount = amount; }

  public Instant getCreatedAt() { return createdAt; }
  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
