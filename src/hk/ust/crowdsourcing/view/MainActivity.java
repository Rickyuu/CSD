package hk.ust.crowdsourcing.view;

import hk.ust.crowdsourcing.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends Activity {

	
	String questionListString = null;
	String myQuestionListString = null;
	String myAnswerListString = null;
	@ViewInject(R.id.credit_value)
	TextView credit;

	@OnClick(R.id.question_button)
	public void questionButtonClick(View v) {
		// 直接跳转到QuestionActivity界面
		Intent questionIntent = new Intent(MainActivity.this,
				QuestionActivity.class);
		MainActivity.this.startActivity(questionIntent);
	}

	@OnClick(R.id.answer_button)
	public void answerButtonClick(View v) {
		
		// 访问服务器，服务器返回ＪＳＯＮＡｒｒａｙ其中包括所有当前用户可以回答的ｑｕｅｓｔｉｏｎ
				SharedPreferences userInfo = getSharedPreferences("userInfo",
						MODE_PRIVATE);
				String userIdString = userInfo.getString("userId", "0");
				HttpUtils httpUtils = new HttpUtils();
				httpUtils.send(HttpMethod.GET,
						"http://1.crowdsourcingapp.sinaapp.com/index.php/question_list/"
								+ userIdString, new RequestCallBack<String>() {

							@Override
							public void onFailure(HttpException error, String msg) {
								// TODO
								Log.i("ly", msg);
							}

							@Override
							public void onSuccess(ResponseInfo<String> responseInfo) {
								Log.i("ly", responseInfo.result);
								questionListString = responseInfo.result;
								Intent questionListIntent = new Intent(MainActivity.this,
										QuestionListActivity.class);
								questionListIntent.putExtra("questionListString", questionListString);
								MainActivity.this.startActivity(questionListIntent);
							}

						});
		
	}

	@OnClick(R.id.my_question_button)
	public void myQuestionButtonClick(View v) {
		// get我的问题界面     user_id     返回JSONArray，里面包含：quest_id, question, answer（值null）, credit, count, period, status（有结束1、没结束0、已评价2三种）
		SharedPreferences userInfo = getSharedPreferences("userInfo",
				MODE_PRIVATE);
		String userIdString = userInfo.getString("userId", "0");
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET,
				"http://1.crowdsourcingapp.sinaapp.com/index.php/my_question/"
						+ userIdString, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO
						Log.i("ly", msg);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Log.i("ly", responseInfo.result);
						myQuestionListString = responseInfo.result;
						Intent myQuestionListIntent = new Intent(MainActivity.this,
								MyQuestionActivity.class);
						myQuestionListIntent.putExtra("myQuestionListString", myQuestionListString);
						MainActivity.this.startActivity(myQuestionListIntent);
					}

				});
	}

	@OnClick(R.id.my_answer_button)
	public void myAnswerButtonClick(View v) {
		// get我的答案界面     user_id     返回JSONArray，里面包含：solve_id, question, answer, credit（这里都是已经结束的）
		SharedPreferences userInfo = getSharedPreferences("userInfo",
				MODE_PRIVATE);
		String userIdString = userInfo.getString("userId", "0");
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET,
				"http://1.crowdsourcingapp.sinaapp.com/index.php/answer_list/"
						+ userIdString, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO
						Log.i("ly", msg);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Log.i("ly", responseInfo.result);
						myAnswerListString = responseInfo.result;
						Intent myAnswerListIntent = new Intent(MainActivity.this,
								MyAnswerActivity.class);
						myAnswerListIntent.putExtra("myAnswerListString", myAnswerListString);
						MainActivity.this.startActivity(myAnswerListIntent);
					}

				});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		
		// 显示积分
		SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
		String userId = userInfo.getString("userId", "1");
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.GET, "http://1.crowdsourcingapp.sinaapp.com/index.php/credit/"+userId, 
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						credit.setText(responseInfo.result);
					}
			
		});
	}
}