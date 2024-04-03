package gp.dimitri.dymatennis.service;

import gp.dimitri.dymatennis.Player;
import gp.dimitri.dymatennis.PlayerToSave;
import gp.dimitri.dymatennis.Rank;
import gp.dimitri.dymatennis.dao.entity.PlayerEntity;
import gp.dimitri.dymatennis.dao.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PlayerService {

    private int TEMP_RANK = 999999999;

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    public List<Player> getAllPlayers() {
        return playerRepository.findAll().stream()
                .map(player -> new Player(
                        player.getFirstName(),
                        player.getLastName(),
                        player.getBirthDate(),
                        new Rank(player.getRank(), player.getPoints())
                ))
                .sorted(Comparator.comparing(player -> player.rank().position()))
                .collect(Collectors.toList());
    }

    public Player getPlayerByLastName(String lastName) {
        Optional<PlayerEntity> playerEntity = playerRepository.findByLastNameIgnoreCase(lastName);
        if (playerEntity.isEmpty()) {
            throw new PlayerNotFoundException(lastName);
        }
        return new Player(
                playerEntity.get().getFirstName(),
                playerEntity.get().getLastName(),
                playerEntity.get().getBirthDate(),
                new Rank(playerEntity.get().getRank(), playerEntity.get().getPoints())
        );
    }

    public Player createPlayer(PlayerToSave playerToSave) {
        playerRepository.findByLastNameIgnoreCase(playerToSave.lastName())
                .ifPresent(player -> {
                    throw new PlayerAlreadyExistsException(playerToSave.lastName());
                });

        PlayerEntity playerToRegister = new PlayerEntity(
                playerToSave.lastName(),
                playerToSave.firstName(),
                playerToSave.birthDate(),
                playerToSave.points(),
                TEMP_RANK);

        PlayerEntity registeredPlayer = playerRepository.save(playerToRegister);

        calculateRanking();

        return getPlayerByLastName(registeredPlayer.getLastName());
    }

    public Player updatePlayer(PlayerToSave playerToSave) {
        PlayerEntity playerToUpdate = playerRepository.findByLastNameIgnoreCase(playerToSave.lastName())
                .orElseThrow(() -> new PlayerNotFoundException(playerToSave.lastName()));

        playerToUpdate.setFirstName(playerToSave.firstName());
        playerToUpdate.setBirthDate(playerToSave.birthDate());
        playerToUpdate.setPoints(playerToSave.points());

        PlayerEntity updatedPlayer = playerRepository.save(playerToUpdate);

        calculateRanking();

        return getPlayerByLastName(updatedPlayer.getLastName());
    }

    public void calculateRanking() {
        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> newRanking = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(newRanking);
    }


    public void delete(String lastName) {
        PlayerEntity playerToDelete = playerRepository.findByLastNameIgnoreCase(lastName)
                .orElseThrow(() -> new PlayerNotFoundException(lastName));
        playerRepository.delete(playerToDelete);
        calculateRanking();
    }


}
