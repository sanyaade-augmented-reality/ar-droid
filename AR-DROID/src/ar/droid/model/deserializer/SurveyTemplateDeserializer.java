package ar.droid.model.deserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ar.droid.admin.survey.SurveyTemplate;
import ar.droid.admin.survey.question.Question;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class SurveyTemplateDeserializer implements JsonDeserializer<SurveyTemplate> {

	@Override
	public SurveyTemplate deserialize(JsonElement json, Type type, JsonDeserializationContext context)throws JsonParseException {
		// TODO Auto-generated method stub
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Question.class, new QuestionDeserializer());
		Gson gson =	gsonBuilder.create();	
		SurveyTemplate surveyTemplate = new SurveyTemplate();
		surveyTemplate.setId(json.getAsJsonObject().get("id").getAsLong());
		surveyTemplate.setDescription(json.getAsJsonObject().get("description").getAsString());
		Type listType = new TypeToken<ArrayList<Question>>() {}.getType();
		List<Question> xLsResult  = gson.fromJson(json.getAsJsonObject().get("questions"),listType);
		surveyTemplate.setQuestions(xLsResult);
		return surveyTemplate;
	}

}
