package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.web.bind.annotation.RequestBody;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    @InjectMocks
    // denne skal testes
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository adminRepository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void test_hentAlle_loggetInn() {
        // given
        List<Kunde> kunder = new ArrayList<>();

        Kunde kunde1 = new Kunde(
                "01010110523",
                "Lene",
                "Jensen",
                "Askerveien 22",
                "3270",
                "Asker",
                "22224444",
                "HeiHei");
        Kunde kunde2 = new Kunde(
                "13349270527",
                "Hans",
                "Andersen",
                "Osloveien 99",
                "0470",
                "Oslo",
                "22789435",
                "Passord123");
        kunder.add(kunde1);
        kunder.add(kunde2);

        // when
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(adminRepository.hentAlleKunder()).thenReturn(kunder);

        // then
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertEquals(kunder, resultat);

    }

    @Test
    public void test_hentAlle_ikkeLoggetInn() {
        // when
        when(sjekk.loggetInn()).thenReturn(null);

        // then
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);

    }

    @Test
    public void test_lagreKunde_loggetInn() {
        // arrange
        Kunde innKunde = new Kunde(
                "01010110523",
                "Lene",
                "Jensen",
                "Askerveien 22",
                "3270",
                "Asker",
                "22224444",
                "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(adminRepository.registrerKunde(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = adminKundeController.lagreKunde(innKunde);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void test_lagreKunde_ikkeLoggetInn() {
        // arrange
        Kunde innKunde = new Kunde(
                "01010110523",
                "Lene",
                "Jensen",
                "Askerveien 22",
                "3270",
                "Asker",
                "22224444",
                "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.lagreKunde(innKunde);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }

    @Test
    public void test_endreKundeInfo_loggetInn() {
        // arrange
        Kunde innKunde = new Kunde(
                "01010110523",
                "Lene",
                "Jensen",
                "Askerveien 22",
                "3270",
                "Asker",
                "22224444",
                "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(adminRepository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        // act
        String resultat = adminKundeController.endre(innKunde);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void test_endreKundeInfo_ikkeLoggetInn() {
        // arrange
        Kunde innKunde = new Kunde(
                "01010110523",
                "Lene",
                "Jensen",
                "Askerveien 22",
                "3270",
                "Asker",
                "22224444",
                "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.endre(innKunde);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }
/*
    @Test
    public void test_slettKunde_loggetInn() {
        // arrange
        String personnummer = "01010110523";

        when(sjekk.loggetInn()).thenReturn(personnummer);
        when(adminRepository.slettKunde(anyString()).thenReturn("OK"));

        // act
        String resultat = adminKundeController.slett(personnummer);

        // assert
        assertEquals("OK", resultat);
    }
*/
    @Test
    public void test_slettKunde_ikkeLoggetInn() {
        // arrange
        String personnummer = "01010110523";

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String resultat = adminKundeController.slett(personnummer);

        // assert
        assertEquals("Ikke logget inn", resultat);
    }
}