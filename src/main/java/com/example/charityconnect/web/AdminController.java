package com.example.charityconnect.web;

import com.example.charityconnect.repo.CampaignRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final CampaignRepository campaigns;
    public AdminController(CampaignRepository campaigns) { this.campaigns = campaigns; }

    @PostMapping("/campaigns/{id}/archive")
    public void archive(@PathVariable Long id) {
        var c = campaigns.findById(id).orElseThrow();
        c.setArchived(true);
        campaigns.save(c);
    }
}
