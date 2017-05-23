# react-native-android-media-loader

# Description
This component to get all image & video from android device, like CameraRoll component.

# Installation
This component does't have npm install, so you must install manually.
```
  npm install --save https://github.com/RZulfikri/react-native-android-media-loader.git
```
then user 
```
  react-native link react-native-android-media-loader
```

# Usage
```
  ...
  import MediaLoader from 'react-native-android-media-loader'
  ...
  
  export default class extend Components{
    ...
    // paramA(number) = number of media file. (if param = 0 it mean load all media)
    // paramB(number) = nextLoadId (it use for next Load) (if param = -1, it mean no start pos to load)
    componentWillMount() {
        MediaLoader.getMedia(paramA, paramB).then((result) => console.log(result))
    }
    ...
  }
```

# Return
this component will return media file in object like this :
```
  {
    load_status : { 
                  next_load : true  // (true) if it have next item, and (false) if no more item to load.
                  next : (number) // it use for next load, as param in paramB,
                }
    result : {[
              id : // media id
              path : // media path 
              title : // media title
              type : // media type (video/mp4, image/jpeg, image/png, etc)
            ]}
  }
```
