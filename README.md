# RxJavaHttpConnect
分别集成了Rxjava okhttp retrofit FastJson Gson AvLoarding等框架
 
 
![](https://github.com/guodongxiaren/ImageCache/raw/master/Logo/foryou.gif)  
版本 [![](https://jitpack.io/v/asd392044178/RxJavaHttpConnect.svg)](https://jitpack.io/#asd392044178/RxJavaHttpConnect)
# 集成
```  
* Add it in your root build.gradle at the end of repositories:
 	 allprojects {
	 repositories {
		 ...
		 	maven { url 'https://jitpack.io' }
	 }
    }
 ```   
 
 
  
   ```  
    Step 2. Add the dependency
	  dependencies {
	          implementation 'com.github.asd392044178:RxJavaHttpConnect:1.0'
              } 
   ``` 

 
 
 
# 使用接入

  ``` 	 <1> Config.init("","",false) ``` 
 
 第三个参数true打印log false不打印 建议非测试的时候设置为false
  
 
 ```  
<2> RxRestClient.builder().url(url).params(params).build().post().subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new AbstractObserver<你的实体>(){
                   @Override
              public void onSuccess(你的实体) {
           
            }

                 @Override
                 public void onFailure(String message) {
    
                }
            });
                    }
                     );   
   ``` 
    #### parames 以键值对的方式传入
    WeakHashMap<String, Object> weakHashMap = new WeakHashMap<>();   
    
              
           
