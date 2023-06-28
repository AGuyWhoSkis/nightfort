package nightfort.api.v1.service;

import nightfort.api.v1.exception.ProfileNotFoundException;
import nightfort.api.v1.model.Profile;
import nightfort.api.v1.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
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
    void getAllProfiles() {
        when(profileRepository.findAll()).thenReturn(Arrays.asList(profile1, profile2));

        assertThat(profileService.getAllProfiles()).containsExactly(profile1, profile2);
        verify(profileRepository, times(1)).findAll();
    }

    @Test
    void getProfile() {
        when(profileRepository.findById(anyLong())).thenReturn(Optional.of(profile1));

        assertThat(profileService.getProfile(1L)).isEqualTo(profile1);
        verify(profileRepository, times(1)).findById(anyLong());
    }

    @Test
    void getProfileNotFound() {
        when(profileRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> profileService.getProfile(1L))
                .isInstanceOf(ProfileNotFoundException.class)
                .hasMessageContaining("Profile '1' not found.");

        verify(profileRepository, times(1)).findById(anyLong());
    }

    @Test
    void createProfile() {
        when(profileRepository.save(any(Profile.class))).thenReturn(profile1);

        assertThat(profileService.createProfile(profile1)).isEqualTo(profile1);
        verify(profileRepository, times(1)).save(any(Profile.class));
    }

    @Test
    void updateProfile() {
        when(profileRepository.findById(anyLong())).thenReturn(Optional.of(profile1));
        when(profileRepository.save(any(Profile.class))).thenReturn(profile1);

        assertThat(profileService.updateProfile(1L, profile1)).isEqualTo(profile1);

        verify(profileRepository, times(1)).findById(anyLong());
        verify(profileRepository, times(1)).save(any(Profile.class));
    }

    @Test
    void deleteProfile() {
        doNothing().when(profileRepository).deleteById(anyLong());

        profileService.deleteProfile(1L);

        verify(profileRepository, times(1)).deleteById(anyLong());
    }
}
