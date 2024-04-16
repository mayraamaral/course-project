package dev.mayra.courses.entities.course;

import dev.mayra.courses.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {
  @Column(name = "name")
  private String name;

  @Id
  @Column(name = "code")
  private String code;

  @ManyToOne
  @JoinColumn(name = "instructor_id", referencedColumnName = "user_id")
  private User instructor;

  @Column(name = "description")
  private String description;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private CourseStatus status;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @Column(name = "inactivated_at")
  private LocalDate inactivatedAt;

  public boolean isInactive() {
    return this.status.equals(CourseStatus.INACTIVE);
  }

  public void inactivate() {
    this.status = CourseStatus.INACTIVE;
    this.inactivatedAt = LocalDate.now();
  }
}
