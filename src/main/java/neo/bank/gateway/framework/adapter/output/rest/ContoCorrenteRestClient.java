package neo.bank.gateway.framework.adapter.output.rest;


import java.time.LocalDate;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import neo.bank.gateway.domain.enums.TipologiaFlusso;
import neo.bank.gateway.framework.adapter.output.rest.request.ImpostaSogliaBonificoClientRequest;
import neo.bank.gateway.framework.adapter.output.rest.request.InviaBonificoClientRequest;

@Path("/cc")
@RegisterRestClient(configKey = "conto-corrente-service")
public interface ContoCorrenteRestClient {

    @Path("/{iban}")
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response recuperaContoCorrenteDaIban(@PathParam(value = "iban") String iban, @HeaderParam("X-Authenticated-User") String authenticatedUser);


    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response creaContoCorrente(@HeaderParam("X-Authenticated-User") String authenticatedUser);

    @Path("/soglia-bonifico-giornaliera")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaSogliaBonificoGiornaliera(@HeaderParam("X-Authenticated-User") String authenticatedUser, ImpostaSogliaBonificoClientRequest request);

    @Path("/soglia-bonifico-mensile")
    @PUT
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response impostaSogliaBonificoMensile(@HeaderParam("X-Authenticated-User") String authenticatedUser, ImpostaSogliaBonificoClientRequest request);


    @Path("/predisponi-bonifico")
    @POST
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response predisponiBonifico(@HeaderParam("X-Authenticated-User") String authenticatedUser, InviaBonificoClientRequest request);


    @GET
    @Path("/{iban}/transazioni")
    Response recuperaTransazioni(
        @HeaderParam("X-Authenticated-User") String authenticatedUser,   // 👈 header aggiunto
        @PathParam("iban") String iban,
        @QueryParam("dataCreazioneMin") LocalDate dataCreazioneMin,
        @QueryParam("dataCreazioneMax") LocalDate dataCreazioneMax,
        @QueryParam("tipologiaFlusso") TipologiaFlusso tipologiaFlusso,
        @QueryParam("importoMin") Double importoMin,
        @QueryParam("importoMax") Double importoMax,
        @QueryParam("numeroPagina") @DefaultValue("0") Integer numeroPagina,
        @QueryParam("dimensionePagina") @DefaultValue("10") Integer dimensionePagina
    );
    
}
