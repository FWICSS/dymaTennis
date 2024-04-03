package gp.dimitri.dymatennis.service;

public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(String lastName) {
        super("Player with last name " + lastName + " not found");
    }
}
