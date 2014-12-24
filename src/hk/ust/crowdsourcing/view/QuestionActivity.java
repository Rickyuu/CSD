package hk.ust.crowdsourcing.view;

import hk.ust.crowdsourcing.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

@ContentView(R.layout.activity_question)
public class QuestionActivity extends Activity {

	@ViewInject(R.id.question_content)
	EditText question;
	@ViewInject(R.id.given_credit_value)
	EditText credit;
	@ViewInject(R.id.count_content)
	EditText count;
	@ViewInject(R.id.period_content)
	EditText period;
	@OnClick(R.id.question_submit_button)
	public void submitButtonClick(View v){
		//验证问题内容是否为空，如果为空，拒绝提交并给出提醒
		String questionString = question.getText().toString();
		String creditString = credit.getText().toString();
		String countString = count.getText().toString();
		String periodString = period.getText().toString();
		if(questionString.equals("")) {
			Toast.makeText(getApplicationContext(), "问题不能为空！", Toast.LENGTH_SHORT).show();
			return;
		}
		creditString = (creditString.equals("")) ? "2" : creditString;
		countString = (countString.equals("")) ? "5" : countString;
		periodString = (periodString.equals("")) ? "5" : periodString;
		//向服务器提交问题
		RequestParams params = new RequestParams();
		SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
		String userIdString = userInfo.getString("userId", "1");
		// user_id, question, credit, count, period     返回quest_id（id=0代表提问失败，其他数字代表quest标号）
		params.addBodyParameter("user_id", userIdString);
		params.addBodyParameter("question", questionString);
		params.addBodyParameter("credit", creditString);
		params.addBodyParameter("count", countString);
		params.addBodyParameter("period", periodString);
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.POST, "http://1.crowdsourcingapp.sinaapp.com/index.php/question", params, 
				new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String questId = responseInfo.result;
				if(!questId.equals("0")) {
					Toast.makeText(getApplicationContext(), "提问成功！", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "提问失败！", Toast.LENGTH_SHORT).show();
				}
				//回退到上一级页面
				QuestionActivity.this.finish();
			}

		});
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
	}
}