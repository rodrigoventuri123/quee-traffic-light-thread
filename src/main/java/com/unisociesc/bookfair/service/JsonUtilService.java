package com.unisociesc.bookfair.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.unisociesc.bookfair.domain.Genre;
import com.unisociesc.bookfair.domain.Request;
import com.unisociesc.bookfair.domain.School;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JsonUtilService {

    public List<Genre> convertJsonInListGenre(JsonArray jsonArray) {
        List<Genre> genreList = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            Genre genre = new Genre(jsonElement.getAsJsonObject().get("id").getAsLong(), jsonElement.getAsJsonObject().get("nome").getAsString(),
                    jsonElement.getAsJsonObject().get("preco").getAsDouble(), jsonElement.getAsJsonObject().get("quantidade").getAsInt());
            genreList.add(genre);
        }
        return genreList;
    }

    public List<School> convertJsonInListSchool(JsonArray jsonArray) {
        List<School> schoolList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            List<Request> requests = this.convertJsonInListRequest(jsonElement.getAsJsonObject().get("pedidos").getAsJsonArray());
            School school = new School(jsonElement.getAsJsonObject().get("nome").getAsString(), requests);
            schoolList.add(school);
        }
        return schoolList;
    }

    public List<Request> convertJsonInListRequest(JsonArray jsonArray) {
        List<Request> requestList = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            Request request = new Request(jsonElement.getAsJsonObject().get("genero_id").getAsLong(), jsonElement.getAsJsonObject().get("quantidade").getAsInt());
            requestList.add(request);
        }

        return requestList;

    }

}
