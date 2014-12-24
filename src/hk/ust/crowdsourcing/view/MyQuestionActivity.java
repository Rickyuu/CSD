package hk.ust.crowdsourcing.view;

import hk.ust.crowdsourcing.R;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_my_questioin)
public class MyQuestionActivity extends Activity {

	@ViewInject(R.id.my_question_listview)
	ListView myQuestionListView;

	public final static String EXTRA_MESSAGE = "hk.ust.crowdsourcing.view.MESSAGE";
	private SimpleAdapter adapter;

	private JSONArray results;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);

		// 访问服务器，服务器返回ＪＳＯＮＡｒｒａｙ其中包括所有已经得到答案的ｑｕｅｓｔｉｏｎ
		try {
			String myQuestionListString = getIntent().getStringExtra("myQuestionListString");
			results = new JSONArray(myQuestionListString);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		// 将数据赋给listview
		adapter = new SimpleAdapter(MyQuestionActivity.this, getData(),
				R.layout.listview_my_question, new String[] { "question",
						"answer", "count", "credit", "period", "status" },
				new int[] { R.id.listview_question, R.id.listview_answer,
						R.id.listview_count, R.id.listview_credit,
						R.id.listview_period, R.id.listview_status });
		myQuestionListView.setAdapter(adapter);

		myQuestionListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					Intent feedbackIntent = new Intent(MyQuestionActivity.this,
							FeedbackActivity.class);
					feedbackIntent.putExtra(EXTRA_MESSAGE, results.getJSONObject((int)id).toString());
					MyQuestionActivity.this.startActivity(feedbackIntent);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		});
	}

	private List<Hashtable<String, Object>> getData() {
		List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();
		for (int i = 0; i < results.length(); i++) {

			Hashtable<String, Object> table = new Hashtable<String, Object>();

			try {
				table.put("question", results.getJSONObject(i).getString("question"));
				table.put("answer", results.getJSONObject(i).getString("answer"));
				table.put("count", results.getJSONObject(i).getString("count"));
				table.put("credit", results.getJSONObject(i).getString("credit"));
				table.put("period", results.getJSONObject(i).getString("period"));
				table.put("status", results.getJSONObject(i).getString("status"));
				list.add(table);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
