package cz.muni.fi.pa165.hauntedhouses.security;

import cz.muni.fi.pa165.hauntedhouses.dto.PlayerAuthenticationDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.PlayerDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.PlayerFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final static Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private PlayerFacade playerFacade;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (authenticate(email,password)) {
            final List<GrantedAuthority> grantedAuths = new ArrayList<>();
            PlayerDTO matchingPlayer = playerFacade.findPlayerByEmail(email);
            if(matchingPlayer.isAdmin()) {
               grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
            else {
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            return new UsernamePasswordAuthenticationToken(
                    email, password, grantedAuths);
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public boolean authenticate(String email,String password) {
        PlayerDTO matchingPlayer = playerFacade.findPlayerByEmail(email);
        if (matchingPlayer == null) {
            log.warn("no user with email {}", email);
            return false;
        }
        PlayerAuthenticationDTO playerAuthenticationDTO  = new PlayerAuthenticationDTO();
        playerAuthenticationDTO.setPlayerId(matchingPlayer.getId());
        playerAuthenticationDTO.setPassword(password);
        if (!playerFacade.isAdmin(matchingPlayer)) {
            log.warn("user not admin {}", matchingPlayer);
        }
        if (!playerFacade.authenticate(playerAuthenticationDTO)) {
            log.warn("wrong credentials: user={} password={}", email, password);
            return false;
        }
        return true;
    }
}
