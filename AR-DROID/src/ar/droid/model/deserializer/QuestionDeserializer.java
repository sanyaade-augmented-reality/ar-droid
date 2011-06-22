package ar.droid.model.deserializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import ar.droid.admin.survey.question.Choice;
import ar.droid.admin.survey.question.MultipleChoiceQuestion;
import ar.droid.admin.survey.question.NumericValueQuestion;
import ar.droid.admin.survey.question.Question;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class QuestionDeserializer implements JsonDeserializer<Question> {
	
	public Question deserialize(JsonElement json, Type type,JsonDeserializationContext context) throws JsonParseException {
		Log.i("class **** ",json.getAsJsonObject().get("class").getAsString());
		String nameClass = json.getAsJsonObject().get("class").getAsString();
		try {
			Class<?> c = Class.forName(nameClass);
			Question object = (Question)c.newInstance();
			object.setId(json.getAsJsonObject().get("id").getAsLong());
			object.setQuestion(json.getAsJsonObject().get("question").getAsString());
			
			if(json.getAsJsonObject().get("maxOptions")!= null && !json.getAsJsonObject().get("maxOptions").isJsonNull()){
				((MultipleChoiceQuestion)object).setMaxOptions(json.getAsJsonObject().get("maxOptions").getAsInt());
				//Se mapean las opciones
				Type listType = new TypeToken<ArrayList<Choice>>() {}.getType();
				GsonBuilder gsonBuilder = new GsonBuilder();
				Gson gson =	gsonBuilder.create();	
				List<Choice> xLsResult  = gson.fromJson(json.getAsJsonObject().get("options"),listType);
				((MultipleChoiceQuestion)object).setOptions(xLsResult);
			}
			else if (json.getAsJsonObject().get("limitTo") != null && !json.getAsJsonObject().get("limitTo").isJsonNull()){
				((NumericValueQuestion)object).setLimitFrom(json.getAsJsonObject().get("limitFrom").getAsInt());
				((NumericValueQuestion)object).setLimitTo(json.getAsJsonObject().get("limitTo").getAsInt());
			}
			return object;
		} catch (ClassNotFoundException e) {
			Log.e("Question deserialize", "No se encontro la clase", e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}

}
