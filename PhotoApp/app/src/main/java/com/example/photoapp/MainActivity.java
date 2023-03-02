package com.example.photoapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;

import java.io.File;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String TAG=this.getClass().getName();

    ActivityResultLauncher launcher; //권한 요청 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_internal=findViewById(R.id.bt_internal);
        Button bt_external=findViewById(R.id.bt_external);

        bt_internal.setOnClickListener((v)->{
            openInternal();
        });
        bt_external.setOnClickListener((v)->{
            openExternal();
        });



        /*안드로이드의 새로운 권한 정책으로 인하여, 앱의 시작과 동시에 사용자로부터
        안드로이드의 새로운 정책을 구현하기 위해서는  사용자에게 권한을 요청하고 수락을
        받아야 하는데, 이때 사용되는 객체가 바로 ActivityResultLauncher 라 하며
        이 객체의 인스턴스를 생성하기 위한 메서드는 registerForActivityResult() 라 한다

        매개변수1) 어떤 퍼미션을 요청햇는지에 대한 정보
                         ActivityResultContracts
        매개변수2) 사용자가 해당 요청에 대해 어떤 반응을 보였는지에 대한 정보를 처리할
                        콜백메서드
        */
        launcher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                Log.d(TAG, "요청에 대한 사용자의 반응 결과는 "+result);
            }
        });

        //권한에 대한 확인 및 수락을 받도록 하자 (단 마시멜로 이전 폰의 경우엔 허락불필요..)
        if(checkVersion()){
            //최신핸드폰이므로, 파일에 대한 접근보다는 사용자보부터 허락부터 받아야 한다
            checkGranted(); 
            //openExternal();
        }else{
            //구버전 핸드폰이므로 허락이고 뭐고 필요없이 그냥 파일접근하자
        }
    }

    public boolean checkVersion(){
        //마시멜로 폰 부터 새로운 정책을 적용해야 하므로, 현재 사용자의 폰이
        //어떤 버전인지를 파악하자
        Log.d(TAG, "SDK_INT 은 "+Build.VERSION.SDK_INT);
        Log.d(TAG, "VERSION_CODES 마시멜로는 "+Build.VERSION_CODES.M);
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            return true;
        }else{
            return false;
        }
    }

    //사용자로부터 권한수락을 요청하는 메서드
    public void checkGranted(){
        launcher.launch(new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        });
    }


    /*
    * 안드로이드의 저장소(storage)는 크게 2가지 유형으로 나뉜다
    * 1)내부저장소 - 외부저장소 중 , 해당 앱만이 전용으로 사용하는 저장소를
    *                      가리켜 내부저장소라 한다
    *                      해당앱이 폰에서 설치삭제되면, 저장소도 함께 삭제되어 진다.
    * 2)외부저장소 -
    * */
    public void openInternal(){

        //내부저장소  root
        File file=new File(this.getFilesDir(), "");
        Log.d(TAG, file.getAbsolutePath());
    }
    public void openExternal(){
        File storage=Environment.getExternalStorageDirectory();
        Log.d(TAG, "외부저장소 경로 "+storage.getAbsolutePath());

        //외부저장소의 하위 디렉토리 및 모든 파일의 목록을 조회해보자
        File[] files = storage.listFiles();
        Log.d(TAG, "하위 디렉토리 및 파일수는 "+files.length);
        for(File file : files){
            Log.d(TAG, file.getName());
        }
    }
}







