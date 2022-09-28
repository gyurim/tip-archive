## 1. Android App Component
```
1. Activity
2. Fragment
3. Service
4. BroadcastReceiver
5. ContentProvider
```

Android App Component에는 4가지 유형이 있다.
- Activity
- Service
- Broadcast Receiver
- Content Provider

## 2. 다음 View 관련 함수 실행 과정 및 결과로 해당 View를 다시 measure 하지 않는 함수를 모두 고르세요
```
1. void invalidate()
2. void setMinimumHeight(int)
3. void requestLayout()
4. void setLayoutParams(ViewGroup.LayoutParams)
5. void setScaleX(float)
```

### View의 생명 주기 
<img width="666" alt="view의 생명주기" src="https://user-images.githubusercontent.com/31344894/129595878-5a52ec1a-44d8-425a-85e1-16f47312c81c.png">


### invalidate( )
- draw()를 다시 하도록 요청
- 단순한 변경 사항을 보여주기 위하여 다시 그리는 경우에는 invalidate()를 사용한다. 

### requestLayout()
- measure를 통한 사이즈 체크부터 다시함
- 사이즈가 바뀌어서 뷰 간의 경계에도 영향을 준다면 requestLayout()을 호출해야한다. 

### setMinimumHeight(int) 

## 3. RecyclerView.Adapter를 상속할 때 반드시 구현해야하는 함수를 모두 고르세요
```
1. int getItemCount()
2. int getItemViewType(View)
3. VH onCreateViewHolder(ViewGroup, int)
4. void onBindViewHolder(VH, int)
5. onAttachedToRecyclerView(RecyclerView)
```

### abstract int getItemCount() 
- RecyclerView Adapter에서 관리하는 아이템의 개수를 반환

### abstract viewHolder onCreateViewHolder(ViewGroup, int)

### abstract void onBindViewHolder(VH, int)


## 4. 다음 중 kotlin standard inline함수를 모두 고르세요
```
1. let
2. run
3. with
4. skip
5. fold
```

### inline 함수

## 5. 다음 중 비용이 가장 낮은 작업을 고르세요
```
1. 네트워크를 경유하여 접근 권한 확인
2. 디스크에서 100 바이트 문자열 읽기
3. 프로세스 전체에 공유되고 있는 객체에 접근 
4. 64 * 64 사이즈의 이미지를 디스플레이에 표시
5. 10개의 row를 가지는 데이터베이스 row 수 세기
```

## 6. 다음 중 AndroidManifest의 activity 요소에 지정할 수 있는 속성을 모두 고르세요. 
```
1. android:launchMode
2. android:configChanges
3. android:noHistory
4. android:allowBackup
5. android:screenOrientation
```

## 7. 다음 중 Android 10에서 반드시 사용자의 허가가 필요한 권한을 모두 고르세요
```
1. android.permission.INTERNET
2. android.permission.VIBRATE
3. android.permission.WAKE_LOCK
4. android.permission.READ_CONTACT
5. android.permission.READ_PHONE_STATE
```

## 8. View 생명주기에 따른 콜백 순서가 옳은 것을 고르세요

## 9. equals()와 ==의 차이를 서술해주세요

## 10. println(result)의 결과를 예측하고, 그렇게 예측한 이유를 서술해주세요
```kotlin
var result = 1

fun increase(): Boolean {
	result += 1
	return true
}

if (true || increase()) {
	println(result)
}
```

## 11. 아래 코드에서 x, y의 차이를 서술해주세요
```kotlin
class Test {
	private val foo = Foo()

	val x : Bar = foo.bar

	var y : Bar 
		get() = foo.bar
}
```

## 12. Activity.onSaveInstanceState(Bundle outState)의 역할에 대해 서술해주세요
- UI를 복원할 때 사용
- 구성 변경 혹은 메모리 부족에 의해 시스템이 Activity를 소멸시켜 Activity가 중지되기 시작하면 시스템이 onSaveInstanceState()를 호출하여 activity가 인스턴스 상태 bundle에 상태 정보를 저장할 수 있게 함
- 이후 onCreate() 매개변수 savedInstanceState에서 복원(null 체크 필요) 혹은 onRestoreInstanceState()에서 복원 
- 인스턴스 상태 bundle에는 단순하고 가벼운 데이터가 들어가야함. 원래 instance state bundle은 프로세스 중단에도 유지가 되지만, onSaveInstanceState()는 데이터를 디스크에 직렬화하여 저장하기 때문에 저장 용량 및 속도에 한계가 있음. 
- 직렬화 프로세스는 main thread에서 이루어지기 때문에, 직렬화가 장기적으로 이루어지면 프레임 하락 및 시각적인 끊김 현상 발생 

## 12-1. 또 다른 UI 복원를 위한 방법
- ViewModel을 사용해 UI 데이터 복원
- ViewModel은 Activity의 생명주기와 상관이 없으므로 ViewModel에 저장된 데이터는 Activity가 onRestart될 때, 유지하고 있던 데이터를 제공할 수 있음
- 메모리에 데이터를 보관하므로 네트워크 혹은 디스크에서 가져오는 것보다 검색 비용이 낮음
- 그러나, instance state bundle은 프로세스 중단에도 유지가 되지만, ViewModel은 유지되지 않기 때문에 동시에 사용하는 것이 좋음

## 13. Activity에서 onPause()와 onStop()이 어떤 조건에서 호출되는지 서술해주세요
- onPause(): Activity의 화면이 일부 가려졌을 때 호출됨. 투명 Activity, Dialog로 가려졌을 때도 onPause 호출
- onStop(): Activity의 화면이 완전히 가려졌을 때 호출됨.

- Activity A에서 Activity B로 이동하는 상황에서 Activity A는 onPause() 호출 및 background로 가게 됨. Activity B가 onCreate(), onStart(), onResume()을 모두 수행하면 Activity B는 foreground로 가게 되고 이때, Activity A는 onStop()이 호출된다. 

## 14. RecyclerView를 구성할 때 ViewHolder는 무엇이며, 왜 필요한지 서술해주세요
