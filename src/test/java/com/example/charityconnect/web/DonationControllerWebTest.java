package com.example.charityconnect.web;

import com.example.charityconnect.domain.Campaign;
import com.example.charityconnect.domain.Donation;
import com.example.charityconnect.domain.User;
import com.example.charityconnect.repo.CampaignRepository;
import com.example.charityconnect.repo.DonationRepository;
import com.example.charityconnect.repo.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DonationControllerWebTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @MockitoBean DonationRepository donations;
    @MockitoBean CampaignRepository campaigns;
    @MockitoBean UserRepository users;

    @Test
    @WithMockUser(roles = "USER")
    void donate_updates_campaign_total_and_saves_donation() throws Exception {
        // existing campaign
        Campaign c = new Campaign();
        c.setId(1L);
        c.setTitle("A");
        c.setGoalAmount(new BigDecimal("100"));
        c.setRaisedAmount(BigDecimal.ZERO);
        c.setStartDate(LocalDate.now());

        // existing user
        User u = new User();
        u.setId(10L);
        u.setEmail("user@charity.local");

        when(campaigns.findById(1L)).thenReturn(Optional.of(c));
        when(users.findByEmail("user@charity.local")).thenReturn(Optional.of(u));
        when(donations.save(any(Donation.class))).thenAnswer(inv -> {
            Donation d = inv.getArgument(0);
            d.setId(123L);
            return d;
        });

        String body = """
      {"campaignId":1,"amount":25,"donorEmail":"user@charity.local"}
      """;

        mvc.perform(post("/donations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        // campaign total should be updated and persisted
        ArgumentCaptor<Campaign> cap = ArgumentCaptor.forClass(Campaign.class);
        verify(campaigns, atLeastOnce()).save(cap.capture());
        Campaign saved = cap.getValue();
        assert saved.getRaisedAmount().compareTo(new BigDecimal("25")) == 0;

        // donation persisted
        verify(donations).save(any(Donation.class));
    }
}
