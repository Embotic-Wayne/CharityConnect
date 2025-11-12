package com.example.charityconnect.web;

import com.example.charityconnect.domain.Campaign;
import com.example.charityconnect.repo.CampaignRepository;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {
    private final CampaignRepository campaigns;
    public CampaignController(CampaignRepository campaigns) { this.campaigns = campaigns; }

    @GetMapping
    public List<Campaign> list() { return campaigns.findByArchivedFalse(); }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public Campaign create(@Valid @RequestBody Campaign c) {
        c.setId(null);
        c.setRaisedAmount(BigDecimal.ZERO);
        if (c.getStartDate() == null) c.setStartDate(LocalDate.now());
        return campaigns.save(c);
    }

    @GetMapping("/{id}")
    public Campaign get(@PathVariable Long id) { return campaigns.findById(id).orElseThrow(); }
}
