package nightfort.api.v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nightfort.api.v1.model.Profile;
import nightfort.api.v1.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProfileService profileService;
    
    private Profile profile1;
    private Profile profile2;
    
    @BeforeEach
    void setup() {
        profile1 = new Profile();
        profile1.setId(1L);
        profile1.setDisplayName("Profile1");
        
        profile2 = new Profile();
        profile2.setId(2L);
        profile2.setDisplayName("Profile2");
    }
    
    @Test
    void getAllProfiles() throws Exception {
        when(profileService.getAllProfiles()).thenReturn(Arrays.asList(profile1, profile2));
        
        mockMvc.perform(get("/api/v1/profile")
                    .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$", hasSize(2)))
              .andExpect(jsonPath("$[0].id").value(profile1.getId()))
              .andExpect(jsonPath("$[0].displayName").value(profile1.getDisplayName()))
              .andExpect(jsonPath("$[1].id").value(profile2.getId()))
              .andExpect(jsonPath("$[1].displayName").value(profile2.getDisplayName()));
        
        verify(profileService, times(1)).getAllProfiles();
    }
    
    @Test
    void getProfileById() throws Exception {
        when(profileService.getProfile(anyLong())).thenReturn(profile1);
        
        mockMvc.perform(get("/api/v1/profile/1")
                    .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(profile1.getId()))
              .andExpect(jsonPath("$.displayName").value(profile1.getDisplayName()));
        
        verify(profileService, times(1)).getProfile(1L);
    }
    
    @Test
    void createProfile() throws Exception {
        when(profileService.createProfile(any(Profile.class))).thenReturn(profile1);
        
        mockMvc.perform(post("/api/v1/profile")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(profile1)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(profile1.getId()))
              .andExpect(jsonPath("$.displayName").value(profile1.getDisplayName()));
        
        verify(profileService, times(1)).createProfile(any(Profile.class));
    }
    
    @Test
    void updateProfile() throws Exception {
        when(profileService.updateProfile(anyLong(), any(Profile.class))).thenReturn(profile1);
        
        mockMvc.perform(put("/api/v1/profile/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(profile1)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.id").value(profile1.getId()))
              .andExpect(jsonPath("$.displayName").value(profile1.getDisplayName()));
        
        verify(profileService, times(1)).updateProfile(anyLong(), any(Profile.class));
    }
    
    @Test
    void deleteProfile() throws Exception {
        doNothing().when(profileService).deleteProfile(anyLong());
        
        mockMvc.perform(delete("/api/v1/profile/1")
                    .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
        
        verify(profileService, times(1)).deleteProfile(anyLong());
    }
}
