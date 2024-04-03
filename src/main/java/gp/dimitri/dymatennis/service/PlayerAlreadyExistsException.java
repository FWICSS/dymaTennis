package gp.dimitri.dymatennis.service;

public class PlayerAlreadyExistsException extends RuntimeException {
    public PlayerAlreadyExistsException(String lastName) {
        super("Player with last name " + lastName + " already exists.");
    }
}
