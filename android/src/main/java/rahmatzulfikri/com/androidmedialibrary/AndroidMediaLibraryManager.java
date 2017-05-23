package rahmatzulfikri.com.androidmedialibrary;

import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

public class AndroidMediaLibraryManager extends ReactContextBaseJavaModule {
  static final String REACT_PACKAGE = "RNMediaLoader";

  private final ReactApplicationContext reactContext;

  public AndroidMediaLibraryManager(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return REACT_PACKAGE;
  }

  @ReactMethod
  public void getMediaList(int limit, int nextId, Promise promise){
      if(limit > 0 ){
        if(nextId > 0){
            MediaLoader.getMediaList(limit, nextId, promise);
        }else{
            MediaLoader.getMediaList(limit, promise);
        }
      }else{
          MediaLoader.getMediaList(promise);
      }
  }

  @ReactMethod
  public void getPreviewImages(String path, Promise promise) {
    Log.d(REACT_PACKAGE, "getPreviewImages: " + path);
    Trimmer.getPreviewImages(path, promise, reactContext);
  }

}