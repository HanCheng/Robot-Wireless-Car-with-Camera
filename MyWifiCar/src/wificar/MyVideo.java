package wificar;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;

import my.wificar.R;

import android.R.color;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MyVideo extends Activity
{
    private Button ForWard;
	private Button BackWard;
	private Button TurnLeft;
	private Button TurnRight;

    URL videoUrl;
    private Thread mThreadClient = null;
	private Socket mSocketClient = null;

	public static String CameraIp;
	public static String CtrlIp;
	public static String CtrlPort;
	public static boolean Is_Scale;
    MySurfaceView r;
    
    private int Send_CMD_Status = 0;
    boolean Is_Lighted = false;
    
    private int Cam_LeftRight = 0x5A;
    private int Cam_UpDown = 0x7B;
    private int step = 2;
    private int Cam_Reset_Status = 0;

    
    
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.myvideo);
        r = (MySurfaceView)findViewById(R.id.mySurfaceViewVideo);
        
        WindowManager manager = getWindowManager();
        int Width = manager.getDefaultDisplay().getWidth();
        int Height = manager.getDefaultDisplay().getHeight();
        int btn_length = Height /8;        
        
        AbsoluteLayout.LayoutParams params;
        ForWard = (Button)findViewById(R.id.ForWard);
        params = (AbsoluteLayout.LayoutParams)ForWard.getLayoutParams();  
		params.width = btn_length;
		params.height = btn_length;
		params.x = btn_length;
		params.y = Height - btn_length*3;
		ForWard.setLayoutParams(params); 
        
        BackWard = (Button)findViewById(R.id.BackWard);
        params = (AbsoluteLayout.LayoutParams)BackWard.getLayoutParams();  
		params.width = btn_length;
		params.height = btn_length;
		params.x = btn_length;
		params.y = Height - btn_length;
		BackWard.setLayoutParams(params); 		
		
		TurnLeft = (Button)findViewById(R.id.TurnLeft);
		params = (AbsoluteLayout.LayoutParams)TurnLeft.getLayoutParams();  
		params.width = btn_length;
		params.height = btn_length;
		params.x = 0;
		params.y = Height - btn_length*2;
		TurnLeft.setLayoutParams(params); 		
		
		TurnRight = (Button)findViewById(R.id.TurnRight);
		params = (AbsoluteLayout.LayoutParams)TurnRight.getLayoutParams();  
		params.width = btn_length;
		params.height = btn_length;
		params.x = btn_length*2;
		params.y = Height - btn_length*2;
		TurnRight.setLayoutParams(params); 

		
		Intent intent = getIntent();
		CameraIp = intent.getStringExtra("CameraIp");		
		//CameraIp ="http://192.168.1.1:8080/?action=snapshot";
		CtrlIp = intent.getStringExtra("CtrlIp");
		CtrlPort = intent.getStringExtra("CtrlPort");
		Is_Scale = intent.getBooleanExtra("Is_Scale", false);
		r.GetCameraIP(CameraIp);
		r.Is_Scale = Is_Scale;

	    mThreadClient = new Thread(mRunnable);
		mThreadClient.start();
		
	
		BackWard.setOnTouchListener(new View.OnTouchListener() 
		{
			public boolean onTouch(View v, MotionEvent event) 
			{
				int action = event.getAction();
				switch(action)
				{
					case MotionEvent.ACTION_DOWN:
				    	CmdBuffer[1] = (byte)0x00;
						CmdBuffer[2] = (byte)0x02;
						CmdBuffer[3] = (byte)0x00;
						Send_CMD_Status = -1;							    
				    	break;
					case MotionEvent.ACTION_UP:
						CmdBuffer[1] = (byte)0x00;
						CmdBuffer[2] = (byte)0x00;
						CmdBuffer[3] = (byte)0x00;
						Send_CMD_Status = 1;				    
						break;
					default:
						break;
				}
				return false;
			}
		});
		
		ForWard.setOnTouchListener(new View.OnTouchListener() 
		{
			public boolean onTouch(View v, MotionEvent event) 
			{
				int action = event.getAction();
				switch(action)
				{
					case MotionEvent.ACTION_DOWN:
				    	//mPrintWriterClient.print("W"); 
				    	CmdBuffer[1] = (byte)0x00;
						CmdBuffer[2] = (byte)0x01;
						CmdBuffer[3] = (byte)0x00;
						Send_CMD_Status = -1;							    
				    	break;
					case MotionEvent.ACTION_UP:
						CmdBuffer[1] = (byte)0x00;
						CmdBuffer[2] = (byte)0x00;
						CmdBuffer[3] = (byte)0x00;
						Send_CMD_Status = 1;				    
						break;
					default:
						break;
				}
				return false;
			}
		});
		
		TurnLeft.setOnTouchListener(new View.OnTouchListener() 
		{
			public boolean onTouch(View v, MotionEvent event) 
			{
				int action = event.getAction();
				switch(action)
				{
					case MotionEvent.ACTION_DOWN:
				    	CmdBuffer[1] = (byte)0x00;
						CmdBuffer[2] = (byte)0x03;
						CmdBuffer[3] = (byte)0x00;
						Send_CMD_Status = -1;							    
				    	break;
					case MotionEvent.ACTION_UP:
						CmdBuffer[1] = (byte)0x00;
						CmdBuffer[2] = (byte)0x00;
						CmdBuffer[3] = (byte)0x00;
						Send_CMD_Status = 1;				    
						break;
					default:
						break;
				}
				return false;
			}
		});
		
		TurnRight.setOnTouchListener(new View.OnTouchListener() 
		{
			public boolean onTouch(View v, MotionEvent event) 
			{
				int action = event.getAction();
				switch(action)
				{
					case MotionEvent.ACTION_DOWN:
				    	CmdBuffer[1] = (byte)0x00;
						CmdBuffer[2] = (byte)0x04;
						CmdBuffer[3] = (byte)0x00;
						Send_CMD_Status = -1;							    
				    	break;
					case MotionEvent.ACTION_UP:
						CmdBuffer[1] = (byte)0x00;
						CmdBuffer[2] = (byte)0x00;
						CmdBuffer[3] = (byte)0x00;
						Send_CMD_Status = 1;				    
						break;
					default:
						break;
				}
				return false;
			}
		});
						
	}
	

    private byte[] CmdBuffer = {(byte) 0xFF,(byte)0x00,(byte)0x00,(byte)0x00,(byte) 0xFF};
	private Runnable mRunnable	= new Runnable() 
	{
		public void run()
		{
			while(true)
			{
				if(Send_CMD_Status==0) 
				{
					try 
					{
						Thread.sleep(50);
					} catch (InterruptedException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					continue;
				}
				if(Send_CMD_Status>0) Send_CMD_Status--;
				
				if(Cam_Reset_Status==2)
				{
					Cam_Reset_Status = 1;
					
					CmdBuffer[1] = (byte)0x01;
					CmdBuffer[2] = (byte)0x02;
					Cam_UpDown = 0x7B; 
					CmdBuffer[3] = (byte)Cam_UpDown;
				}
				else if(Cam_Reset_Status==1)
				{
					Cam_Reset_Status = 0;
					
					CmdBuffer[1] = (byte)0x01;
					CmdBuffer[2] = (byte)0x01;
					Cam_LeftRight = 0x5A; 
					CmdBuffer[3] = (byte)Cam_LeftRight;
					
				}
				else 
				{
					Cam_Reset_Status = 0;
				}

				
				try 
				{
					mSocketClient = new Socket(CtrlIp,Integer.parseInt(CtrlPort));					
					//mBufferedReaderClient = new BufferedReader(new InputStreamReader(mSocketClient.getInputStream()));
					//mPrintWriterClient = new PrintWriter(mSocketClient.getOutputStream(), true);
					mSocketClient.getOutputStream().write(CmdBuffer);
					//mSocketClient.getOutputStream().write(CmdBuffer);
					mSocketClient.close();
					
					Thread.sleep(100);
				}	
				catch (Exception ex) 
				{
					mSocketClient = null;
				}
				
				
				
			} 
			
		}
	};
	
		
	public void onDestroy() 
	{
		super.onDestroy();
	
		try 
		{
			if(mSocketClient!=null)
			{
				mSocketClient.close();
				mSocketClient = null;
			}
	
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		mThreadClient.interrupt();
	}
	
	private long exitTime = 0;
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event)   
    {  
		 if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)  
		 {  
		           
		         if((System.currentTimeMillis()-exitTime) > 2500)  
				 {  
					 Toast.makeText(getApplicationContext(), "Press Again to Exit ",Toast.LENGTH_SHORT).show();                                  
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


