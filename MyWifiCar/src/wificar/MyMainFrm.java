package wificar;

import java.net.URL;
import my.wificar.R;
import wificar.MySurfaceView;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;

public class MyMainFrm extends Activity {

	EditText edIP,edCtrlIP,edCtrlPort;
	Button Button_go;
	URL videoUrl;
	CheckBox cBox;
	public static String CameraIp;
	public static String CtrlIp;
	public static String CtrlPort;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		setContentView(R.layout.mymainfrm);	
		
		edIP = (EditText) findViewById(R.id.editIP);
		edCtrlIP = (EditText) findViewById(R.id.editCtrlIP);
		edCtrlPort = (EditText) findViewById(R.id.editCtrlPort);
		Button_go = (Button) findViewById(R.id.button_go);
		cBox = (CheckBox) findViewById(R.id.checkBox_Scale);
		
		CameraIp = edIP.getText().toString();
		CtrlIp = edCtrlIP.getText().toString();
		CtrlPort = edCtrlPort.getText().toString();
		
		Button_go.requestFocusFromTouch();

        
		Button_go.setOnClickListener(new Button.OnClickListener() 
		{
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			Intent intent = new Intent();
    			intent.putExtra("CameraIp", CameraIp);
    			intent.putExtra("CtrlIp", CtrlIp);
    			intent.putExtra("CtrlPort", CtrlPort);
    			intent.putExtra("Is_Scale", cBox.isChecked());
    			intent.setClass(MyMainFrm.this, MyVideo.class);
    			MyMainFrm.this.startActivity(intent);
    			
    			 finish();  
	             System.exit(0);  
    		}
    	});
    
	}
	
	private long exitTime = 0;
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)   
    {  
                 if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)  
                 {  
                           
                         if((System.currentTimeMillis()-exitTime) > 2000)     
                         {  
                          Toast.makeText(getApplicationContext(), "Press Again to Exit",Toast.LENGTH_SHORT).show();                                  
                          exitTime = System.currentTimeMillis();  
                         }  
                         else  
                         {  
                             finish();  
                             System.exit(0);  
                         }  
                                   
                         return true;  
                 }  
                 return super.onKeyDown(keyCode, event);  
    }  

        

   

}


