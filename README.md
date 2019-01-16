# tooc - for user
<img src="https://user-images.githubusercontent.com/33562226/51236950-c6b4f100-19b6-11e9-8628-758535bf1588.png" width="100" height="100">

관광객과 쇼핑객을 위한 짐 보관 배송 서비스 플랫폼

## 소개
Wherever you go, Be free.
* 나를 곤란하게 만드는 무겁고 귀찮은 짐들, 이제는 '인접한 상점'에서 바로 처리할 수 있어요
* 툭과 함께 어디에 있던 자유롭게 도시를 즐기세요!
* 나의 짐을 안전하게, 저렴한 가격으로 보관 할 수 있어요

더 이상 지하철물품보관함 찾으러 다닐 필요 없어요
* 인접한 tooc(툭) 제휴상점을 툭 맵에서 찾아, 가까운 곳에서 짐을 처리할 수 있어요
* 쇼핑백, 캐리어, 백팩 무엇이던 보관할 수 있어요

귀찮은 예약, 보관 및 픽업 과정도 이제는 작별
* 단 30초면, 인접한 상점에 보관 예약이 가능해요
* 경로안내를 통해 상점에 방문, 결제하고 짐을 건내주면 끝!
* 보관 및 픽업 과정은 QR 인식 한번이면 끝!

## Develop Environment
- Language - Kotlin, java
- Minimum SDK Version - 19
- Target SDK Version - 27

## Library
- 위치 정보

    implementation 'com.google.android.gms:play-services-location:15.0.1'
    implementation 'com.google.android.gms:play-services-maps:15.0.1'
    implementation 'com.android.support:recyclerview-v7:27.1.1'
    implementation 'com.github.bumptech.glide:glide:4.8.0'
    implementation 'de.hdodenhof:circleimageview:2.2.0'
    implementation 'com.airbnb.android:lottie:2.6.0-beta19'
    
- QR코드 라이브러리

    implementation 'com.journeyapps:zxing-android-embedded:3.6.0'
    
- Layout

    implementation 'com.android.support:recyclerview-v7:27.1.1'
    implementation "org.jetbrains.anko:anko:0.10.7"
    implementation 'me.relex:circleindicator:2.1.0'
    implementation 'com.airbnb.android:lottie:2.6.0-beta19'
- HTTP REST API
    implementation 'com.github.bumptech.glide:glide:4.8.0'
    implementation 'com.squareup.retrofit2:retrofit:2.4.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
