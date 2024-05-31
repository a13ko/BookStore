package az.abb.postservice.model.dto;

import az.abb.postservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRespDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
}
