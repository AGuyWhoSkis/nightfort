package nightfort.api.v1.service;

import nightfort.api.v1.exception.ProfileNotFoundException;
import nightfort.api.v1.model.Profile;
import nightfort.api.v1.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile getProfile(Long id) {
        return profileRepository.findById(id).orElseThrow(() -> new ProfileNotFoundException(id));
    }

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Long id, Profile profile) {
        Profile profileToUpdate = profileRepository.findById(id).orElseThrow(() -> new ProfileNotFoundException(id));
        profileToUpdate.setDisplayName(profile.getDisplayName());
        return profileRepository.save(profileToUpdate);
    }

    public void deleteProfile(Long id) {
        profileRepository.deleteById(id);
    }

}
