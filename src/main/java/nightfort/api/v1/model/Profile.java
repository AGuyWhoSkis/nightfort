package nightfort.api.v1.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@EntityListeners(ProfileDisplayNameListener.class)
public class Profile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String displayName;

    @Column(columnDefinition = "TIMESTAMP")
    @Getter
    @Setter
    private LocalDateTime createTime;

    @Column(columnDefinition = "TIMESTAMP")
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private LocalDateTime lastNameChangeTime;

    // Track the previous display name for use in onUpdate()

    @Transient
    @Getter(AccessLevel.PACKAGE)
    private String oldDisplayName;

    @PostLoad
    void saveOldDisplayName() {
        this.oldDisplayName = this.displayName;
    }

}

