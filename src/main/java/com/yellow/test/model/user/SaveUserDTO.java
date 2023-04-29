package com.yellow.test.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    // TODO: 2/16/2019 Some pattern or limits
    @JsonProperty("username")
    private String username;

    @NotBlank
    // TODO: 2/16/2019 Some pattern or limits
    @JsonProperty("password")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveUserDTO that = (SaveUserDTO) o;
        return Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getPassword(), that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword());
    }
}
