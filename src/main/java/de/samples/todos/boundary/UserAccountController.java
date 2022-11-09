package de.samples.todos.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Profile("security")
@PreAuthorize("hasRole('USER')")
@Tag(name = "users", description = "User Information (only when security is enabled)")
public class UserAccountController {

    @Schema(name="User", description = "A user's account information.")
    public static @Data class UserDto {

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @Schema(description = "The user name")
        private String name;
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @Schema(description = "The user's roles")
        private String[] roles;

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Detects the current user account.")
    @ApiResponse(
      responseCode = "200",
      description = "The user was detected and returned."
    )
    public UserDto getUser(Authentication auth) {
        final var result = new UserDto();
        result.setName(auth.getName());
        result.setRoles(
          auth.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .toArray(String[]::new)
        );
        return result;
    }

}
