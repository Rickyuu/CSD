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

@ContentView(R.layout.activity_question_list)
public class QuestionListActivity extends Activity {

	@ViewInject(R.id.question_listview)
	ListView questionListView;

	public final static String EXTRA_MESSAGE = "hk.ust.crowdsourcing.view.MESSAGE";
	private SimpleAdapter adapter;
	private JSONArray results;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);

		try {
			String questionListString = getIntent().getStringExtra("questionListString");
			results = new JSONArray(questionListString);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		// 将数据赋给listview
		adapter = new SimpleAdapter(QuestionListActivity.this, getData(),
				R.layout.listview_question_list, new String[] { "question",
						"credit", "deadline" }, new int[] {
						R.id.listview_question, R.id.listview_credit,
						R.id.listview_deadline });
		questionListView.setAdapter(adapter);

		questionListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				try {
					Intent answerIntent = new Intent(QuestionListActivity.this,
							AnswerActivity.class);
					answerIntent.putExtra(EXTRA_MESSAGE, results.getJSONObject((int)id).toString());
					QuestionListActivity.this.startActivity(answerIntent);
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
				// get问题列表界面 user_id 返回JSONArray，里面包含：solve_id, question,
				// credit, time_left（启动）
				table.put("question",
						results.getJSONObject(i).getString("question"));
				table.put("credit", results.getJSONObject(i)
						.getString("credit"));
				table.put("deadline",
						results.getJSONObject(i).getString("time_left"));
				list.add(table);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}