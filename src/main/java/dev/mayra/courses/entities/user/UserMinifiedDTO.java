package dev.mayra.courses.entities.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMinifiedDTO {
  public String name;
  public String email;
  public String role;
}
