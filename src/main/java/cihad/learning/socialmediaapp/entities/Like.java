package cihad.learning.socialmediaapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "p_like")
@Getter
@Setter
public class Like {
    @Id
    Long id;
    Long postId;
    Long userId;
}
