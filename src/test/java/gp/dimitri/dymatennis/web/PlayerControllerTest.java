package gp.dimitri.dymatennis.web;


import gp.dimitri.dymatennis.PlayerList;
import gp.dimitri.dymatennis.service.PlayerNotFoundException;
import gp.dimitri.dymatennis.service.PlayerService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Test
    public void shouldListAllPlayers() throws Exception {
        // Given
        Mockito.when(playerService.getAllPlayers()).thenReturn(PlayerList.ALL
        );

        // When / Then
        mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].lastName", CoreMatchers.is("Murray")))
                .andExpect(jsonPath("$[1].lastName", CoreMatchers.is("Djokovic")))
                .andExpect(jsonPath("$[2].lastName", CoreMatchers.is("Federer")))
                .andExpect(jsonPath("$[3].lastName", CoreMatchers.is("Nadal")));
    }

    @Test
    public void shouldRetrievePlayer() throws Exception {
        // Given
        String playerToRetrieve = "nadal";
        Mockito.when(playerService.getPlayerByLastName(playerToRetrieve)).thenReturn(PlayerList.RAFAEL_NADAL);

        // When / Then
        mockMvc.perform(get("/players/{lastName}", playerToRetrieve))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", CoreMatchers.is("Nadal")))
                .andExpect(jsonPath("$.firstName", CoreMatchers.is("Rafael")))
                .andExpect(jsonPath("$.rank.position", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.rank.points", CoreMatchers.is(5000)));

    }

    @Test
    public void shouldReturn404WhenPlayerNotFound() throws Exception {
        // Given
        String playerToRetrieve = "xxx";
        Mockito.when(playerService.getPlayerByLastName(playerToRetrieve)).thenThrow(new PlayerNotFoundException("Player with last name " + playerToRetrieve + " not found"));

        // When / Then
        mockMvc.perform(get("/players/{lastName}", playerToRetrieve))
                .andExpect(status().isNotFound());
    }
}
