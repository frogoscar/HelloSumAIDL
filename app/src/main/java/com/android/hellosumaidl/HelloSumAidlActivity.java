package com.android.hellosumaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HelloSumAidlActivity extends AppCompatActivity {
  IAdditionService service;
  AdditionServiceConnection connection;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hello_sum_aidl);

    initService();

    Button buttonCalc = (Button)findViewById(R.id.buttonCalc);
    buttonCalc.setOnClickListener(new View.OnClickListener() {
      EditText value1 = (EditText)findViewById(R.id.value1);
      EditText value2= (EditText)findViewById(R.id.value2);
      TextView result = (TextView)findViewById(R.id.result);
      @Override
      public void onClick(View v) {
        int v1, v2, res = -1;
        v1 = Integer.parseInt(value1.getText().toString());
        v2 = Integer.parseInt(value2.getText().toString());

        try {
          res = service.add(v1, v2);
        } catch (RemoteException e) {
          e.printStackTrace();
        }

        result.setText(Integer.valueOf(res).toString());
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    releaseService();
  }

  /*
     * This inner class is used to connect to the service
     * 这个内部类用于连接到服务（service）
     */
  class AdditionServiceConnection implements ServiceConnection {

    @Override
    public void onServiceConnected(ComponentName name, IBinder boundService) {
      service = IAdditionService.Stub.asInterface(boundService);
      Toast.makeText(HelloSumAidlActivity.this, "Service connected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      service = null;
      Toast.makeText(HelloSumAidlActivity.this, "Service disconnected", Toast.LENGTH_LONG).show();
    }
  }

  /*
   * This method connects the Activity to the service
   * 这个方法使Activity（客户端）连接到服务（service）
   */
  private void initService() {
    connection = new AdditionServiceConnection();
    Intent i = new Intent();
    i.setClassName("com.android.hellosumaidl", com.android.hellosumaidl.AdditionService.class.getName());
    bindService(i, connection, Context.BIND_AUTO_CREATE);
  }

  /*
   * This method disconnects the Activity from the service
   * 这个方法使Activity（客户端）从服务（service）断开
   */
  private void releaseService() {
    unbindService(connection);
    connection = null;
  }
}
