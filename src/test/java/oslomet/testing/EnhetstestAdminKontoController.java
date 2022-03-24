package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {
    @InjectMocks
    // denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKonti_ok() {
        // arrange
        List<Konto> accounts = new ArrayList<>();
        Konto acc1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto acc2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        accounts.add(acc1);
        accounts.add(acc2);

        when(sjekk.loggetInn()).thenReturn(acc1.getPersonnummer(), acc2.getPersonnummer());

        when(repository.hentAlleKonti()).thenReturn(accounts);

        // act
        List<Konto> result = adminKontoController.hentAlleKonti();

        // assert
        assertEquals(accounts, result);
    }

    @Test
    public void hentAlleKonti_feil() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> result = adminKontoController.hentAlleKonti();

        // assert
        assertNull(result);
    }

    @Test
    public void registrerKonto_ok() {
        // Arrange
        List<Transaksjon> acc1Transactions = new ArrayList<>();
        Konto acc1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", acc1Transactions);

        when(sjekk.loggetInn()).thenReturn(acc1.getPersonnummer());
        when(repository.registrerKonto(any(Konto.class))).thenReturn("OK");

        // Act
        String result = adminKontoController.registrerKonto(acc1);

        // Assert
        assertEquals("OK", result);
    }

    @Test
    public void registrerKonto_feil() {
        // Arrange
        List<Transaksjon> acc1Transactions = new ArrayList<>();
        Konto acc1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", acc1Transactions);
        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String result = adminKontoController.registrerKonto(acc1);

        // Assert
        assertEquals("Ikke innlogget", result);
    }

    @Test
    public void endreKonto_ok() {
        // Arrange
        List<Transaksjon> acc1Transactions = new ArrayList<>();
        Konto acc1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", acc1Transactions);

        when(sjekk.loggetInn()).thenReturn(acc1.getPersonnummer());

        when(repository.endreKonto(any(Konto.class))).thenReturn("OK");

        // Act
        String result = adminKontoController.endreKonto(acc1);

        // Assert
        assertEquals("OK", result);
    }

    @Test
    public void endreKonto_feil() {
        // Arrange
        List<Transaksjon> acc1Transactions = new ArrayList<>();
        Konto acc1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", acc1Transactions);

        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String result = adminKontoController.endreKonto(acc1);

        // Assert
        assertEquals("Ikke innlogget", result);
    }

    @Test
    public void slettKonto_ok() {
        // Arrange
        List<Transaksjon> acc1Transactions = new ArrayList<>();
        Konto acc1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", acc1Transactions);

        when(sjekk.loggetInn()).thenReturn(acc1.getPersonnummer());

        when(repository.slettKonto(acc1.getKontonummer())).thenReturn("OK");

        // Act
        String result = adminKontoController.slettKonto(acc1.getKontonummer());

        // Assert
        assertEquals("OK", result);
    }

    @Test
    public void slettKonto_feil() {
        // Arrange
        List<Transaksjon> acc1Transactions = new ArrayList<>();
        Konto acc1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", acc1Transactions);

        when(sjekk.loggetInn()).thenReturn(null);

        // Act
        String result = adminKontoController.slettKonto(acc1.getKontonummer());

        // Assert
        assertEquals("Ikke innlogget", result);
    }
}
