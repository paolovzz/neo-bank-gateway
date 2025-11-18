package neo.bank.gateway.framework.adapter.input.rest.request;

import java.time.LocalDate;

import lombok.Value;

@Value
public class RegistraUtenteRequest {

    private String username;
    private String nome;
    private String cognome;
    private String email;
    private LocalDate dataNascita;
    private String residenza;
    private String password;
    private String telefono;
    private String codiceFiscale;
    
}
