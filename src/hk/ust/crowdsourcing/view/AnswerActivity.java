package hk.ust.crowdsourcing.view;

import hk.ust.crowdsourcing.R;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.activity_answer)
public class AnswerActivity extends Activity {

	private String solveIdString;
	
	@ViewInject(R.id.answer_question_content)
	TextView question;

	@ViewInject(R.id.answer_credit_content)
	TextView credit;

	@ViewInject(R.id.answer_deadline_content)
	TextView deadline;

	@ViewInject(R.id.answer_answer_content)
	EditText answer;

	@OnClick(R.id.answer_submit_button)
	public void submitButtonClick(View v) {
		// 验证答案不能为空，如果为空，拒绝提交并给出提醒
		String answerString = answer.getText().toString();
		if(answerString.equals("")) {
			Toast.makeText(getApplicationContext(), "答案不能为空！", Toast.LENGTH_SHORT).show();
			return;
		}
		// 给服务器post数据，包括问题ID，回答者ID，答案
		RequestParams params = new RequestParams();
		params.addBodyParameter("solve_id", solveIdString);
		params.addBodyParameter("answer", answerString);
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.POST, "http://1.crowdsourcingapp.sinaapp.com/index.php/answer", params, 
				new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String questId = responseInfo.result;
				if(!questId.equals("0")) {
					Toast.makeText(getApplicationContext(), "回答成功！", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "回答失败！", Toast.LENGTH_SHORT).show();
				}
				//回退到上一级页面
				AnswerActivity.this.finish();
			}

		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);

		Intent intent = getIntent();
		String message = intent.getStringExtra(QuestionListActivity.EXTRA_MESSAGE);
		try {
			JSONObject messageObject = new JSONObject(message);
			solveIdString = messageObject.getString("solve_id");
			question.setText(messageObject.getString("question"));
			credit.setText(messageObject.getString("credit"));
			deadline.setText(messageObject.getString("time_left"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}