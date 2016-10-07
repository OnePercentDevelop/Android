import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

public class FCM {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PushMessage("dSnl0LXOBAA:APA91bH30JEu6csMBroqVgZXrX_aovOBW9kZJJ8QMJH1X95iaqrmpozxw3LPUKdsFOKvVCHHLx0jVUwjjzSp1z6RYbN1SCFtcLO68BVHq-hV5hoVmYIABArA7DbZOVdRrwEfQ4FJIXr9", "hello");
	}
	
	static void PushMessage(String deviceToken, String sendMsg){ // 디바이스 토큰, 보낼 메세지
		 Sender sender = new Sender( "AIzaSyCxE2vBXE_-3aWxKO62K9-caos_6iauXTk"); // 서버 API Key 입력
         String regId = deviceToken; 
         
         Message message = new Message.Builder().addData("message", sendMsg).build();
		  List<String> list = new ArrayList<String>();
		  list.add(regId);
		  MulticastResult multiResult;
		  try {
		      multiResult = sender.send(message, list, 5);
		      if (multiResult != null) {
		          List<com.google.android.gcm.server.Result> resultList = multiResult.getResults();
		          for (com.google.android.gcm.server.Result result : resultList) {
		              System.out.println(result.getMessageId());
		          }
		      }
		  } catch (IOException e) {
		      e.printStackTrace();
		  }
	}
}
