package cz.muni.fi.pa165.hauntedhouses.service.facade;

import cz.muni.fi.pa165.hauntedhouses.dto.PlayerAuthenticationDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.PlayerDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.PlayerFacade;

public class PlayerFacadeImpl implements PlayerFacade {
    @Override
    public PlayerDTO findPlayerById(Long id) {
        return null;
    }

    @Override
    public PlayerDTO findPlayerByEmail(String email) {
        return null;
    }

    @Override
    public void registerPlayer(PlayerDTO player, String unencryptedPassword) {

    }

    @Override
    public boolean authenticate(PlayerAuthenticationDTO authentication) {
        return false;
    }

    @Override
    public boolean isAdmin(Long playerId) {
        return false;
    }
}
