# httpcore
This is a network request library based on okhttp（基于OkHttp的网络请求封装库）

#### Usage:

Step1: 添加依赖

    implementation 'com.bodhi.httpcore:httpcore:1.0.3'
    
Step2: 初始化网络库

     HttpCore.getInstance().init(context,sslParams,isDebug);
     
Step3: 开始使用
     
    HttpCore.getInstance().simpleGet(url, header,urlParamMap, new HttpRequestListener<String>() {
        @Override
        public void onResult(String s) {
            Log.e("aaa", "onResult   s:" + s);
        }

        @Override
        public void onError(int errorCode, String errorMsg) {
            Log.e("aaa", "onError   errorCode:" + errorCode+"   errorMsg:"+errorMsg);

        }
    });
    
    HttpCore.getInstance().simplePost(url, header,urlParamMap,requestBody, new HttpRequestListener<String>() {
        @Override
        public void onResult(String s) {
            Log.e("aaa", "onResult   s:" + s);
        }

        @Override
        public void onError(int errorCode, String errorMsg) {
            Log.e("aaa", "onError   errorCode:" + errorCode+"   errorMsg:"+errorMsg);

        }
    });
    
    HttpCore.getInstance().anyGet(url,header, urlParamMap, Class<T>.class, new HttpRequestListener<T>() {
        @Override
        public void onResult(Class<T> resultClz) {
            Log.e("aaa","onResult:"+resultClz);
        }

        @Override
        public void onError(int errorCode, String errorMsg) {
            Log.e("aaa","errorCode:"+errorCode+"  errorMsg:"+errorMsg);
        }
    });
    
    HttpCore.getInstance().anyPost(url,header, urlParamMap,requestBody, Class<T>.class, new HttpRequestListener<T>() {
        @Override
        public void onResult(Class<T> resultClz) {
            Log.e("aaa","onResult:"+resultClz);
        }

        @Override
        public void onError(int errorCode, String errorMsg) {
            Log.e("aaa","errorCode:"+errorCode+"  errorMsg:"+errorMsg);
        }
    });
    
     HttpCore.getInstance().bitmapGet(url,header, urlParamMap, new HttpRequestListener<T>() {
        @Override
        public void onResult(Bitmap bitmap) {
            Log.e("aaa","onResult:"+bitmap);
        }

        @Override
        public void onError(int errorCode, String errorMsg) {
            Log.e("aaa","errorCode:"+errorCode+"  errorMsg:"+errorMsg);
        }
    });
    
     HttpCore.getInstance().upLoadFile(url,header, urlParamMap,imagePath,mediaType, new HttpRequestListener<T>() {
        @Override
        public void onResult(String s) {
            Log.e("upload","---onResult s:"+s);
        }

        @Override
        public void onError(int errorCode, String errorMsg) {
            Log.e("aaa","errorCode:"+errorCode+"  errorMsg:"+errorMsg);
        }
    });
  
  
    
