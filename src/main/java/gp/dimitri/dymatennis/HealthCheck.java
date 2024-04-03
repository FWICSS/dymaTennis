package gp.dimitri.dymatennis;

import gp.dimitri.dymatennis.ApplicationStatus;

public record HealthCheck(ApplicationStatus status, String message) {
}