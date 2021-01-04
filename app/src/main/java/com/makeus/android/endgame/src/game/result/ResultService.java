package com.makeus.android.endgame.src.game.result;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.SocialObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.network.storage.ImageUploadResponse;
import com.kakao.util.helper.log.Logger;
import com.makeus.android.endgame.R;
import com.makeus.android.endgame.src.game.result.interfaces.ResultActivityView;
import com.makeus.android.endgame.src.game.result.interfaces.ResultRetrofitInterface;
import com.makeus.android.endgame.src.game.result.models.DefaultResponse;
import com.makeus.android.endgame.src.game.result.models.RecordResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.makeus.android.endgame.src.ApplicationClass.DATE_FORMAT;
import static com.makeus.android.endgame.src.ApplicationClass.getRetrofit;

public class ResultService {
    private final ResultActivityView mResultActivityView;
    private Context mContext;

    public ResultService(final ResultActivityView resultActivityView, Context context) {
        this.mResultActivityView = resultActivityView;
        this.mContext = context;
    }
    //결과 화면 File 로 만들어서 return
    public File ScreenShot(FrameLayout view){
        view.setDrawingCacheEnabled(true);  //화면에 뿌릴때 캐시를 사용하게 한다
//        view.buildDrawingCac/he(); //drawingCache에 저장

        Bitmap screenBitmap = view.getDrawingCache();   //캐시를 비트맵으로 변환
        Date date = new Date();

        String filename = "endgame"+DATE_FORMAT.format(date);
        File file = new File(Environment.getExternalStorageDirectory()+"/Endgame", filename);
        FileOutputStream os = null;
        try{
            os = new FileOutputStream(file);
            screenBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os); //save file
            os.close();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
//        view.destroyDrawingCache();

        view.setDrawingCacheEnabled(false);
        return file;
    }
    //kakao server에 이미지 업로드
    protected void uploadImageKakaoLink(FrameLayout view, final int gameIdx, final String choice, final int ratio) {
        File imageFile = ScreenShot(view);
        KakaoLinkService.getInstance().uploadImage((Context) mResultActivityView,
                false, imageFile, new ResponseCallback<ImageUploadResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e(errorResult.toString());
                mResultActivityView.validateFailureKakakolink(mContext.getString(R.string.kakao_share_fail));
            }

            @Override
            public void onSuccess(ImageUploadResponse result) {
                shareKakaoLink(result.getOriginal().getUrl(), gameIdx, choice, ratio); //이미지 업로드 성공 시 공유하기 호출
            }
        });
    }

    protected void shareKakaoLink(String url, int gameIdx, String choice, int ratio){
        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("논란종결",
                        url,//image url
                        LinkObject.newBuilder()
                                .setMobileWebUrl("https://developers.kakao.com").build())
                        .setDescrption("내 유형은 "+choice+"ㅎㅎ. 너도 해봐!!!")
                        .build())
                .addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()
                        .setWebUrl("https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com")
                        .setAndroidExecutionParams("gameIdx="+gameIdx+"&choice="+choice+"&ratio="+ratio)
                        .build()))
                .build();
        KakaoLinkService.getInstance().sendDefault((Context) mResultActivityView, params, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                mResultActivityView.validateFailureKakakolink(mContext.getString(R.string.kakao_share_fail));
            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
                // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
                mResultActivityView.validateSuccessKakakolink("✓");
            }
        });
    }

    public void saveImage(FrameLayout view){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/EndGame";
        final FrameLayout capture = view;//캡쳐할영역(프레임레이아웃)
        //처음 생성한 path 경로가 존재하는지 확인 후 존재하지 않을 시 경로를 생성
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
//            printToast("폴더가 생성되었습니다.");
        }
        Date date = new Date();
        SimpleDateFormat day = new SimpleDateFormat("yyyyMMddHHmmss");
        capture.buildDrawingCache(); //drawingCache에 저장
        Bitmap captureView = capture.getDrawingCache(); //비트맵 형식으로 저장
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(path+"/EndGame"+day.format(date)+".jpeg");
            captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos); //save file
            mResultActivityView.validateSuccessSaveImage("결과가 갤러리에 저장되었습니다.", path, day, date);
//            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path + "/EndGame" + day.format(date) + ".JPEG")));
            fos.flush();
            fos.close();
            capture.destroyDrawingCache();
        } catch (IOException e) {
            e.printStackTrace();
            mResultActivityView.validateFailureSaveImage("저장 실패ㅠㅠ. [설정] > [권한] 에서 외부 접근 권한을 허용해주세요.");
        }
    }
}
