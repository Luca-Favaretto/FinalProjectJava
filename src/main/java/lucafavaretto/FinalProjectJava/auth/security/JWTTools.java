package lucafavaretto.FinalProjectJava.auth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import lucafavaretto.FinalProjectJava.exceptions.UnauthorizedException;
import lucafavaretto.FinalProjectJava.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(User user) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis())) // Data di emissione del token (IAT)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Data di scadenza del token (Expiration Date)
                .subject(String.valueOf(user.getId())) // Subject ovvero a chi appartiene il token
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())) // Firmo il token
                .compact();
    }

    public void verifyToken(String token) { // Dato un token mi lancia eccezioni in caso di token manipolato o scaduto
        try {
            Jwts.parser()  // Crea un oggetto parser della libreria JWT
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))  // Configura la chiave segreta per la verifica della firma
                    .build()  // Costruisce l'oggetto JwtParser
                    .parse(token);  // Effettua il parsing del token JWT
        } catch (Exception ex) {
            throw new UnauthorizedException("Problem with token! Log in again!");
        }
    }


    public String extractIdFromToken(String token) {
        return Jwts.parser()  // Crea un oggetto parser della libreria JWT
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))  // Configura la chiave segreta per la verifica della firma
                .build()  // Costruisce l'oggetto JwtParser
                .parseSignedClaims(token)  // Effettua il parsing del token e restituisce le informazioni firmate
                .getPayload()  // Ottiene la parte del payload del token
                .getSubject();  // Ottiene il Subject dal payload (l'ID dell'utente)
    }
}
