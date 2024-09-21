# <팀명> 강물을거꾸로거슬러오르는연어들 - <서비스명> 푸나바다
## 서비스 요약
푸나바다는 대구의 개인 자영업자, 대형 마트, 그리고 개인 영농업체 등의 판매자가 버려지는 식품을 소비자에게 정가보다 저렴한 가격으로 판매함으로써 음식물 쓰레기를 줄이고 환경을 보호하는 데 기여하는 플랫폼 서비스입니다.


## 주제 구분
-	S타입 대구 시내의 환경 문제를 해결하고 지속가능한 발전을 지원하는 서비스 

## 팀원 소개
팀명 
- 푸나바다

팀원 소개

팀장: 박규리 (학번: , 학과: 심화컴퓨터학부)

팀원: 이민혁 (학번: , 학과: 수학과)

팀원: 이효은 (학번: , 학과: 심화컴퓨터학부)

팀원: 박지현 (학번: 2021115412, 학과 : 심화컴퓨터학부)

## 시연 영상
(필수) Youtube 링크
(선택) Github Repository 페이지에서 바로 볼 수 있도록 넣어주셔도 좋습니다.

## 서비스 소개
### 서비스 개요
서비스에 대한 개요/요약을 개략적으로 작성

푸나바다는 대형 마트, 개인 자영업자, 영농업자 등과 협력하여 상품성이 떨어지거나 판매가 어려운 제품, 그리고 버려지는 재료들을 정가보다 저렴하게 판매하고자 하는 판매자와 이를 필요로 하는 소비자를 연결해주는 플랫폼입니다.
 판매자는 재고 관리를 효율적으로 하고 손실을 최소화할 수 있습니다. 또한, 소비자는 생활비를 절감하고, 이를 통해 많은 사람들이 저렴한 가격으로 필요한 식품을 구매할 수 있는 기회를 가질 수 있습니다.
 이를 통해 푸나바다는 지역 판매자와 소비자 협력을 통해 음식물 쓰레기를 최소화하여 환경을 보호하는 지속 가능한 발전을 이루고자 합니다.


### 타서비스와의 차별점
푸나바다는 대구 지역에 특화된 서비스로, 대규모 플랫폼들과는 달리 대구에 집중하여 판매자와 소비자 간의 긴밀한 관계를 형성하는 것을 목표로 합니다. 이를 통해 대구의 특성과 수요에 맞춘 맞춤형 서비스를 제공하여 음식물을 줄여 지역 단위로 환경 발전에 기여하는 것이 목표입니다. 



### 구현 내용 및 결과물
서비스의 실제 구현 내용과 결과물을 기재한다.

푸나바다는 판매자와 구매자의 역할에 따라 UI/UX가 다르며, 이용할 수 있는 서비스도 다릅니다. 
설명의 편의를 위해 아래와 같이 나눠서 설명하겠습니다.

판매자 : 상품 판매자
구매자 : 상품 구매자
사용자 : 웹 서비스 사용자 

로그인 및 회원 가입

1.소셜 로그인 후 회원 가입
 - 소셜 로그인(카카오, 구글) 후 자체 회원가입을 진행합니다.

게시글 

1.게시글 작성
 - 판매자는 판매하고 싶은 상품을 게시글로 작성할 수 있습니다.

2.메인 화면에서 게시글 리스트 조회 
 - 사용자는 현재 판매중인 게시글 정보 및 리스트를 볼 수 있습니다.
 - 구매자는 본인이 찜(즐겨찾기)한 목록은 게시글에 하트가 표시돼 직관적으로 확인할 수 있습니다.

3.게시글 상세 조회
 - 사용자는 게시글 클릭 시 판매 상세 정보 및 판매자(업체 정보) 정보를 확인할 수 있습니다.

4.1:1 채팅 
 - 구매자는 게시글을 통해 판매자에게 1:1 채팅을 할 수 있습니다.
 - 판매자는 1:1 채팅을 요청할 수 없습니다.

5.찜하기 
 - 구매자는 게시글 찜하기를 통해 추후 찜 목록을 확인할 수 있습니다.


지도

1.사용자 위치 기반 게시글 목록 조회 
 - 사용자의 위치를 기반으로 주변 게시글을 지도에 마커로 표시하여 쉽게 확인할 수 있습니다.

2.지도 내 존재하는 게시글 목록 조회
 - 사용자는 지도를 이동하여 해당 지역에 있는 게시글 목록을 조회할 수 있습니다.

3.가까운 순 게시글 목록 조회 
 - 사용자는 버튼을 클릭하여 가까운 순으로 게시글을 목록을 확인할 수 있습니다.

마이 페이지

1.사용자 프로필 정보 조회
 - 사용자의 프로필 정보 조회가 가능합니다.

2.채팅방 조회 
 - 사용자는 현재 사용 중인 채팅방 목록을 조회할 수 있습니다.

3.판매 목록 조회
 - 판매자는 판매 목록을 조회할 수 있습니다.

4.찜 목록 조회
 - 구매자는 찜목록을 조회할 수 있습니다.



### 구현 방식

프론트엔드
언어: TypeScript
프레임워크: React

백엔드
언어: Java
프레임워크: Spring

배포 환경
플랫폼: Google Cloud Platform (GCP)


## 향후 개선 혹은 발전 방안
현재 개발된 서비스에서 향후 개선되거나 발전될 수 있는 부분에 대한 설명

서비스 개선 방향 
1.대구 지역 활성화 계획
 - 대구 내 다양한 지역 상점과 식당, 영농업자와의 협력을 강화하여, 지역 경제를 활성화하고 주민들이 쉽게 접근할 수 있는 플랫폼으로 발전시켜나갈 계획입니다. 이를 통해 지역 내 신선한 식품을 저렴하게 공급하고, 음식물 쓰레기 감소에 기여하는 생태계 조성을 목표로 합니다.

2.커뮤니티 기반의 기부 및 재활용 프로그램
 - 유통기한이 임박한 식품을 지역 사회에 기부하는 프로그램을 강화하여, 필요한 이웃에게 도움을 줄 수 있는 기회를 마련하겠습니다. 또한, 소비자들이 참여할 수 있는 재활용 프로그램을 통해 환경 보호의식을 고취시키고 지역사회의 참여를 유도할 것입니다.


서비스 고도화 계획

1.결제 기능 및 목록 조회 기능 구현 계획
 - 푸나바다 플랫폼을 통해 안전한 거래를 할 수 있도록 결제 기능을 제공할 계획입니다.
 - 사용자의 판매 목록 및 구매 기능 조회는 추후 구현 예정  

2.소비자 습관 데이터 수집 및 AI 분석을 위한 기능 구현 계획
 - 소비자 행동과 구매 패턴을 분석하기 위해 데이터 수집 시스템을 구축하고, 이를 기반으로 AI를 활용한 개인 맞춤형 추천 서비스를 개발할 예정입니다. 
 - 추후 AWS에서 제공하는 데이터 클라우드를 활용하여 데이터 수집 및 분석에 활용할 계획입니다.
 - Content-Based Recommender System 모델을 도입하여 사용자가 이전에 선호한 식품을 분석하여 맞춤형 추천 제품을 제공합니다.

내용 삭제 해야됨 
3.마이크로 서비스 아키텍처 구성
 - 서비스의 확장을 고려하여 마이크로 서비스 아키텍처를 도입하며, FastAPI를 활용하여 AI 서버를 구축할 예정입니다. 이로써 각 기능을 독립적으로 개발하고 배포할 수 있어 시스템의 유연성과 확장성을 높일 수 있습니다.

4.사용자 경험 향상을 위한 UI/UX 개선 계획
 - 추후 UI/UX 개선을 통해 사용자 친화적인 인터페이스와 직관적인 내비게이션을 통해 소비자가 쉽게 원하는 상품을 찾고 구매할 수 있도록 할 계획입니다.


