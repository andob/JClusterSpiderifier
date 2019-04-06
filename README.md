# JClusterSpiderifier

### Java cross-platform library-agnostic Map Cluster Spiderifier algorithm

<img src="https://github.com/andob/JClusterSpiderifier/raw/master/demo.gif" align="left" height="700" >

[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-JClusterSpederifier-green.svg?style=flat )]( https://android-arsenal.com/details/1/7219 )

This library provides a lightweight algorithm to expand a map marker cluster into a spiderweb-like structure, so that the user can interact with each individual marker.

### Import

Using gradle:

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
```
dependencies {
    implementation 'com.github.andob:JClusterSpiderifier:1.0.3'
}
```

Using maven:

```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```
<dependency>
    <groupId>com.github.andob</groupId>
    <artifactId>JClusterSpiderifier</artifactId>
    <version>1.0.2</version>
</dependency>
```

### Setup

### Android / GoogleMaps

[Google Maps guide](https://github.com/andob/JClusterSpiderifier/tree/master/sample_android_google_maps)

using [GoogleMaps Android utils / marker clustering module](https://github.com/googlemaps/android-maps-utils)

### Android / MapBox

[MapBox guide](https://github.com/andob/JClusterSpiderifier/tree/master/sample_android_mapbox)

using [Mapbox Android Plugin / deprecated marker clustering module](https://github.com/mapbox/mapbox-plugins-android/tree/ce6793ac7cf107b4ce5cd4740f5aef6aaf6b9a0b/plugin-cluster)

### Other library / platform

Take a look at Android / Google maps or MapBox guide. The library itself focuses only on the algorithm, does not interact with nor include any map library. Thus the setup is a little verbose.. But in this way you have unlimited freedom to your code, to use any map library (GMaps, MapBox, osmdroid etc) or any platform (Android, JavaFX etc).

### License

```java
Copyright 2018 Andrei Dobrescu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.`
```
