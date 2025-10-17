package neo.bank.gateway.adapter.input.rest;

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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import neo.bank.gateway.adapter.input.rest.request.CreaContoCorrenteRequest;
import neo.bank.gateway.adapter.input.rest.request.LoginUtenteRequest;
import neo.bank.gateway.adapter.input.rest.request.RegistraUtenteRequest;
import neo.bank.gateway.adapter.input.rest.request.RichiediAggiornamentoEmailRequest;
import neo.bank.gateway.adapter.input.rest.request.RichiediAggiornamentoResidenzaRequest;
import neo.bank.gateway.adapter.input.rest.request.RichiediAggiornamentoTelefonoRequest;
import neo.bank.gateway.adapter.output.rest.AuthRestClient;
import neo.bank.gateway.adapter.output.rest.ClienteRestClient;
import neo.bank.gateway.adapter.output.rest.ContoCorrenteRestClient;

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

    @Inject
    public ApiGatewayResource(@RestClient AuthRestClient authRestClient, @RestClient ClienteRestClient clienteRestClient, @RestClient ContoCorrenteRestClient ccRestClient) {
        this.authRestClient = authRestClient;
        this.clienteRestClient = clienteRestClient;
        this.ccRestClient = ccRestClient;
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
    public Response creaContoCorrente() {
        log.info(("Inoltro richiesta apertura di un nuovo conto corrente"));
        String username = identity.getPrincipal().getName();
        return ccRestClient.creaContoCorrente(new CreaContoCorrenteRequest(username));
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
