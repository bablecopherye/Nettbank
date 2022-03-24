package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.Sikkerhet.Sikkerhet;

import javax.servlet.http.HttpSession;
import java.lang.String;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhet {


    @InjectMocks
    // denne skal testes
    private Sikkerhet sikkerhet;

    @Mock
    private HttpSession session;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void test_sjekkLoggetInn_OK() {
        // arrange
        String personnummer = "01010110523";
        String passord = "HeiHei";

        when(sjekk.loggetInn()).thenReturn(personnummer, passord);
        // when(session.setAttribute(anySet()).thenReturn("Innlogget", personnummer));

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void test_sjekkLoggetInn_feilPersonnummer() {
        // arrange
        String personnummer = "01010110";
        String passord = "HeiHei";

        when(sjekk.loggetInn()).thenReturn(personnummer, passord);

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // assert
        assertEquals("Feil i personnummer", resultat);
    }

    @Test
    public void test_sjekkLoggetInn_feilPassord() {
        // arrange
        String personnummer = "01010110523";
        String passord = "Hei";

        when(sjekk.loggetInn()).thenReturn(personnummer, passord);

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // assert
        assertEquals("Feil i passord", resultat);
    }

    @Test
    public void test_loggInnAdmin_OK() {
        // arrange
        String bruker = "Admin";
        String passord = "Admin";

        when(sjekk.loggInnAdmin(anyString(), anyString())).thenReturn(bruker, passord);

        // act
        String resultat = sikkerhet.loggInnAdmin(bruker, passord);

        // assert
        assertEquals("Logget inn", resultat);
    }

    @Test
    public void test_loggInnAdmin_feil() {
        // arrange
        String bruker = "AdminMin";
        String passord = "MinMin";

        when(sjekk.loggInnAdmin(anyString(), anyString())).thenReturn(bruker, passord);

        // act
        String resultat = sikkerhet.loggInnAdmin(bruker, passord);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void test_loggetInn_OK() {
        // arrange
        when(session.getAttribute(anyString())).thenReturn("Innlogget");

        // act
        String resultat = sikkerhet.loggetInn();

        // assert
        assertEquals("Innlogget", resultat);

    }

    @Test
    public void test_ikkeLoggetInn() {
        // arrange
        when(session.getAttribute(anyString())).thenReturn(null);

        // act
        String resultat = sikkerhet.loggetInn();

        // assert
        assertNull(resultat);

    }
}