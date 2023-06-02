package com.hw.demoplayer.network;

import com.hw.demoplayer.network.interceptor.HeadInterceptor;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.kotlin.RxlifecycleKt;
import com.hw.demoplayer.network.converter.GsonConverterFactory;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * des:封装retrofit工具类
 */
public class RetrofitUtil {

    static final int DEFAULT_TIME_OUT = 10; //默认超时

    private static String mBaseUrl;

    static volatile Retrofit instance;

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(new HeadInterceptor())
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            .build();

    public static <T> T createService(Class<T> cls) {
        return defaultInstance().create(cls);
    }

    public static <T> T createService(String baseUrl, Class<T> cls) {
        return createInstance(baseUrl).create(cls);
    }

    public static Retrofit createInstance(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static Retrofit defaultInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (RetrofitUtil.class) {
            if (instance == null) {
                instance = createInstance(mBaseUrl);
            }
            return instance;
        }
    }

    public static void init(String url) {
        mBaseUrl = url;
    }

    public static <T extends BaseResponse> Observable<T> hull(Observable<T> observable) {
        return hull(observable, true);
    }

    public static <T extends BaseResponse> Observable<T> hull(Observable<T> observable, boolean switchThread) {
        return hull(observable, null, null, switchThread);
    }

    public static <T extends BaseResponse> Observable<T> hull(Observable<T> observable, LifecycleProvider activity) {
        return hull(observable, activity, ActivityEvent.DESTROY, true);
    }

    public static <T extends BaseResponse> Observable<T> hull(Observable<T> observable, LifecycleProvider activity, boolean switchThread) {
        return hull(observable, activity, ActivityEvent.DESTROY, switchThread);
    }

    public static <T extends BaseResponse> Observable<T> hull(Observable<T> observable, LifecycleProvider activity, ActivityEvent event) {
        return hull(observable, activity, event, true);
    }


    public static <T extends BaseResponse> Observable<T> hull(Observable<T> observable, LifecycleProvider activity, ActivityEvent event, boolean switchThread) {
        if (activity != null && event != null) {
            observable = RxlifecycleKt.bindUntilEvent(observable, activity, event);
        }

        Observable<T> nake = observable.map(new Function<T, T>() {
            @Override
            public T apply(@NonNull T respond) throws Exception {
//                if (respond.isSuccess()) {
                return respond;
//                } else {//可以统一错误处理
//                    throw new RuntimeException(BaseResponse.RESPONSE_ERROR + "|" + respond.header.info);
//                }
            }
        });

        if (switchThread) {
            return nake.compose(RetrofitUtil.<T>threadSwitcher());
        }
        return nake;
    }

    public static <T> ObservableTransformer<T, T> threadSwitcher() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}