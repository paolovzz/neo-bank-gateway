package neo.bank.gateway.adapter.input.rest.request;

import lombok.Value;

@Value
public class InviaBonificoRequest {
    private String ibanMittente;
    private String ibanDestinatario;
    private double importo;
    private String causale;

}
