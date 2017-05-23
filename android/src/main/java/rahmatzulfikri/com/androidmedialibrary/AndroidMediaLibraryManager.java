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
  private AndroidMediaLibrary amd;
  private final ReactApplicationContext reactContext;

  public AndroidMediaLibraryManager(ReactApplicationContext reactContext) {
    super(reactContext);
    amd = new AndroidMediaLibrary(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return REACT_PACKAGE;
  }

  @ReactMethod
  public void getMedia(int limit, int nextId, Promise promise){
      if(limit > 0 ){
        if(nextId > 0){
            amd.getMediaList(limit, nextId, promise);
        }else{
            amd.getMediaList(limit, promise);
        }
      }else{
          amd.getMediaList(promise);
      }
  }

}