package gp.dimitri.dymatennis.dao.entity;

import gp.dimitri.dymatennis.ApplicationStatus;

public record HealthCheck(ApplicationStatus status, String message) {
}