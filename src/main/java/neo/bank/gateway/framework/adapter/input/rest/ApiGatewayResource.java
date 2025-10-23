package neo.bank.gateway.framework.adapter.input.rest;

import java.security.Principal;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.auth.principal.JWTCallerPrincipal;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import neo.bank.gateway.framework.adapter.input.rest.request.CreaCartaRequest;
import neo.bank.gateway.framework.adapter.input.rest.request.CreaContoCorrenteRequest;
import neo.bank.gateway.framework.adapter.input.rest.request.ImpostaAbilitazionePagamentiOnlineRequest;
import neo.bank.gateway.framework.adapter.input.rest.request.ImpostaSogliaBonificoRequest;
import neo.bank.gateway.framework.adapter.input.rest.request.ImpostaSogliaPagamentiRequest;
import neo.bank.gateway.framework.adapter.input.rest.request.ImpostaStatoCartaRequest;
import neo.bank.gateway.framework.adapter.input.rest.request.InviaBonificoRequest;
import neo.bank.gateway.framework.adapter.input.rest.request.LoginUtenteRequest;
import neo.bank.gateway.framework.adapter.input.rest.request.RegistraUtenteRequest;
import neo.bank.gateway.framework.adapter.input.rest.request.RichiediAggiornamentoEmailRequest;
import neo.bank.gateway.framework.adapter.input.rest.request.RichiediAggiornamentoResidenzaRequest;
import neo.bank.gateway.framework.adapter.input.rest.request.RichiediAggiornamentoTelefonoRequest;
import neo.bank.gateway.framework.adapter.output.rest.AuthRestClient;
import neo.bank.gateway.framework.adapter.output.rest.CartaRestClient;
import neo.bank.gateway.framework.adapter.output.rest.ClienteRestClient;
import neo.bank.gateway.framework.adapter.output.rest.ContoCorrenteRestClient;
import neo.bank.gateway.framework.adapter.output.rest.request.CreaCartaClientRequest;
import neo.bank.gateway.framework.adapter.output.rest.request.ImpostaAbilitazionePagamentiOnlineClientRequest;
import neo.bank.gateway.framework.adapter.output.rest.request.ImpostaSogliaBonificoClientRequest;
import neo.bank.gateway.framework.adapter.output.rest.request.ImpostaSogliaPagamentiClientRequest;
import neo.bank.gateway.framework.adapter.output.rest.request.ImpostaStatoCartaClientRequest;
import neo.bank.gateway.framework.adapter.output.rest.request.InviaBonificoClientRequest;

@Path("/api/v1")
@Slf4j
public class ApiGatewayResource {

    @Inject
    private SecurityIdentity identity;

    @RestClient
    private final AuthRestClient authRestClient;

    @RestClient
    private final ClienteRestClient clienteRestClient;

    @RestClient
    private final ContoCorrenteRestClient ccRestClient;

    @RestClient
    private final CartaRestClient cartaRestClient;

    @Inject
    public ApiGatewayResource(@RestClient AuthRestClient authRestClient, @RestClient ClienteRestClient clienteRestClient, @RestClient ContoCorrenteRestClient ccRestClient, @RestClient CartaRestClient cartaRestClient) {
        this.authRestClient = authRestClient;
        this.clienteRestClient = clienteRestClient;
        this.ccRestClient = ccRestClient;
        this.cartaRestClient = cartaRestClient;
    }

    /**********************************************
     *  API IAM
     *********************************************/
    @POST
    @Tag(name="Endpoints IAM")
    @Path("/auth/signin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signin(@RequestBody RegistraUtenteRequest request) {

        log.info(("Inoltro richiesta registrazione utente"));
        return authRestClient.registraUtente(request);
    }

    @POST
    @Path("/auth/login")
    @Tag(name="Endpoints IAM")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@RequestBody LoginUtenteRequest request) {

        log.info(("Inoltro richiesta login utente"));
        return authRestClient.login(request);
    }

    @POST
    @Path("/auth/logout")
    @Tag(name="Endpoints IAM")
    @RolesAllowed("cliente")
    public Response logout() {

        log.info(("Inoltro richiesta logout utente"));
        return authRestClient.logout(getToken(identity));
    }

    

    /**********************************************
     *  API CLIENTE
     *********************************************/


    @GET
    @Path("/clienti")
    @Tag(name="Endpoints Clienti")
    @RolesAllowed("cliente")
    public Response recuperaDatiCliente() {
        log.info(("Inoltro richiesta recupero dati cliente"));
        String username = identity.getPrincipal().getName();
        return clienteRestClient.recuperaDatiCliente(username);
    }

    @GET
    @Path("/clienti/iban")
    @Tag(name="Endpoints Clienti")
    @RolesAllowed("cliente")
    public Response recuperaListaIbanCliente() {
        log.info(("Inoltro richiesta recupero lista iban del cliente cliente"));
        String username = identity.getPrincipal().getName();
        return clienteRestClient.recuperaListaIbanCliente(username);
    }


    @Path("/clienti/residenza")
    @Tag(name="Endpoints Clienti")
    @RolesAllowed("cliente")
    @PUT // Sarebbe piu' corretto PATCH ma quarkus non lo supporta
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response aggiornaResidenza( RichiediAggiornamentoResidenzaRequest req) {
        log.info(("Inoltro richiesta aggiornamento residenza"));
        String username = identity.getPrincipal().getName();
        clienteRestClient.aggiornaResidenza(username, req);
        return Response.noContent().build();
    }

    @Path("/clienti/telefono")
    @Tag(name="Endpoints Clienti")
    @RolesAllowed("cliente")
    @PUT // Sarebbe piu' corretto PATCH ma quarkus non lo supporta
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response aggiornaTelefono( RichiediAggiornamentoTelefonoRequest req) {
        log.info(("Inoltro richiesta aggiornamento telefono"));
         String username = identity.getPrincipal().getName();
        clienteRestClient.aggiornaTelefono(username, req);
        return Response.noContent().build();
    }

    @Path("/clienti/email")
    @PUT // Sarebbe piu' corretto PATCH ma quarkus non lo supporta
    @Tag(name="Endpoints Clienti")
    @RolesAllowed("cliente")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response aggiornaEmail( RichiediAggiornamentoEmailRequest req) {
        log.info(("Inoltro richiesta aggiornamento email"));
        String username = identity.getPrincipal().getName();
        clienteRestClient.aggiornaEmail(username, req);
        return Response.noContent().build();
    }

    /**********************************************
     *  API CONTO CORRENTE
     *********************************************/

    @GET
    @Path("/cc/{iban}")
    @Tag(name="Endpoints Conti Correnti")
    @RolesAllowed("cliente")
    public Response recuperaDatiIban(@PathParam(value = "iban") String iban) {
        log.info(("Inoltro richiesta recupero dati iban"));
        return ccRestClient.recuperaContoCorrenteDaIban(iban);
    }

    @Path("/cc")
    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Tag(name="Endpoints Conti Correnti")
    @RolesAllowed("cliente")
    public Response creaContoCorrente() {
        log.info(("Inoltro richiesta apertura di un nuovo conto corrente"));
        String username = identity.getPrincipal().getName();
        return ccRestClient.creaContoCorrente(new CreaContoCorrenteRequest(username));
        }

    
    @Path("/cc/soglia-bonifico-giornaliera")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    @Tag(name="Endpoints Conti Correnti")
    @RolesAllowed("cliente")
    public Response impostaSogliaBonificoGiornaliera( ImpostaSogliaBonificoRequest request){
        try {
            log.info(("Inoltro richiesta aggiornamento soglia bonifico giornaliera"));
            String username = identity.getPrincipal().getName();
            return ccRestClient.impostaSogliaBonificoGiornaliera(new ImpostaSogliaBonificoClientRequest(request.getIban(), username, request.getNuovaSoglia()));
        } catch (WebApplicationException ex) {
            log.error("Errore durante l'aggiornamento della soglia bonifico giornaliera", ex.getMessage());
            return ex.getResponse();
        }
    }

    @Path("/cc/soglia-bonifico-mensile")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    @Tag(name="Endpoints Conti Correnti")
    @RolesAllowed("cliente")
    public Response impostaSogliaBonificoMensile(ImpostaSogliaBonificoRequest request){
        try {
            log.info(("Inoltro richiesta aggiornamento soglia bonifico mensile"));
            String username = identity.getPrincipal().getName();
            return ccRestClient.impostaSogliaBonificoMensile(new ImpostaSogliaBonificoClientRequest(request.getIban(), username, request.getNuovaSoglia()));
        } catch (WebApplicationException ex) {
            log.error("Errore durante l'aggiornamento della soglia bonifico mensile", ex.getMessage());
            return ex.getResponse();
        }
        
    }

    @Path("/cc/predisponi-bonifico")
    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    @Tag(name="Endpoints Conti Correnti")
    @RolesAllowed("cliente")
    public Response predisponiBonifico(InviaBonificoRequest request) {
        try {
            log.info(("Inoltro richiesta predisposizione bonifico"));
            String username = identity.getPrincipal().getName();
            return ccRestClient.predisponiBonifico(new InviaBonificoClientRequest(username, request.getIbanMittente(), request.getIbanDestinatario(), request.getImporto(), username));
        } catch (WebApplicationException ex) {
            log.error("Errore durante la predisposizione del bonifico", ex.getMessage());
            return ex.getResponse();
        }
    }


    /**********************************************
     *  API CARTA
     *********************************************/

    @GET
    @Path("/carte/{numeroCarta}")
    @Tag(name="Endpoints Carte")
    @RolesAllowed("cliente")
    public Response recuperaDatiCarta(@PathParam(value = "numeroCarta") String numeroCarta) {
        log.info(("Inoltro richiesta recupero dati carta"));
        return cartaRestClient.recuperaCartaDaNumeroCarta(numeroCarta);
    }

    @Path("/carte")
    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
     @Tag(name="Endpoints Carte")
    @RolesAllowed("cliente")
    public Response creaCarta(CreaCartaRequest request) {
        log.info(("Inoltro richiesta creazione di una nuova carta"));
        String username = identity.getPrincipal().getName();
        return cartaRestClient.creaCarta(new CreaCartaClientRequest(username, request.getIban()));
        }

    
    @Path("/carte/soglia-pagamenti-giornaliera")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    @Tag(name="Endpoints Carte")
    @RolesAllowed("cliente")
    public Response impostaSogliaPagamentiGiornaliera( ImpostaSogliaPagamentiRequest request){
        try {
            log.info(("Inoltro richiesta aggiornamento soglia pagamenti giornaliera"));
            String username = identity.getPrincipal().getName();
            return cartaRestClient.impostaSogliaPagamentiGiornaliera(new ImpostaSogliaPagamentiClientRequest(request.getNumeroCarta(), request.getIban(), username, request.getNuovaSoglia()));
        } catch (WebApplicationException ex) {
            log.error("Errore durante l'aggiornamento della soglia bonifico giornaliera", ex.getMessage());
            return ex.getResponse();
        }
    }

    @Path("/carte/soglia-pagamenti-mensile")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    @Tag(name="Endpoints Carte")
    @RolesAllowed("cliente")
    public Response impostaSogliaPagamentiMensile( ImpostaSogliaPagamentiRequest request){
        try {
            log.info(("Inoltro richiesta aggiornamento soglia pagamenti mensile"));
            String username = identity.getPrincipal().getName();
            return cartaRestClient.impostaSogliaPagamentiMensile(new ImpostaSogliaPagamentiClientRequest(request.getNumeroCarta(), request.getIban(), username, request.getNuovaSoglia()));
        } catch (WebApplicationException ex) {
            log.error("Errore durante l'aggiornamento della soglia bonifico giornaliera", ex.getMessage());
            return ex.getResponse();
        }
    }


    @Path("/carte/abilitazione-pagamenti-online")
    @PUT
    @Tag(name="Endpoints Carte")
    @RolesAllowed("cliente")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaAbilitazionePagamentiOnline( ImpostaAbilitazionePagamentiOnlineRequest request) {
        try {
            log.info(("Inoltro richiesta settaggio abilitazione pagamenti online"));
            String username = identity.getPrincipal().getName();
            return cartaRestClient.impostaAbilitazionePagamentiOnline(new ImpostaAbilitazionePagamentiOnlineClientRequest(request.getNumeroCarta(), request.getIban(), username, request.isAbilitazionePagamentiOnline()));
        } catch (WebApplicationException ex) {
            log.error("Errore durante l'aggiornamento della soglia bonifico giornaliera", ex.getMessage());
            return ex.getResponse();
        }
    }

    @Path("/carte/stato-carta")
    @PUT
    @Tag(name="Endpoints Carte")
    @RolesAllowed("cliente")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaStatoCarta( ImpostaStatoCartaRequest request) {
        try {
            log.info(("Inoltro richiesta settaggio stato della carta"));
            String username = identity.getPrincipal().getName();
            return cartaRestClient.impostaStatoCarta(new ImpostaStatoCartaClientRequest(request.getNumeroCarta(), request.getIban(), username, request.isStatoCarta()));
        } catch (WebApplicationException ex) {
            log.error("Errore durante l'aggiornamento della soglia bonifico giornaliera", ex.getMessage());
            return ex.getResponse();
        }
    }
    //****************************************************************************** */

    private String getToken(SecurityIdentity identity) {
        Principal principal = identity.getPrincipal();
        if (principal instanceof JWTCallerPrincipal) {
            JWTCallerPrincipal jwtPrincipal = (JWTCallerPrincipal) principal;
            String token = jwtPrincipal.getRawToken();
            return token;
        } else {
            return "Nessun token disponibile";
        }
    }
}
