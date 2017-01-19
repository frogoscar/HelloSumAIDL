package com.android.hellosumaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/*
 * This class exposes the service to client
 * 服务端，将服务（service）"暴露"给客户端（client）
 */
public class AdditionService extends Service {
  public AdditionService() {
  }

  @Override
  public IBinder onBind(Intent intent) {
    return new IAdditionService.Stub() {
      /*
       * Implement com.android.hellosumaidl.IAdditionService.add(int, int)
       * 实现了add方法
       */
      @Override
      public int add(int value1, int value2) throws RemoteException {
        return value1 + value2;
      }
    };
  }
}