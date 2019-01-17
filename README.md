# tooc - for user
<img src="https://user-images.githubusercontent.com/33562226/51236950-c6b4f100-19b6-11e9-8628-758535bf1588.png" width="150" height="150">

관광객과 쇼핑객을 위한 짐 보관 배송 서비스 플랫폼 

## 소개
#### Wherever you go, Be free.
* 나를 곤란하게 만드는 무겁고 귀찮은 짐들, 이제는 '인접한 상점'에서 바로 처리할 수 있어요
* 툭과 함께 어디에 있던 자유롭게 도시를 즐기세요!
* 나의 짐을 안전하게, 저렴한 가격으로 보관 할 수 있어요

#### 더 이상 지하철물품보관함 찾으러 다닐 필요 없어요
* 인접한 tooc(툭) 제휴상점을 툭 맵에서 찾아, 가까운 곳에서 짐을 처리할 수 있어요
* 쇼핑백, 캐리어, 백팩 무엇이던 보관할 수 있어요

#### 귀찮은 예약, 보관 및 픽업 과정도 이제는 작별
* 단 30초면, 인접한 상점에 보관 예약이 가능해요
* 경로안내를 통해 상점에 방문, 결제하고 짐을 건내주면 끝!
* 보관 및 픽업 과정은 QR 인식 한번이면 끝!

## Develop Environment
- Language - Kotlin, Java
- Minimum SDK Version - 19
- Target SDK Version - 27

## Library
#### 1. 위치 정보
- implementation 'com.google.android.gms:play-services-location:15.0.1'
- implementation 'com.google.android.gms:play-services-maps:15.0.1'

#### 2. 레이아웃
- implementation 'com.android.support:recyclerview-v7:27.1.1'
- implementation 'de.hdodenhof:circleimageview:2.2.0'
- implementation "org.jetbrains.anko:anko:0.10.7"
- implementation 'me.relex:circleindicator:2.1.0'

#### 3. 애니메이션
- implementation 'com.airbnb.android:lottie:2.6.0-beta19'
    
#### 4. QR 코드
- implementation 'com.journeyapps:zxing-android-embedded:3.6.0'

#### 5. HTTP REST API
- implementation 'com.squareup.retrofit2:retrofit:2.4.0'
- implementation 'com.squareup.retrofit2:converter-gson:2.4.0'

#### 6. Image Load
- implementation 'com.github.bumptech.glide:glide:4.8.0'

## 주요기능
#### 1. 메인 화면
- 사용자는 자신의 현 위치를 중심으로 Google Map에 찍혀 있는 Marker와 지역별 제휴 상가 리스트를 통해 원하는 지역의 제휴 상가를 쉽게 확인할 수 있습니다.
- 상가 선택 시, 해당 상가의 주소와 영업시간 등 상가 세부 정보는 물론, Google Map과 Kakao Map를 통한 길찾기 기능을 제공합니다.

#### 2. 예약 화면
- 사용자는 상가 휴무일과 영업시간에 따라 동적으로 변하는 Picker를 통해 자신이 원하는 시간에 예약을 설정할 수 있습니다.
- 일반짐 및 캐리어를 선택하게 되면 보관 시간에 따른 가격이 자동으로 산출됩니다.
- 예약 설정을 모두 마치게 되면, 예약 내용에 대한 동의를 함으로써 예약을 완료할 수 있습니다.

#### 3. 예약 현황 화면
- 서버에서 받은 예약 코드로 QR 코드를 생성하여 보관 절차를 간소화하였습니다.
- 예약 중, 결제 완료, 보관 중, 짐 수거로 상태를 나눠, 사용자가 현재 상태를 확인할 수 있습니다.
- 맡기는 시간 및 찾는 시간, 짐 개수, 가격 등 예약 내역을 확인할 수 있습니다.
- 비밀번호를 입력하여 예약을 취소할 수 있습니다.
- Google Map과 Kakao Map를 활용하여 사용자가 길찾기를 통해 쉽게 상가를 찾을 수 있습니다.

#### 4. Mypage 화면
- 내 짐 현황을 통해 현재 보관중인 짐의 개수와 예약 내역을 확인할 수 있습니다.
- 나의 리뷰를 통해 자신이 작성한 리뷰를 확인할 수 있고 수정과 삭제도 가능합니다.
- 즐겨찾기를 통해 사용자가 즐겨찾기에 추가한 상가들의 정보를 볼 수 있습니다.
- 프로필을 통해 사용자의 정보를 확인할 수 있습니다.
- 환경 설정을 통해 회사 정보, 사용법, FAQ, 이용약관 등을 확인할 수 있고, 문의사항을 작성할 수 있습니다.

## 통신 API 문서
http://52.78.222.197:8080/swagger-ui.html#/

https://github.com/team-travely/travely-server/wiki

-----------------------------

# tooc - for admin
<img src="https://user-images.githubusercontent.com/38368820/51321967-83867b00-1aa7-11e9-97cb-f9bf9b5c816f.png" width="150" height="150">
