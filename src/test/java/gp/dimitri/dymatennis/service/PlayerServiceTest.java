package gp.dimitri.dymatennis.service;

import gp.dimitri.dymatennis.Player;
import gp.dimitri.dymatennis.dao.repo.PlayerRepository;
import gp.dimitri.dymatennis.data.PlayerEntityList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    private PlayerService playerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        playerService = new PlayerService(playerRepository);
    }

    @Test
    public void shouldReturnPlayersRanking() {
        // Given
        Mockito.when(playerRepository.findAll()).thenReturn(PlayerEntityList.ALL);


        // When
        List<Player> players = playerService.getAllPlayers();

        // Then
        Assertions.assertThat(players).extracting("lastName").containsExactly("Nadal", "Djokovic", "Federer", "Murray");

    }

    @Test
    public void shouldReturnPlayerByLastName() {
        // Given
        Mockito.when(playerRepository.findByLastNameIgnoreCase("Nadal")).thenReturn(Optional.of(PlayerEntityList.RAFAEL_NADAL));

        // When
        Player retrievedPlayer = playerService.getPlayerByLastName("Nadal");

        // Then
        Assertions.assertThat(retrievedPlayer.lastName()).isEqualTo("Nadal");
        Assertions.assertThat(retrievedPlayer.firstName()).isEqualTo("Rafael");
        Assertions.assertThat(retrievedPlayer.rank().position()).isEqualTo(1);
    }

    @Test
    public void shouldFailToRetrieve_WhenPlayerDoesNotExist() {
        // Given
        String unknownPlayerLastName = "Unknown";
        Mockito.when(playerRepository.findByLastNameIgnoreCase(unknownPlayerLastName)).thenReturn(Optional.empty());

        // When
       Exception exception = assertThrows(PlayerNotFoundException.class, () -> playerService.getPlayerByLastName(unknownPlayerLastName));

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo("Player with last name Unknown not found");
    }
}
