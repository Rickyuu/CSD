package hk.ust.crowdsourcing.view;

import hk.ust.crowdsourcing.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

@ContentView(R.layout.activity_login)
public class LoginActivity extends Activity {

	@ViewInject(R.id.user_name_content)
	EditText user_name;
	
	@ViewInject(R.id.password_content)
	EditText password;
	
	/**
	 * post登陆     user_name, password     返回user_id
	 */
	@OnClick(R.id.login_button)
	public void loginButtonClick(View v){
		// 验证用户名和密码不能为空，如果为空，拒绝登陆并给出提醒
		final String userNameString = user_name.getText().toString();
		String passwordString = password.getText().toString();
		if(userNameString.equals("") || passwordString.equals("")) {
			Toast.makeText(getApplicationContext(), "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
			return;
		}
		// 给服务器发送请求，检验用户名和密码
		RequestParams params = new RequestParams();
		params.addBodyParameter("user_name", userNameString);
		params.addBodyParameter("password", passwordString);
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.send(HttpMethod.POST, "http://1.crowdsourcingapp.sinaapp.com/index.php/login", params, 
				new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String userIdString = responseInfo.result;
				if(userIdString.equals("0")) {
					Toast.makeText(getApplicationContext(), "用户名或密码错误！", Toast.LENGTH_SHORT).show();
					return;
				}
				// 服务器通过验证之后跳转到MainActivity界面
				SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
				Editor edit = userInfo.edit();
				edit.putString("userId", responseInfo.result);
				edit.putString("userName", userNameString);
				edit.commit();
				Intent mainpageIntent = new Intent(LoginActivity.this, MainActivity.class);
				LoginActivity.this.startActivity(mainpageIntent);
				LoginActivity.this.finish();
			}

		});
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
	}
}
