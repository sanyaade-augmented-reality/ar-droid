package ar.droid.activity;

import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import ar.droid.R;
import ar.droid.admin.survey.question.Choice;
import ar.droid.admin.survey.question.IQuestionListener;
import ar.droid.admin.survey.question.MultipleChoiceQuestion;
import ar.droid.admin.survey.question.NumericValueQuestion;
import ar.droid.admin.survey.question.TextValueQuestion;
import ar.droid.admin.survey.response.MultipleChoiceResponse;
import ar.droid.admin.survey.response.NumericValueResponse;
import ar.droid.admin.survey.response.Summary;
import ar.droid.admin.survey.response.SurveyResponse;
import ar.droid.admin.survey.response.TextValueResponse;
import ar.droid.config.AppPreferences;
import ar.droid.model.Entity;
import ar.droid.model.Event;
import ar.droid.model.Resource;
import ar.droid.model.ResourceHelperFactory;
import ar.droid.resources.ImageHelperFactory;
import ar.droid.sound.SoundManager;

public class EventActivity extends Activity  implements android.view.View.OnClickListener,IQuestionListener {
	
		private Event event;
		private Entity entity;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.view_event);
			
			 //recupero la entidad
			entity = Resource.getInstance().getEntity(getIntent().getExtras().getLong("idEntity"));
			event = entity.getEvent(getIntent().getExtras().getLong("idEvent"));
			
			// registrar visita cliente
			ResourceHelperFactory.createResourceHelper().makeVisit(event);
			
			//recuperar comentarios eventos
			Summary summary = Resource.getInstance().getSummary(event);
			
			TextView title = (TextView) this.findViewById(R.id.title);
	        title.setText(event.getTitle());
	        
	        TextView subtitle = (TextView) this.findViewById(R.id.sutbtitle);
	        subtitle.setTextColor(Color.parseColor("#"+event.getTypeEvent().getColor()));
	        subtitle.setText(event.getTypeEvent().getDescription());
	       
	        TextView rank = (TextView) this.findViewById(R.id.rank);
	        rank.setText(summary.getDescription());
	        RatingBar ratingbar = (RatingBar)this.findViewById(R.id.ratingBar);
	        if (summary.getRating() == -1){
	        	ratingbar.setVisibility(View.INVISIBLE);
	        	ratingbar.getLayoutParams().height = 0;
	        }
	        else{
	            ratingbar.setRating(summary.getRating());
		    }
	        
	        ListView comments = (ListView) this.findViewById(R.id.comments);
	        if (!summary.getComments().isEmpty()){
	        	rank.setAutoLinkMask(0);
	        	//Pattern pattern = Pattern.compile("Ver Todos");
	        	//rank.setText(summary.getDescription() + "-  Ver Todos" );
	        	//Linkify.addLinks(rank,pattern, entity.getUrl(),null,null);
	        	rank.setMovementMethod(LinkMovementMethod.getInstance());
	        	
	        	String urlServer = AppPreferences.getString("urlServerPref", "http://www.gabrielnegri.com.ar:8080/server");
	        	
	        	String text = summary.getDescription()+ "   <a href=\"" + urlServer + "/event/comentarios/" + event.getId() + ">Ver Todos</a>";
	        	rank.setText(Html.fromHtml(text));
	        	comments.setSelected(false);
	        	
	        	comments.getLayoutParams().height = summary.getComments().size() * 40;
	        	comments.setAdapter(new ArrayAdapter<String>(this,R.layout.row_list_comment ,R.id.comment,summary.getComments()));
	        }
	        else{
	        	comments.setVisibility(View.INVISIBLE);
	        	comments.getLayoutParams().height = 0;
	        }
	        
	       //se recupera el icono a mostrar para el tipo de entidad
			Drawable imageEvent =ImageHelperFactory.createImageHelper().getImageEvent(event);
			imageEvent.setBounds(0, 0, imageEvent.getIntrinsicWidth(), imageEvent.getIntrinsicHeight());
			
	        ImageView image  = (ImageView)this.findViewById(R.id.imageEntity);
	        image.setImageDrawable(imageEvent);
	        
	        TextView url = (TextView) this.findViewById(R.id.site);
	        url.setText( Html.fromHtml("<a href=\'"+entity.getUrl()+"\'> "+entity.getName()+"</a>" ));
	        url.setMovementMethod(LinkMovementMethod.getInstance());
	        
	        TextView descr = (TextView) this.findViewById(R.id.description);
	        descr.setText(event.getDescription());
	        
	        TextView place = (TextView) this.findViewById(R.id.place);
	        if("".equals(event.getPlace()))
	        	place.setText("Lugar: No definido");
	        	place.setText("Lugar: " + event.getPlace());
	        
	     
	        TextView initdate = (TextView) this.findViewById(R.id.Initdate);
	        String linea1 = event.getEventCalendar().getLinea1();
	        initdate.setText(linea1);

	        TextView enddate = (TextView) this.findViewById(R.id.enddate);
	        String linea2 = event.getEventCalendar().getLinea2();
	        enddate.setText(linea2);
	        
	 	        
	        final Button btnShare= (Button) findViewById(R.id.buttonShare);
	        btnShare.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 showDialogCompartir();
	             }
	         });
	        
	        Button btbCommnet = (Button) findViewById(R.id.buttonComment);
	        
	        if (event.getSurveyResponse() ==null){
	            btbCommnet.setOnClickListener(new View.OnClickListener() {
		             public void onClick(View v) {
		            	 showDialogCommentEvent(v);
		             }
		         });
	        }
	        else{
	        	btbCommnet.setVisibility(View.INVISIBLE);
	        }
	        
		}
		
		
		@Override
		public void onClick(View v) {
			/*if (v.getId()==R.id.btn_ir_a){
				Bundle bundle = new Bundle();
				bundle.putLong("idEntity", entity.getId());
				bundle.putLong("idEvent", event.getId());
				Intent mIntent = new Intent();
	            mIntent.putExtras(bundle);
	            getParent().setResult(2,mIntent);
				finish();
			}*/
	        
	  }
		
		
		protected void showDialogCommentEvent(View v) {
			if (!event.getSurveyTemplate().getQuestions().isEmpty()){
				event.getSurveyTemplate().getQuestions().get(0).onQuestion(this);	
			}			
			//v.setVisibility(View.INVISIBLE);
		}
		
		protected void showDialogCompartir() {
			String urlServer = AppPreferences.getString("urlServerPref", "http://www.gabrielnegri.com.ar:8080/server");
			Intent sendIntent = new Intent(Intent.ACTION_SEND);
			
		    sendIntent.putExtra(Intent.EXTRA_SUBJECT,"Evento " + this.event.getTitle());
		    sendIntent.putExtra(Intent.EXTRA_TEXT,"Hola! Voy a asistir al evento " + this.event.getTitle() + 
		        	". Mas información en " + urlServer + "/event/evento/" + this.event.getId() + ".");
		    sendIntent.setType("text/plain");
		    startActivity(Intent.createChooser(sendIntent, "Compartir con"));
		}


		@Override
		public void doMultipleChoiceQuestion(final MultipleChoiceQuestion multipleChoiceQuestion) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(multipleChoiceQuestion.getQuestion());

			final String options[] = new String[multipleChoiceQuestion.getOptions().size()];
			final boolean selected[] = new boolean[multipleChoiceQuestion.getOptions().size()];
			
			Iterator<Choice> option = multipleChoiceQuestion.getOptions().iterator();
			int i =0;
			while (option.hasNext()) {
				Choice choice = (Choice) option.next();
				options[i] = choice.getDescription();
				selected[i] = false;	
				i++;
			}
		
			
			builder.setCancelable(true);
			
			
			if (multipleChoiceQuestion.getMaxOptions()>1){				
				builder.setMultiChoiceItems(options, selected, new DialogInterface.OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int item, boolean isChecked) {
						selected[item] = isChecked;
						//ir guardando las opciones elegidas	
					}
				});
			}
			else{
				builder.setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				    	selected[item] = true;
				    	
				    }
				});	
			}
			
			builder.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	//guardar la opción elegida
					SurveyResponse surveyResponse = new SurveyResponse();
					surveyResponse.setEvent(event);
					MultipleChoiceResponse multipleChoiceResponse = new MultipleChoiceResponse();
					multipleChoiceResponse.setMultipleChoiceQuestion(multipleChoiceQuestion);
					for (int j = 0; j < selected.length; j++) {
						if (selected[j]){
							multipleChoiceResponse.addOption(multipleChoiceQuestion.getOptions().get(j));		
						}
					}
					surveyResponse.addResponse(multipleChoiceResponse);
					ResourceHelperFactory.createResourceHelper().saveResponse(surveyResponse);
					event.setSurveyResponse(surveyResponse);
                }
            });

			builder.setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	//no hace nada
                }
            });
			
			SoundManager.playSound(R.raw.message);
			AlertDialog alert = builder.create();
			alert.show();
		}


		@Override
		public void doNumericValueQuestion(final NumericValueQuestion numericValueQuestion) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(numericValueQuestion.getQuestion());
			
			final RatingBar ratingBar = new RatingBar(getApplicationContext());
			ratingBar.setRating(0f);
			ratingBar.setMax(numericValueQuestion.getLimitTo());
			ratingBar.setStepSize(0.5f);
			
			/*final EditText input = new EditText(this);
		    input.setInputType(InputType.TYPE_CLASS_NUMBER);*/
		    
		    builder.setView(ratingBar);

			
			builder.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	//armar respuesta
                	SurveyResponse surveyResponse = new SurveyResponse();
					surveyResponse.setEvent(event);
					NumericValueResponse numericValueResponse = new NumericValueResponse();
					numericValueResponse.setNumericValueQuestion(numericValueQuestion);
					
					numericValueResponse.setValue(new Float(ratingBar.getRating()).intValue());
					surveyResponse.addResponse(numericValueResponse);
					
					ResourceHelperFactory.createResourceHelper().saveResponse(surveyResponse);
					
					event.setSurveyResponse(surveyResponse);
                }
            });
			
			builder.setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	//no hace nada
                }
            });
			
			SoundManager.playSound(R.raw.message);
			AlertDialog alert = builder.create();
			alert.show();
		}


		@Override
		public void doTextValueQuestion(final TextValueQuestion textValueQuestion) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(textValueQuestion.getQuestion());
			
			final EditText input = new EditText(this);
		    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
		    input.setLines(2);
		    builder.setView(input);

			builder.setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	//armar respuesta
                	SurveyResponse surveyResponse = new SurveyResponse();
					surveyResponse.setEvent(event);
					TextValueResponse textValueResponse = new TextValueResponse();
					textValueResponse.setTextValueQuestion(textValueQuestion);
					textValueResponse.setComment(input.getText().toString());
					surveyResponse.addResponse(textValueResponse);
					ResourceHelperFactory.createResourceHelper().saveResponse(surveyResponse);
					
					event.setSurveyResponse(surveyResponse);
                }
            });
			
			builder.setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	//no hace nada
                }
            });
			
			SoundManager.playSound(R.raw.message);
			AlertDialog alert = builder.create();
			alert.show();
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			SoundManager.playSound(R.raw.action);
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.menu_events, menu);
			return true;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			SoundManager.playSound(R.raw.button);
			// Handle item selection
			switch (item.getItemId()) {
				case R.id.menu_goto_event:
					Bundle bundle = new Bundle();
					bundle.putLong("idEvent", event.getId());
					bundle.putLong("idEntity", event.getIdEntity());
					Intent mIntent = new Intent();
		            mIntent.putExtras(bundle);
		            getParent().setResult(RESULT_OK,mIntent);
					finishFromChild(this);
					return true;
				case R.id.menu_close:
		            finishFromChild(this);
					return true;
				default:
					return super.onOptionsItemSelected(item);
			}
		}
}
