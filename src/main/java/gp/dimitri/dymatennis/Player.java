package gp.dimitri.dymatennis;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public record Player(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @PastOrPresent LocalDate birthDate,
        @Valid Rank rank
) {

}
