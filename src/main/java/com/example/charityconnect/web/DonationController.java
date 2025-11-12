package com.example.charityconnect.web;

import com.example.charityconnect.domain.Donation;
import com.example.charityconnect.repo.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;

@RestController
@RequestMapping("/donations")
public class DonationController {
  private final DonationRepository donations;
  private final CampaignRepository campaigns;
  private final UserRepository users;

  public DonationController(DonationRepository donations, CampaignRepository campaigns, UserRepository users) {
    this.donations = donations; this.campaigns = campaigns; this.users = users;
  }

  record DonateRequest(Long campaignId, BigDecimal amount, String donorEmail) {}

  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public Donation donate(@RequestBody DonateRequest req) {
    var campaign = campaigns.findById(req.campaignId()).orElseThrow();
    var donor = users.findByEmail(req.donorEmail()).orElseThrow();

    var d = new Donation();
    d.setCampaign(campaign);
    d.setDonor(donor);
    d.setAmount(req.amount());
    d.setCreatedAt(Instant.now());

    campaign.setRaisedAmount(campaign.getRaisedAmount().add(req.amount()));
    campaigns.save(campaign);

    return donations.save(d);
  }
}
