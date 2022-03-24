package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
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
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldi_loggetInn() {
        // arrange
        List<Konto> saldi = new ArrayList<>();
        Konto acc1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        saldi.add(acc1);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentSaldi(anyString())).thenReturn(saldi);

        // act
        List<Konto> result = bankController.hentSaldi();

        // assert
        assertEquals(saldi, result);

    }

    @Test
    public void hentSaldi_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> result = bankController.hentSaldi();

        // assert
        assertNull(result);
    }


    @Test
    public void registrerbetaling_ok() {
        // arrange
        List<Transaksjon> acc1Transactions = new ArrayList<>();
        Transaksjon oneTransaction = new Transaksjon(5, "105010123456", 299.99,
                "2011-05-07", "LOL xD", "3", "12345678901");

        acc1Transactions.add(oneTransaction);

        Konto acc1 = new Konto("105010123456", "01010110523",
                895, "Lønnskonto", "NOK", acc1Transactions);

        when(sjekk.loggetInn()).thenReturn(acc1.getPersonnummer());
        when(repository.registrerBetaling(oneTransaction)).thenReturn("OK");

        // act
        String result = bankController.registrerBetaling(oneTransaction);

        // assert
        assertEquals("OK", result);

    }

    @Test
    public void registrerbetaling_feil() {
        // arrange
        Transaksjon oneTransaction = new Transaksjon(5, "105010123456", 299.99,
                "2011-05-07", "hehe", "4", "12345678901");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String result = bankController.registrerBetaling(oneTransaction);

        // assert
        assertNull(result);
    }

    @Test
    public void hentBetalinger_OK() {
        // arrange
        List<Transaksjon> transactions = new ArrayList<>();
        Transaksjon transaction = new Transaksjon(5, "105010123456", 77.7,
                "2011-05-07", "LUL", "4", "12345678901");
        transactions.add(transaction);

        Konto acc1 = new Konto("105010123456", "01010110523",
                499, "Lønnskonto", "NOK", transactions);

        when(sjekk.loggetInn()).thenReturn("105010123456");
        when(repository.hentBetalinger(acc1.getPersonnummer())).thenReturn(transactions);

        // act
        List<Transaksjon> result = bankController.hentBetalinger();

        // assert
        assertEquals(transactions, result);
    }

    @Test
    public void hentBetalinger_feil() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> result = bankController.hentBetalinger();

        // assert
        assertNull(result);
    }

    @Test
    public void utforBetaling_ok() {
        // arrange
        List<Transaksjon> payments = new ArrayList<>();
        Transaksjon oneTransaction = new Transaksjon(5, "105010123456",
                77.7, "2011-05-07", "LUL", "4", "12345678901");

        payments.add(oneTransaction);

        Konto acc = new Konto("105010123456", "01010110523",
                499, "Lønnskonto", "NOK", payments);
        acc.setTransaksjoner(payments);

        when(sjekk.loggetInn()).thenReturn(acc.getPersonnummer());
        when(repository.utforBetaling(oneTransaction.getTxID())).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(payments);

        // act
        List<Transaksjon> result = bankController.utforBetaling(oneTransaction.getTxID());

        // assert
        assertEquals(payments, result);
    }

    @Test
    public void utforBetaling_feil() {
        // arrange
        List<Transaksjon> payments = new ArrayList<>();
        Transaksjon onePayment = new Transaksjon(5, "105010123456", 40, "2015-03-20", "LUL",
                "4", "12345678901");

        new Konto("105010123456", "01010110523",
                499, "Lønnskonto", "NOK", payments);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Transaksjon> result = bankController.utforBetaling(onePayment.getTxID());

        // assert
        assertNull(result);
    }

    @Test
    public void endre_ok() {
        // arrange
        Kunde customer = new Kunde("01010110523", "Lene", "Jensen",
                "Askerveien 22", "3270", "Oslo", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(customer.getPersonnummer());
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn(customer.getPersonnummer());

        // act
        String result = bankController.endre(customer);

        // assert
        assertEquals(customer.getPersonnummer(), result);
    }

    @Test
    public void endre_feil() {
        // arrange
        Kunde customer = new Kunde("01010110523", "Lene", "Jensen",
                "Askerveien 22", "3270", "Oslo", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        String result = bankController.endre(customer);

        // assert
        assertNull(result);
    }

    @Test
    public void hentTransaksjoner_ok() {

        // arrange
        List<Transaksjon> transactions = new ArrayList<>();

        Transaksjon transaction1 = new Transaksjon(5, "105010123456", 65,
                "2011-05-07", "LOL", "4", "12345678901");
        Transaksjon transaction2 = new Transaksjon(6, "654321010501", 86,
                "2017-08-30", "xD", "4", "10987654321");

        transactions.add(transaction1);
        transactions.add(transaction2);


        List <Konto> konti = new ArrayList<>();
        Konto acc1 = new Konto("105010123456", "01010110523",
                499, "Lønnskonto", "NOK", transactions);

        konti.add(acc1);

        acc1.setTransaksjoner(transactions);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(acc1);

        // act
        Konto result = bankController.hentTransaksjoner("105010123456","2011-01-01", "2012-01-01");

        // assert
        assertEquals(acc1, result);

    }

    @Test
    public void hentTransaksjoner_feil() {
        Konto acc1 = new Konto("115111133557", "02020211533",
                800, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        Konto result = bankController.hentTransaksjoner(null,null, null);

        assertNull(result);

    }
}

