package cz.muni.fi.pa165.hauntedhouses.security;

import cz.muni.fi.pa165.hauntedhouses.dto.PlayerAuthenticationDTO;
import cz.muni.fi.pa165.hauntedhouses.dto.PlayerDTO;
import cz.muni.fi.pa165.hauntedhouses.facade.PlayerFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@WebFilter(urlPatterns = {"/auth/*"})
public class ProtectFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(ProtectFilter.class);


    @Override
    public void doFilter(ServletRequest r, ServletResponse s, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;

        String auth = request.getHeader("Authorization");
        if (auth == null) {
            response401(response);
            return;
        }
        String[] creds = parseAuthHeader(auth);
        String logemail = creds[0];
        String password = creds[1];

        //get Spring context and UserFacade from it
        PlayerFacade playerFacade = WebApplicationContextUtils.getWebApplicationContext(r.getServletContext()).getBean(PlayerFacade.class);
        PlayerDTO matchingPlayer = playerFacade.findPlayerByEmail(logemail);
        if (matchingPlayer == null) {
            log.warn("no user with email {}", logemail);
            response401(response);
            return;
        }
        PlayerAuthenticationDTO playerAuthenticationDTO  = new PlayerAuthenticationDTO();
        playerAuthenticationDTO.setPlayerId(matchingPlayer.getId());
        playerAuthenticationDTO.setPassword(password);
        if (!playerFacade.isAdmin(matchingPlayer)) {
            log.warn("user not admin {}", matchingPlayer);
            response401(response);
            return;
        }
        if (!playerFacade.authenticate(playerAuthenticationDTO)) {
            log.warn("wrong credentials: user={} password={}", creds[0], creds[1]);
            response401(response);
            return;
        }
        request.setAttribute("authenticatedUser", matchingPlayer);
        chain.doFilter(request, response);
    }


    private String[] parseAuthHeader(String auth) {
        return new String(Base64.getDecoder().decode(auth.split(" ")[1])).split(":", 2);
    }

    private void response401(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=\"type email and password\"");
        response.getWriter().println("<html><body><h1>401 Unauthorized</h1> Go away ...</body></html>");
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("Running in {}", filterConfig.getServletContext().getServerInfo());
    }

    @Override
    public void destroy() {

    }
}