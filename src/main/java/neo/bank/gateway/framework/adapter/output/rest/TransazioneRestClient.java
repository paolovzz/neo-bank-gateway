package neo.bank.gateway.framework.adapter.output.rest;


import java.time.LocalDate;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import neo.bank.gateway.domain.enums.TipologiaFlusso;

@Path("/transazioni")
@RegisterRestClient(configKey = "transazione-service")
public interface TransazioneRestClient {

   @Path("/iban/{iban}")
    @GET
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response recuperaTransazioni(
        @PathParam(value = "iban") String iban, 
        @QueryParam(value = "dataCreazioneMin" ) LocalDate dataCreazioneMin,
        @QueryParam(value = "dataCreazioneMax" ) LocalDate dataCreazioneMax,
        @QueryParam(value = "tipologiaFlusso" ) TipologiaFlusso tipologiaFlusso,
        @QueryParam(value = "importoMin" ) Double importoMin,
        @QueryParam(value = "importoMax" ) Double importoMax,
        @QueryParam(value = "numeroPagina") @DefaultValue("0") Integer numeroPagina,
        @QueryParam(value = "dimensionePagina") @DefaultValue("10") Integer dimensionePagina
    );
    
}
