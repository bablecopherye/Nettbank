package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.DAL.BankRepository;
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
    private BankRepository repository;


    @Test
    public void test_sjekkLoggetInn_OK() {
        // arrange
        String personnummer = "01010110523";
        String passord = "HeiHei";

        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("OK");
        // when(session.setAttribute(anySet()).thenReturn("Innlogget", personnummer));

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void test_sjekkLoggetInn_feil() {
        // arrange
        String personnummer = "01010110523";
        String passord = "HeiHei";

        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("Feil");

        // act
        String resultat = sikkerhet.sjekkLoggInn(personnummer, passord);

        // assert
        assertEquals("Feil i personnummer eller passord", resultat);
    }

    @Test
    public void test_loggUt() {
        // arrange
        session.setAttribute("Innlogget", "10987654321");
        sikkerhet.loggUt();

        // act
        String resultat = (String) session.getAttribute("Innlogget");

        // assert
        assertNull(resultat);
    }

    @Test
    public void test_sjekkLoggetInn_feilPersonnummer() {
        // arrange
        String personnummer = "01010110";
        String passord = "HeiHei";

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