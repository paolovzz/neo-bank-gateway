package neo.bank.gateway.framework.adapter.output.rest.request;

import lombok.Value;

@Value
public class InviaBonificoClientRequest {
    private String usernameCliente;
    private String ibanMittente;
    private String ibanDestinatario;
    private double importo;
    private String causale;

}
