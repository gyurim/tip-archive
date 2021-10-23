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

## 13. Activity에서 onPause()와 onStop()이 어떤 조건에서 호출되는지 서술해주세요

## 14. RecyclerView를 구성할 때 ViewHolder는 무엇이며, 왜 필요한지 서술해주세요
