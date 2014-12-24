package hk.ust.crowdsourcing.view;

import org.json.JSONException;
import org.json.JSONObject;

import hk.ust.crowdsourcing.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

@ContentView(R.layout.activity_feedback)
public class FeedbackActivity extends Activity {

	private String questIdString;
	
	@ViewInject(R.id.feedback_question_content)
	TextView questionTextView;

	@ViewInject(R.id.feedback_answer_content)
	TextView answerTextView;

	@ViewInject(R.id.feedback_count_content)
	TextView countTextView;
	
	@ViewInject(R.id.feedback_credit_content)
	TextView creditTextView;
	
	@ViewInject(R.id.feedback_period_content)
	TextView periodTextView;

	@OnClick(R.id.feedback_accept_button)
	public void acceptButtonClick(View v) {
		// 提交接受结果到服务器
		// post赞踩界面     quest_id, feedback（1表示赞，0表示踩）     返回01（1成功，0失败）
		RequestParams params = new RequestParams();
		params.addBodyParameter("quest_id", questIdString);
		params.addBodyParameter("feedback", "1");
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.POST, "http://1.crowdsourcingapp.sinaapp.com/index.php/feedback", params, 
				new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String questId = responseInfo.result;
				if(!questId.equals("0")) {
					Toast.makeText(getApplicationContext(), "赞成功！", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "赞失败！", Toast.LENGTH_SHORT).show();
				}
				//回退到上一级页面
				FeedbackActivity.this.finish();
			}
		});
	}

	@OnClick(R.id.feedback_deny_button)
	public void denyButtonClick(View v) {
		// 提交拒绝结果到服务器
		// post赞踩界面     quest_id, feedback（1表示赞，0表示踩）     返回01（1成功，0失败）
				RequestParams params = new RequestParams();
				params.addBodyParameter("quest_id", questIdString);
				params.addBodyParameter("feedback", "0");
				HttpUtils httpUtils = new HttpUtils();
				httpUtils.send(HttpMethod.POST, "http://1.crowdsourcingapp.sinaapp.com/index.php/feedback", params, 
						new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String questId = responseInfo.result;
						if(!questId.equals("0")) {
							Toast.makeText(getApplicationContext(), "踩成功！", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), "踩失败！", Toast.LENGTH_SHORT).show();
						}
						//回退到上一级页面
						FeedbackActivity.this.finish();
					}
				});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(MyQuestionActivity.EXTRA_MESSAGE);
		try {
			JSONObject messageObject = new JSONObject(message);
			questIdString = messageObject.getString("quest_id");
			questionTextView.setText(messageObject.getString("question"));
			answerTextView.setText(messageObject.getString("answer"));
			countTextView.setText(messageObject.getString("count"));
			creditTextView.setText(messageObject.getString("credit"));
			countTextView.setText(messageObject.getString("count"));
			periodTextView.setText(messageObject.getString("period"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}