package com.example.charityconnect.web;

import com.example.charityconnect.domain.Campaign;
import com.example.charityconnect.repo.CampaignRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc   // Keep security filters; @WithMockUser will satisfy RBAC
class CampaignControllerWebTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @MockitoBean CampaignRepository campaigns;

    @Test
    void list_public_ok() throws Exception {
        Campaign c = new Campaign();
        c.setId(1L); c.setTitle("A");
        c.setGoalAmount(BigDecimal.TEN);
        c.setRaisedAmount(BigDecimal.ZERO);
        c.setStartDate(LocalDate.now());
        when(campaigns.findByArchivedFalse()).thenReturn(List.of(c));

        mvc.perform(get("/campaigns"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("A"));
    }

    @Test
    void get_by_id_public_ok() throws Exception {
        Campaign c = new Campaign();
        c.setId(99L); c.setTitle("X");
        c.setGoalAmount(BigDecimal.ONE);
        c.setRaisedAmount(BigDecimal.ZERO);
        c.setStartDate(LocalDate.now());
        when(campaigns.findById(99L)).thenReturn(Optional.of(c));

        mvc.perform(get("/campaigns/99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("X"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void create_user_only_sets_defaults() throws Exception {
        Campaign req = new Campaign();
        req.setTitle("New");
        req.setGoalAmount(new BigDecimal("5000"));

        when(campaigns.save(any(Campaign.class))).thenAnswer(inv -> {
            Campaign saved = inv.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        mvc.perform(post("/campaigns")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        ArgumentCaptor<Campaign> cap = ArgumentCaptor.forClass(Campaign.class);
        verify(campaigns).save(cap.capture());
        var saved = cap.getValue();
        assert saved.getRaisedAmount() != null && saved.getRaisedAmount().compareTo(BigDecimal.ZERO)==0;
        assert saved.getStartDate() != null;
    }
}
