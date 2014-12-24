package hk.ust.crowdsourcing.view;

import hk.ust.crowdsourcing.R;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_my_answer)
public class MyAnswerActivity extends Activity {

	@ViewInject(R.id.my_answer_listview)
	ListView myAnswerListView;

	private SimpleAdapter adapter;
	private JSONArray results;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);

		// 访问服务器，服务器返回ＪＳＯＮＡｒｒａｙ其中包括所有当前用户已经回答的ｑｕｅｓｔｉｏｎ
		try {
			String myAnswerListString = getIntent().getStringExtra("myAnswerListString");
			results = new JSONArray(myAnswerListString);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		// 将数据赋给listview
		adapter = new SimpleAdapter(MyAnswerActivity.this, getData(),
				R.layout.listview_my_answer, new String[] { "question",
						"answer", "credit" }, new int[] {
						R.id.listview_question, R.id.listview_answer,
						R.id.listview_credit });
		myAnswerListView.setAdapter(adapter);

	}

	private List<Hashtable<String, Object>> getData() {
		List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();

		for (int i = 0; i < results.length(); i++) {

			Hashtable<String, Object> table = new Hashtable<String, Object>();

			try {
				table.put("question", results.getJSONObject(i).getString("question"));
				table.put("answer", results.getJSONObject(i).getString("answer"));
				table.put("credit", results.getJSONObject(i).getString("credit"));
				list.add(table);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
