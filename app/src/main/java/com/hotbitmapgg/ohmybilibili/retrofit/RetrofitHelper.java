package com.hotbitmapgg.ohmybilibili.retrofit;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.hotbitmapgg.ohmybilibili.OhMyBiliBiliApp;
import com.hotbitmapgg.ohmybilibili.retrofit.api.BangumiIndexService;
import com.hotbitmapgg.ohmybilibili.retrofit.api.BangumiRecommendService;
import com.hotbitmapgg.ohmybilibili.retrofit.api.LiveService;
import com.hotbitmapgg.ohmybilibili.retrofit.api.RecommendedService;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitHelper
{


    private static OkHttpClient mOkHttpClient;

    private static final String API_BASE_URL = "http://bilibili-service.daoapp.io/";

    private static final String MAIN_BASE_URL = "http://www.bilibili.com/";

    private static final String APP_BASE_URL = "http://app.bilibili.com/";

    static
    {
        initOkHttpClient();
    }

    /**
     * 获取哔哩哔哩直播Api
     *
     * @return
     */

    public static LiveService getBiliBiliLiveApi()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        LiveService liveService = retrofit.create(LiveService.class);

        return liveService;
    }


    /**
     * 获取番剧索引Api
     *
     * @return
     */

    public static BangumiIndexService getBangumiIndexApi()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MAIN_BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        BangumiIndexService bangumiIndexService = retrofit.create(BangumiIndexService.class);

        return bangumiIndexService;
    }

    /**
     * 获取主页推荐Api
     *
     * @return
     */

    public static RecommendedService getHomeRecommendedApi()
    {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APP_BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        RecommendedService recommendedService = retrofit.create(RecommendedService.class);

        return recommendedService;
    }

    /**
     * 获取首页番剧推荐列表
     *
     * @return
     */

    public static BangumiRecommendService getBnagumiRecommendApi()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BangumiRecommendService bangumiRecommendService = retrofit.create(BangumiRecommendService.class);

        return bangumiRecommendService;
    }


    /**
     * 初始化OKHttpClient
     * 设置缓存
     * 设置超时时间
     * 设置打印日志
     */
    private static void initOkHttpClient()
    {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null)
        {
            synchronized (RetrofitHelper.class)
            {
                if (mOkHttpClient == null)
                {
                    //设置Http缓存
                    Cache cache = new Cache(new File(OhMyBiliBiliApp.getInstance()
                            .getCacheDir(), "HttpCache"), 1024 * 1024 * 100);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(new StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }
}
