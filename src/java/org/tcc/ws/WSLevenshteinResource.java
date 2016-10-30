package org.tcc.ws;

import com.mongodb.BasicDBObject;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.tcc.util.Medicamentos;
import org.tcc.util.MongoDBConection;

/**
 * REST Web Service
 *
 */

@Stateless
@Path("/consulta")
public class WSLevenshteinResource {

 
   
    @GET
    @Produces("text/html")
    public String getGreeting( @QueryParam("pesquisa") String pesquisa) throws UnknownHostException {
        
        MongoDBConection db = new MongoDBConection("dbmedicamentos");

        List<BasicDBObject> objects = db.getAllDocs("catmat");
        int menortxLev = 15;
        String melhorPalavra = "";
        String cmpLev = "";

        System.out.println("Buscando: " + pesquisa);
        for (BasicDBObject ob : objects) {
            int idxof = ob.get("TIPL_DESCRICAO").toString().indexOf(',');
            if (idxof > 0) {
                cmpLev = ob.get("TIPL_DESCRICAO").toString().substring(0, idxof);
            } else {
                cmpLev = ob.get("TIPL_DESCRICAO").toString();
            }

            int txLev = StringUtils.getLevenshteinDistance(pesquisa, cmpLev);

            if (txLev < 20) {
                if (txLev < menortxLev) {
                    menortxLev = txLev;
                    melhorPalavra = cmpLev;
                }

            }

        };

        System.out.println("Menor valor txlev " + menortxLev + " Melhor texto: " + melhorPalavra);
        
        List<Medicamentos> med = db.doAdvancedSearch("\"" + melhorPalavra + "\"", "catmat");

       ArrayList<JSONObject> obj = new ArrayList<JSONObject>();
     
        JSONObject ob = new JSONObject();
        String rsJson="";
        
        for (Medicamentos m : med) {
            System.out.println("FTS search: " + m.getCodigo() + ":" + m.getDescricao());
            ob.put("codigo",m.getCodigo());
	         ob.put("descricao", m.getDescricao());
                 obj.add(ob);
        }
         for (JSONObject o: obj){
        rsJson+=o.toJSONString()+"\n";

    } 
        
        
        return "<html><body><h1>Melhor correspondência.</br>palavra buscada: "+pesquisa+"</br> Taxa de Levensthein: "+menortxLev+
                "</br> Palavra Correta: "+ melhorPalavra+"</h1>"
                + rsJson+"</body></html>";
    }

  
}
