package com.hw.demoplayer.network;

import android.text.TextUtils;
import android.widget.Toast;
import com.hw.demoplayer.BaseApplication;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 说明：网络请求返回数据处理
 */
public abstract class DefaultObservers<T> implements Observer<T> {

    private Disposable disposable;
    /** 在系统筛选之后自己再处理*/
    public static int RESULT_NORMAL =  0;
    /** 完全自己处理  不需要进行筛选*/
    public static int RESULT_SELF =  1;


    /**
     * 请求成功
     * @param data 响应数据
     */
    public abstract void onResponse(T data);

    /***
     * 返回的正常数据
     * @param data
     */
    public void onNormalData(T data){};
    /**
     * 请求失败
     * @param code 错误码 0请求错误  <0 请求成功但是数据错误
     * @param msg 错误信息
     */
    public int onFailure(String code, String msg){
        return RESULT_NORMAL;
    }

    
    @Override
    public void onError(Throwable e) {
        //统一处理错误日志
        String errMsg = e.getMessage();
        if (errMsg != null) {
            onFailure(BaseResponse.RESPONSE_ERROR, errMsg);
        }
        if(!TextUtils.isEmpty(errMsg)){
            Toast.makeText(BaseApplication.getContext(),errMsg,Toast.LENGTH_SHORT).show();
        }
        disposable.dispose();//解除绑定
    }

    @Override
    public void onNext(T respond) {
        try {
            if(respond != null){
                onNormalData(respond);
                if(CodeConfig.CODE_SUCCESS.equals(((BaseResponse) respond).code) || "0000000".equals(((BaseResponse) respond).code)){
                    onResponse(respond);
                } else {
                    handleResult((BaseResponse) respond);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            onFailure(BaseResponse.RESPONSE_ERROR, e.getMessage());
        }
    }

    /**
     * 处理错误日志
     * @param baseResponse
     */
    public void handleResult(BaseResponse baseResponse){
        int result = onFailure(baseResponse.code,baseResponse.msg);
        if(result == RESULT_NORMAL){//系统进行一部分筛选
            if(!TextUtils.isEmpty(baseResponse.msg)){
                Toast.makeText(BaseApplication.getContext(), baseResponse.msg,Toast.LENGTH_SHORT).show();
            }
        } else if(result == RESULT_SELF){//自己处理错误日志
            
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        disposable = d;
    }

    @Override
    public void onComplete() {

    }
}
