package ie.simo.movies.activities;

import java.util.ArrayList;

import ie.simo.movies.R;
import ie.simo.movies.generator.ProductionEventGenerator;
import ie.simo.movies.production.ProductionEvent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Production extends ActivityWithMenu {

	private ProgressBar productionProgress;
	private TextView progressText;
	private ListView productionNews;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> listItems = new ArrayList<String>();
	private Handler handler;
	private ProductionEventGenerator eventGenerator = new ProductionEventGenerator();

	public void onCreate(Bundle savedInstanceState) {
		listItems.add("test item!!");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.production);
		findAllViewsById();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);
		productionNews.setAdapter(adapter);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				adapter.notifyDataSetChanged();
			}
		};
		startProduction();
		//ProgressStepTask stepper = new ProgressStepTask();
		//stepper.execute(null);
	}

	private void findAllViewsById() {
		productionProgress = (ProgressBar) findViewById(R.id.productionProgress);
		progressText = (TextView) findViewById(R.id.productionprogresstv);
		productionNews = (ListView) findViewById(android.R.id.list);
	}

	private void updateList() {
		adapter.notifyDataSetChanged();
	}
	
	public void startProduction(){
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				
				final String effects = "Good buzz changed by %d. Bad buzz changed by %d. ";
				while (productionProgress.getProgress() < 100) {
					try {
						Thread.sleep(100);
						productionProgress.setProgress(productionProgress.getProgress() + 1);
	
						handler.post(new Runnable() {
							@Override
					        public void run() {
								if (productionProgress.getProgress() % 10 == 0) {
									listItems.add(0, String.format("Filming is now %s complete.", productionProgress.getProgress() + "%"));
									adapter.notifyDataSetChanged();
								}
								if(eventGenerator.isDueEvent()){
									handle(eventGenerator.nextEvent());
								}
							}

							private void handle(ProductionEvent nextEvent) {
								listItems.add(0,nextEvent.getDescription() + String.format(effects, nextEvent.getGoodBuzz(), nextEvent.getBadBuzz()));
								adapter.notifyDataSetChanged();
							}
					    });
							
					} catch (Exception e) {
					}
				}
			}
		};
		new Thread(runnable).start();
	}
}
