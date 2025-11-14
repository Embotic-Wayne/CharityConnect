package com.example.charityconnect.web;

import com.example.charityconnect.domain.Campaign;
import com.example.charityconnect.repo.CampaignRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
class AdminControllerWebTest {

    @Autowired MockMvc mvc;

    @MockitoBean CampaignRepository campaigns;

    @Test
    @WithMockUser(roles = "ADMIN")
    void archive_marks_campaign_archived_and_saves() throws Exception {
        Campaign c = new Campaign();
        c.setId(1L);
        c.setTitle("T");
        c.setGoalAmount(BigDecimal.TEN);
        c.setRaisedAmount(BigDecimal.ZERO);
        c.setStartDate(LocalDate.now());
        when(campaigns.findById(1L)).thenReturn(Optional.of(c));

        mvc.perform(post("/admin/campaigns/1/archive"))
                .andExpect(status().isOk());

        verify(campaigns).save(argThat(cc -> Boolean.TRUE.equals(cc.isArchived())));
    }
}
