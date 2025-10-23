package neo.bank.gateway.framework.adapter.input.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImpostaAbilitazionePagamentiOnlineRequest {
    private String numeroCarta;
    private String iban;
    private boolean abilitazionePagamentiOnline;
}
