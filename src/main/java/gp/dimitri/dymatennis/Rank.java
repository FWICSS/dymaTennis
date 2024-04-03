package gp.dimitri.dymatennis.dao.entity;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record Rank(
        @Positive int position,
        @PositiveOrZero int points) {
}
