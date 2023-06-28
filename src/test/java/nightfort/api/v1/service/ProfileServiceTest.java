package nightfort.api.v1.service;

import nightfort.api.v1.model.Profile;
import nightfort.api.v1.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProfileServiceTest {

    @MockBean
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Test
    void testGetProfile() {
        // TODO
        // Given
        String profileName = "mock-profile";
        Long profileId = 1L;
        Profile mockProfile = new Profile();
        mockProfile.setDisplayName(profileName);

        when(profileRepository.findById(anyLong())).thenReturn(Optional.of(mockProfile));

        // When & Then
        Profile profile = profileService.getProfile(1L);
        assertNotNull(profile);

        assertEquals(mockProfile, profile);
    }

}
