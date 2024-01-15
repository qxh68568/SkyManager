package logica;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import dto.Temperaturas;
import org.json.JSONObject;

public class ServiceREST {

    public static Temperaturas serviceSearch(String codMunicipio) throws UnirestException {
        Temperaturas grados = null;

        HttpResponse<JsonNode> jsonResponse
                = Unirest.get("https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/" + codMunicipio)
                        .header("accept", "application/json")
                        .queryString("api_key", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0b21pYXIxN0BnbWFpbC5jb20iLCJqdGkiOiJhZTk4YzRmNS02YTZkLTRkNDctODZiZS1jZmQ0NjkzY2ZiMzciLCJpc3MiOiJBRU1FVCIsImlhdCI6MTcwMzAyMjI2NSwidXNlcklkIjoiYWU5OGM0ZjUtNmE2ZC00ZDQ3LTg2YmUtY2ZkNDY5M2NmYjM3Iiwicm9sZSI6IiJ9.jvI6hoSTlJJdNJkQfUZrY7FKTNgcRSAyLZbRSYVzD2c")
                        .asJson();

        String uErreEle = jsonResponse.getBody().getObject().getString("datos");
        HttpResponse<JsonNode> jsonResponse2
                = Unirest.get(uErreEle)
                        .header("accept", "application/json")
                        .asJson();

        JSONObject temp = jsonResponse2.getBody().getArray().getJSONObject(0)
                .getJSONObject("prediccion").getJSONArray("dia").getJSONObject(0)
                .getJSONObject("temperatura");
        grados = new Temperaturas(codMunicipio, temp.getInt("maxima"), temp.getInt("minima"));

        return grados;

    }
}
