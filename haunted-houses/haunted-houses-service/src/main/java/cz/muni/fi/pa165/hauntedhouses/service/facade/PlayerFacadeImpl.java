package cz.muni.fi.pa165.hauntedhouses.service.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.PlayerAuthenticationDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.PlayerDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.PlayerFacade;
import cz.muni.fi.pa165.hauntedhouses.model.Player;
import cz.muni.fi.pa165.hauntedhouses.service.MappingService;
import cz.muni.fi.pa165.hauntedhouses.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

/**
 * @author Zoltan Fridrich
 */
@Service
@Transactional
public class PlayerFacadeImpl implements PlayerFacade {

    private PlayerService playerService;
    private MappingService mappingService;

    @Autowired
    public PlayerFacadeImpl(PlayerService playerService,
                            MappingService mappingService) {
        this.playerService = playerService;
        this.mappingService = mappingService;
    }

    @Override
    public PlayerDTO getPlayerById(Long id) {
        Player player = playerService.getPlayerById(id);
        return (player == null) ? null : mappingService.mapTo(player, PlayerDTO.class);
    }

    @Override
    public PlayerDTO getPlayerByEmail(String email) {
        Player player = playerService.getPlayerByEmail(email);
        return (player == null) ? null : mappingService.mapTo(player, PlayerDTO.class);
    }

    @Override
    public Collection<PlayerDTO> getAllPlayers() {
        return mappingService.mapTo(playerService.getAllPlayers(), PlayerDTO.class);
    }

    @Override
    public void registerPlayer(PlayerDTO player, String unencryptedPassword) {
        Player playerEntity = mappingService.mapTo(player, Player.class);
        playerService.registerPlayer(playerEntity, unencryptedPassword);
        player.setId(playerEntity.getId());
    }

    @Override
    public boolean authenticate(PlayerAuthenticationDTO authentication) {
        return playerService.authenticate(
                playerService.getPlayerById(authentication.getPlayerId()),
                authentication.getPassword()
        );
    }

    @Override
    public boolean isAdmin(PlayerDTO player) {
        return playerService.isAdmin(mappingService.mapTo(player, Player.class));
    }

}
