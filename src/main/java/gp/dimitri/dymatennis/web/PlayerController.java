package gp.dimitri.dymatennis.web;


import gp.dimitri.dymatennis.Player;
import gp.dimitri.dymatennis.PlayerToSave;
import gp.dimitri.dymatennis.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Player API")
@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Operation(summary = "List all players", description = "List all players")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of players",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Player.class)))})
    })
    @GetMapping
    public List<Player> list() {
        return playerService.getAllPlayers();
    }

    @Operation(summary = "Find a player", description = "Find a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Player.class)))}),
            @ApiResponse(responseCode = "404", description = "Player with specified last name was not found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Error.class)))}),
            @ApiResponse(responseCode = "403", description = "Connected user is not authorized to perform this action.")
    })
    @GetMapping("{lastName}")
    public Player getByLastName(@PathVariable("lastName") String lastName) {
        return playerService.getPlayerByLastName(lastName);
    }

    @Operation(summary = "Create a player", description = "Create a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Create a Player",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlayerToSave.class)))}),
            @ApiResponse(responseCode = "400", description = "Player with specified last name already exists",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Error.class)))}),
            @ApiResponse(responseCode = "403", description = "Connected user is not authorized to perform this action.")
    })
    @PostMapping
    public Player createPlayer(@Valid @RequestBody PlayerToSave playerToSave) {
        return playerService.createPlayer(playerToSave);
    }

    @Operation(summary = "Update a player", description = "Update a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update a Player",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlayerToSave.class)))}),
            @ApiResponse(responseCode = "404", description = "Player with specified last name was not found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Error.class)))}),
            @ApiResponse(responseCode = "403", description = "Connected user is not authorized to perform this action.")
    })
    @PutMapping
    public Player updatePlayer(@Valid @RequestBody PlayerToSave playerToSave) {
        return playerService.updatePlayer(playerToSave);
    }

    @Operation(summary = "Delete a player", description = "Delete a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player has been deleted"),
            @ApiResponse(responseCode = "404", description = "Player with specified last name was not found",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Error.class)))}),
            @ApiResponse(responseCode = "403", description = "Connected user is not authorized to perform this action.")
    })
    @DeleteMapping("{lastName}")
    public void deletePlayer(@PathVariable("lastName") String lastName) {
        playerService.delete(lastName);
    }
}
