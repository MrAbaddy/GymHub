package br.csi.gymhub.security;

import br.csi.gymhub.service.AutenticacaoService;
import br.csi.gymhub.service.TokenServiceJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class AutenticacaoFilter extends OncePerRequestFilter {
    private final TokenServiceJWT tokenService;
    private final AutenticacaoService autenticacaoService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException, ServletException {

        String token = recuperarToken(request);

        if (token != null) {
            try {
                String subject = this.tokenService.getSubject(token);
                UserDetails user = this.autenticacaoService.loadUserByUsername(subject);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                System.out.println("Token inválido ou expirado: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.replace("Bearer ", "").trim();
    }
}