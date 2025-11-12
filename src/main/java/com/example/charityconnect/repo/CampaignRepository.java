package com.example.charityconnect.repo;

import com.example.charityconnect.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByArchivedFalse();
}