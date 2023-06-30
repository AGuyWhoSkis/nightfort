package nightfort.api.v1.model;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

class ProfileDisplayNameListener {
    
    @PreUpdate
    void preUpdate(Profile target) {
        // When displayName is changed, update lastNameChangeTime
        if (target.getOldDisplayName() != null && !target.getOldDisplayName().equals(target.getDisplayName())) {
            target.setLastNameChangeTime(LocalDateTime.now());
        }
    }
    
    @PrePersist
    void prePersist(Profile target) {
        target.setCreateTime(LocalDateTime.now());
        
        
        if (target.getOldDisplayName() != null && !target.getOldDisplayName().equals(target.getDisplayName())) {
            target.setLastNameChangeTime(LocalDateTime.now());
        }
    }
}
