# 모의 면접 - 1. UI

## 기본 질문

- Context는 무엇인가요?
    - ApplicationContext와는 어떤 차이가 있나요?

## Activity

- Activity의 역할을 Activity lifecycle과 함께 설명해주세요.
- 각 lifecycle callback에 적합한 처리가 무엇인지 예시를 들어 설명해주세요.
- Activity의 UI 상태를 유지 및 복원하기 위한 방법에는 어떤 것들이 있나요?
- backstack이 무엇인지 설명해주세요.

## Fragment

- Fragment의 역할을 Fragment lifecycle과 함께 설명해주세요.
    - 각 lifecycle callback에 적합한 처리가 무엇인지 예시를 들어 설명해주세요.
- Activity와 Fragment의 차이점을 설명해주세요.
    - 화면을 구성할 때 Activity와 Fragment를 선택하는 기준을 함께 설명해주세요.

## Layout

- View, ViewGroup은 무엇인가요?
- View, ViewGroup의 차이점을 설명해주세요.
- ConstraintLayout의 특징을 설명해주세요.
- Layout 구현시 성능을 위해 고려해야하는 부분을 설명해주세요.

---

## 기본 질문

- 🔎 Context는 무엇인가요?
    - 답) Android 시스템에 의해 제공되어 구현된 추상 클래스
        - Context의 역할
            - 애플리케이션의 현재 상태를 나타냄
            - Activity와 Application의 정보를 얻기 위해 사용
            - 안드로이드 시스템 서비스에서 제공하는 API (리소스, 데이터베이스, shared preferences 등)에 접근하기 위해 사용
                - 예시) getResource()
            - Activity 실행, Broad Casting, Intent 수신과 같은 Application level에서의 작업을 허용
    - 🔎 ApplicationContext와는 어떤 차이가 있나요?
        - 답) Context 속에 Application Context가 존재한다. Context가 부모클래스 Application Context가 손자클래스였음

### Application Context

- **Application Lifecycle에 종속**되어 있기 때문에 현재 Context가 종료되고 나서도 Context가 필요한 작업 & Activity 범위를 벗어난 곳에 Context가 필요한 작업에 적합
- activity에서 applicaitonContext property를 통해 얻을 수 있는 싱글톤 인스턴스
- 예시
    - Application에서 싱글톤 객체를 생성하였는데 이 객체가 Context를 필요로 한다면, Application Context를 사용하면 됨
    - 만약, Activity Context를 넘겨주게 되면 메모리 누수 발생
        - Activity Context를 넘겨줌면 Activity에 대한 참조를 메모리에 남겨두며 GC 되지 않기 때문
- Application Context가 사용되는 경우
    - ActivityViewModel 클래스를 상속받는 ViewModel 안에서 Context를 사용해야하는 경우
        - ViewModel의 생명주기가 Activity보다 길기 때문에 Application Context 사용
    - Acitivity 안에서 라이브러리를 초기화해야하는 경우
        - Application Context를 전달하여 사용

### Activity Context

- 특정 Activity의 Lifecycle에 종속되어 있으며 Activity 내에서만 사용 가능
- Activity가 destroy 될 때, 같이 destroy되는 context
- Dialog와 같은 GUI 작업 시, Activity Context 사용

<img width="596" alt="스크린샷 2022-09-26 오후 6 38 20" src="[https://user-images.githubusercontent.com/31344894/192295326-ff4c8102-41f6-4692-a0a2-304468a30a5a.png](https://user-images.githubusercontent.com/31344894/192295326-ff4c8102-41f6-4692-a0a2-304468a30a5a.png)">

### 상황 별 Context 선택

- Q. MyApplication 클래스와 MyDB 싱글톤 객체가 있을 때, MyDB가 Context를 필요로 하는 경우, 어떤 Context를 넘겨줘야할까?
- A. **Application Context**
    - 만약 Activity Context를 전달했다면, Activity가 사용되지 않을 때도 MyDB가 해당 Activity를 참조하기에 메모리 누수 발생
- Q. Toast, Dialog 등의 UI 동작에 있어 Context가 필요하다면, 어떤 Context를 넘겨줘야할까?
- A. Activity Context
    - 해당 UI 컴포넌트들은 Activity의 Lifecycle에 종속되는 것들이기 때문
- Application Context는 Activity Context가 지원하는 모든 것을 지원하지 않음. (만능 X) 따라서 GUI(View Component 등) 관련 동작들에 있어 Application Context는 오류를 발생할 확률이 높음
    - Application Context를 활용하여 AlertDialog를 show()하게 되면 아래 에러 발생
        
        <aside>
        ⛔ java.lang.IllegalStateException: You need to use a Theme.AppCompat theme (or descendant) with this activity
        
        </aside>
        

## Activity

![https://user-images.githubusercontent.com/31344894/192295590-d9a3e193-ec99-4c15-9c15-3aa7b12ce79d.png](https://user-images.githubusercontent.com/31344894/192295590-d9a3e193-ec99-4c15-9c15-3aa7b12ce79d.png)

- 🔎 Activity의 역할을 Activity Lifecycle과 함께 설명해주세요.
- 🔎 각 Lifecycle callback에 적합한 처리가 무엇인지 예시를 들어 설명해주세요.
    - onCreate() → View 만들기, 데이터 바인딩
    - onStart() → 화면에 진입할 때마다 실행되어야 하는 작업
    - onResume() → foreground에서 사용자에게 보이는 동안 실행되어야하는 모든 기능
    - onPause() → 시스템 리소스, 센서 혹은 베터리 수명에 영향을 주는 불필요한 리소스 등을 해제
    - onStop() → 현재 화면을 구성하는 데이터를 저장하거나 네트워크 호출 & DB transaction과 같은 시간 소요가 있는 작업을 실행. 부하가 큰 종료 작업
    - onDestroy() → Activity에서 더 이상 사용하지 않는 리소스 해제
- 🔎 Activity의 UI 상태를 복원하기 위한 방법에는 어떤 것들이 있나요?
    - onSaveInstanceState()
        - savedInstanceState에 저장되는 데이터(bundle)는 단순하고 가벼워야함
            - 이유: 저장된 Instance 상태 Bundle은 구성 변경 및 프로세스 중단에도 유지되지만, onSavedInstaceState()가 데이터를 디스크에 직렬화하기 때문에 저장 용량 및 속도에 의해 제한됨.
                - 직렬화 프로세스는 구성 변경 시, main thread에서 발생하기 때문에 직렬화가 장기적으로 실행되면 프레임 하락 및 시각적인 끊김 현상 발생
        - 사용 방법:
            - 구성 변경 혹은 메모리 부족에 의한 프로세스에 의해 시스템이 Acitivity를 소멸시켜 Activity가 중지되기 시작하면 시스템이 onSaveInstanceState()를 호출하여 activity가 인스턴스 상태 Bundle에 상태 정보를 저장할 수 있도록 함.
            - 이후 onCreate()의 매개변수 savedInstanceState에서 복원 혹은 onRestoreInstanceState()에서 복원
            
            ```
            @Override
              protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
            
                textView = (TextView) findViewById(R.id.text_view);
            
                if (savedInstanceState != null) {
                  String data = savedInstanceState.getString(KEY_DATA);
                  textView.setText(data);
                }
              }
            
              @Override
              protected void onSaveInstanceState(Bundle outState) {
                super.onSaveInstanceState(outState);
            
                String data = textView.getText().toString();
                outState.putStringArrayList(KEY_DATA, data);
              }
            
            ```
            
    - ViewModel
        - ViewModel은 Activity의 생명주기와 관계없으므로 ViewModel에 저장된 데이터는 Activity가 onRestart될 때, Activity의 상태와 관계없이 데이터 유지 가능
        - **메모리에 데이터를 보관**하므로 네트워크 혹은 디스크에서 데이터를 가져오는 것보다 **검색 비용이 낮음**
        - savedInstanceState와 달리 ViewModel은 시스템에서 시작된 프로세스 중단 중에 폐기됨. 따라서 onSaveInstanceState()와 결합하여 ViewModel 객체를 사용해야하며 시스템이 중단된 이후 ViewModel이 데이터를 다시 로드할 수 있도록 savedInstanceState에 식별자 보관해야함
- 🔎 backstack이 무엇인지 설명해주세요
    - 화면이 전환될 때마다, 이전 Activity를 저장하는 stack
    - back 버튼이 눌리면 backstack에 저장된 activity를 하나씩 꺼내서 이전 화면으로 전환해준다.

### Activity

- 안드로이드 4대 컴포넌트 중 하나
    - Component: 시스템에 의해 생명주기가 관리되며, 앱에서 독립적으로 실행될 수 있는 구성 단위. 컴포넌트가 조합되어 앱이 만들어짐
- 사용자와 상호작용하기 위한 진입점이며 화면에 표시되는 UI 구성을 위해 가장 기본이 되는 요소
- 시스템과 앱의 상호작용을 도움
    - 화면에 표시된 것을 추적하여 Activity를 호스팅하는 프로세스를 시스템에서 유지하게 함
    - 이전에 사용한 프로세스에 중단된 Activity를 기억하고, 해당 프로세스를 유지하는데 더 높은 우선순위를 부여
    - 앱이 프로세스를 종료하도록 도와서 이전 상태가 복원되는 동시에 사용자가 Activity로 돌아갈 수 있게 함
- LifeCycle
    - Activity는 상태 변화를 관리할 수 있는 lifecycle이라는 콜백을 가짐
    - onStop() VS onPause()
        - onStop: Activity가 이벤트로 인해 새로운 화면이 나타나 전체 화면을 가리게 되면 onStop 호출됨
            - Activity에서 이제 사용되지 않을 리소스 해제
            - 현재 상태 정보를 저장하기 위한 DB 작업
            - onStop 시점에서 Activity 객체는 아직 메모리 안에 머무르게 되기에, 모든 상태나 멤버 정보는 계속 관리하고 있음. 따라서 Activity가 재개되면 해당 정보를 다시 호출할 수 있음. (재개 시, onCreate에서 생성한 구성요소는 다시 초기화할 필요없고, layout에 있는 View 객체도 현재 상태를 기록하기에 저장 및 복원할 필요 없음)
        - onPause: foreground에서 background로 바뀌는 시점으로 화면 일부가 가려진 상태일 때 onPause 콜백 호출됨
            - 이벤트가 발생하여 새로운 Activity가 foreground로 나오기 전까지는 onPause 상태에 있다가 foreground로 나오는 순간 모든 화면이 가려지면 onStop 콜백 호출
                - Activity A → Activity B
                    1. Activity A는 onPause() 실행 (Activity A가 background로 이동)
                    2. Activity B는 onCreate, onStart, onResume를 실행하여 foreground 상태가 됨
                    3. Activity B가 onResume까지 호출된 이후에는 foreground 상태가 되고 전체 화면을 가득 채우기 때문에 Activity A는 onStop 호출
            - Dialog Activity나 투명 Activity가 위에 뜰 경우에도 onPause 상태에 머물게 됨
            - onPause는 아주 잠깐 실행되므로 현재 화면을 구성하는 데이터를 저장하거나 네트워크 호출, DB transaction과 같은 시간 소요가 있는 작업 실행하면 안됨 (대신 onStop에서 하기)
    - 헷갈릴 만한 상황에서의 생명주기 호출 순서
        - 시작할 때 : onCreate → onStart → onResume
        - 화면 회전할 때 : onPause → onStop → onDestory → onCreate → onStart → onResume
        - 홈 버튼 클릭 시 : onPause → onStop
        - 홈 이동 후 다시 돌아올 때 : onRestart → onStart → onResume
        - 백 버튼 클릭하여 액티비티 종료 시 : onPause → onStop → onDestory

### BackStack

- Background와 Foreground 상태
    - 앱 실행 중, 사용자가 종료 버튼을 누른 경우,
        - Android 11 이하 → 앱 종료
        - Android 12 이하 → 시스템이 Activity와 task를 종료하지 않고 background로 보냄. (좀 더 빠르게 앱 켤 수 있음)
- 여러 인스턴스 활동 관리
    
    ![https://user-images.githubusercontent.com/31344894/192295692-b8e3f0d6-86b1-43eb-9414-744c1c2f262a.png](https://user-images.githubusercontent.com/31344894/192295692-b8e3f0d6-86b1-43eb-9414-744c1c2f262a.png)
    
    - 여러 Activity가 인스턴스로 생성되면 스택에 쌓이고 중복된 인스턴스를 막기 위해 backstack 관리가 필요함
        - Intent flag로 관리 가능
- 작업 관리
    - Manifest 파일의 Activity 태그 속 launchMode attribute를 부여함으로써 backstack 관리 가능

## Fragment

![https://user-images.githubusercontent.com/31344894/192296596-6e9f4e40-235f-4aec-9193-69c498e07369.png](https://user-images.githubusercontent.com/31344894/192296596-6e9f4e40-235f-4aec-9193-69c498e07369.png)

- 🔎 Fragment의 역할을 Fragment Lifecycle과 함께 설명해주세요
    - 앱 UI의 재사용 가능한 부분으로 자체 layout을 정의 및 관리하고 자체 수명 주기를 보유
    - **독립적으로 존재할 수 없고 Activity나 다른 Fragment에서 호스팅되어야 함**
    - 단일 화면이나 화면 일부의 UI를 정의하고 관리하는데 적합
- 🔎 각 Lifecycle Callback에 적합한 처리가 무엇인지 예시를 들어 설명해주세요
    - onCreate() → Fragment View 생성 전으로 View와 관련된 작업 수행 금지. Fragment를 생성하면서 넘겨준 값이 있을 경우, onCreate에서 변수 할당
    - onCreateView() → layout inflate 수행
    - onViewCreated() → View의 초기값 설정, LiveData Observing, RecyclerView 또는 ViewPager에 사용될 Adapter 세팅
    - onViewStateRestored() → 각 뷰의 상태 값 체크 가능
    - onStart() → child FragmentManager를 통해 fragment transaction 수행 가능
        - FragmentManager: 앱의 fragment를 더하고 삭제하고 교체하고 back stack에 더하는 활동 (= fragment transaction) 등을 책임지는 class (현재는 jetpack navigation library 사용 권장)
    - onResume() → 입력, 포커스 설정 등 사용자와 상호작용 가능
    - onPause() → 일시 중지되었으나 Fragment가 Visible일 때 필요한 처리
    - onStop() → API 28버전 이후로 onSaveInstanceState() 함수와 onStop() 함수 호출 순서가 달라짐에 따라, FragmentTransaction을 안전히 수행할 수 있는 마지막 지점이 됨
    - onSaveInstanceState() → Fragment에 현재 동적 상태를 저장하도록 요청 가능
    - onDestroyView() → binding 해제. GC에 의해 수거될 수 있도록 Fragment View에 대한 참조 제거
    - onDestroy() → Activity에서 해당 Fragment가 해제됨을 알 수 있음
- 🔎 Activity와 Fragment의 차이점을 설명해주세요
    - Activity는 독립적으로 존재할 수 있는 것에 반해, Fragment는 Activity나 다른 Fragment에 의해 호스팅되어야 존재할 수 있음
    - Activity는 사용자의 상호작용을 위한 진입점 역할이자 동시에 하나의 UI 화면을 그리는 Container 역할이며, 그 안에서 다양한 UI를 모듈화하고 화면 구성을 더욱 쉽게 해주는 것이 Fragment
- 🔎 화면을 구성할 때 Activity와 Fragment를 선택하는 기준을 함께 설명해주세요.
    - Activity는 전체 화면을 차지하지만, Fragment는 전체 화면이 아니여도 되며 디자인에 많은 유연성을 가짐
    - 한 번 작성된 Fragment는 여러 Activity에서 재사용이 가능함

### Fragment

- onDestroyView() VS onDestroy()
- onDestroyView()가 호출되면 fragment 객체 자체는 사라지지 않고 메모리에 남아있음
- onDestroy()가 호출되면 fragment 객체 파괴됨
- 만약 fragment를 계속 바꿔가며 사용할 경우, 메모리 누수에 유의해야함.
    - 1번 fragment를 실행하면 onAttach() → onResume()까지 호출됨
    - 2번 fragment를 실행하면 1번 fragment는 onDestroyView()가 호출됨. (1번 fragment의 객체는 제거되지 않았고 view만 파괴)
    - 만약 현재 2번 fragment에서 사용자와 상호작용 중인 경우, 1번 객체는 여전히 살아있음
    - 1번 fragment에서 LiveData를 구독하고 있다고 한다면 fragment의 lifecycle 인지를 위해 lifecycleOwner에 this(fragment 객체)를 바인딩하면 1번 fragment의 LiveData는 사용자가 2번, 3번 fragment에 머무르고 있을 때도 지워지지 않고 존재하고 있게 됨 (메모리 누수)
    - LifeData는 lifecycleOwner가 destroyed되어야 사라짐
    - 따라서 **Fragment에서 메모리 누수를 막기 위해 lifecycleOwner로 viewLifecycleOwner를 고려하자**

## Layout

- 🔎 View, ViewGroup은 무엇인가요?
    - View
        - Activity에서 화면을 구성하는 최소 구성단위(컴포넌트)로 화면에 보이는 위젯
    - ViewGroup
        - n 개의 View를 담을 수 있는 Container로 ViewGroup 또한 View를 상속받아 만든 클래스
        - = layout
    - 예시) LinearLayout(ViewGroup) 하위에 TextView(View) 등이 들어감
- 🔎 View, ViewGroup의 차이점 설명해주세요
    - ViewGroup은 View의 컨테이너 역할
    - ViewGroup은 View의 배치 방향을 정의함
    - ViewGroup은 View의 컨테이너라 invisible
- 🔎 ConstraintLayout의 특징을 설명해주세요
    - layout 구성 시, 뷰 위젯의 위치와 크기를 유연하게 조절할 수 있도록 만들어주는 ViewGroup
    - Linear Layout을 써야만 했던 View 비율 조절을 간단히 가능 (depth가 깊어지는 것 방지)
    - View 계층 간단히 할 수 있어 유지 보수와 성능이 좋음
    - 형제 View들과 관계를 정의해서 layout을 구성한다는 점이 Relative Layout과 비슷하지만 보다 유연하고 다양한 기능 제공함
- 🔎 Layout 구현 시 성능을 위해 고려해야하는 부분을 설명해주세요
    - Depth가 깊어지지 않도록 구현
    - 반복되는 layout 구성이 있을 경우 재사용할 수 있는 layout 구성을 만들고 <include>를 통해 UI layout에 포함하기
    - 요청이 있을 때만 layout 로드하기