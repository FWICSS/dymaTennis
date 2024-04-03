package gp.dimitri.dymatennis;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record PlayerToSave(
        @NotBlank(message = "First name is mandatory") String firstName,
        @NotBlank(message = "Last name is mandatory") String lastName,
        @NotNull(message = "Birthday is mandatory") @PastOrPresent(message = "Birthday must be in the past or present") LocalDate birthDate,
        @PositiveOrZero(message = "Rank must be positive or zero") int points) {
}
