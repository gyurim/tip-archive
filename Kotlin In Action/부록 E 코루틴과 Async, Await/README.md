# 코루틴과 Async/Await
## E.1 코루틴이란?
> 코루틴은 컴퓨터 프로그램 구성 요소 중 하나로 비선점형 멀티태스킹을 수행하는 일반화한 서브루틴이다.
>
> 코루틴은 실행을 일시 중단(suspend)하고 재개(resume)할 수 있는 여러 진입 지점(entry-point)을 허용한다. 

### 서브루틴
- 여러 명령어를 모아 이름을 부여해서 반복 호출할 수 있게 정의한 프로그램 구성요소
- 함수
- 객체지향 언어에서는 메소드도 서브루틴이라 할 수 있음
- 어떤 서브루틴에 진입하는 방법❓
    - 해당 함수를 호출하면 서브루틴의 맨 처음부터 실행이 됨 -> 이 방법밖에 없음 
    - 그때마다 활성 레코드(activation record)라는 것이 스택에 할당되면서 서브루틴 내부의 로컬 변수 등이 초기화됨 
    - 반면 서브루틴 안에서 여러 번 return을 사용할 수 있기 때문에 서브루틴이 실행을 중단하고 제어를 호출한 쪽에게 돌려주는 지점은 여러개가 있을 수 있음 
        - 다만 일단 서브루틴에서 반환되고 나면 활성 레코드가 스택에서 사라지기 때문에 실행 중이던 모든 상태를 잃음 
        - 따라서 서브루틴을 여러 번 반복 실행해도 항상 같은 결과를 반복해서 얻게 됨 

### 멀티태스킹 & 비선점형
- 멀티태스킹
    - 여러 작업을 사용자가 볼 때 동시에 수행하는 것처럼 보이거나 실제로 동시에 수행하는 것 
- 비선점형
    - 멀티태스킹의 각 작업을 수행하는 참여자들의 실행을 운영체제가 강제로 일시 중단시키고 다른 참여자를 실행하게 만들 수 없다는 뜻 
- 따라서 각 참여자들이 자발적으로 협력해야만 비선점형 멀티태스킹이 제대로 작동할 수 있음 

### 코루틴의 제어 흐름 VS 일반적인 함수의 제어 흐름 
- 코루틴이란 서로 협력해서 실행을 주고받으면서 작동하는 여러 서브루틴
- generator 예시
    - 어떤 함수 A가 실행되다가 제네레이터인 코루틴 B를 호출하면 A가 실행되던 스레드 안에서 코루틴 B의 실행이 시작됨
    - 코루틴 B는 실행을 진행하다가 실행을 A에 양보함 (yield 명령을 통해)
    - A는 다시 코루틴을 호출했던 바로 다음 부분부터 실행을 계속 진행하다가 또 코루틴 B를 호출함
    - ❗️이때 B가 일반적인 함수라면 로컬 변수를 초기화하면서 처음부터 실행을 다시 시작하겠지만, ❗️코루틴이면 이전에 yield로 실행을 양보했던 지점부터 실행을 계속하게 됨 

<img width="700" alt="스크린샷 2022-11-19 오후 7 46 03" src="https://user-images.githubusercontent.com/31344894/202846874-b359f071-387e-4c43-876d-1b01d96d17d1.png">


## E.2 코틀린의 코루틴 지원: 일반적인 코루틴
> 코틀린은 특정 코루틴을 언어가 지원하는 형태가 아니라, 코루틴을 구현할 수 있는 기본 도구를 언어가 제공하는 형태
>  
> 코틀린의 코루틴 지원 기본 기능들은 kotlin.coroutine 패키지 밑에 있고 kotlin 1.3부터는 코틀린을 설치하면 별도의 설정 없이도 모든 기능을 사용할 수 있음 

### 여러가지 코루틴
- 코루틴 빌더: 코루틴을 만들어주는 것
- 코틀린에서는 코루틴 빌더에 원하는 동작을 람다로 넘겨서 코루틴을 만들어 실행하는 방식으로 코루틴을 활용 

### CoroutineScope.launch
- lanunch는 코루틴을 Job을 반환하며 **만들어진 코루틴은 기본적으로 즉시 실행**됨 
- launch가 반환한 Job의 cancel()을 호출해 코루틴 실행을 중단시킬 수 있음 
- launch가 작동하려면 CoroutineScope 객체가 블록의 this로 지정돼야 하는데 다른 suspend 함수 내부라면 해당 함수가 사용 중인 CoroutineScope가 있겠지만, 그렇지 않은 경우에는 GlobalScope를 사용하면 됨 
    - launch가 작동되기 위해 CoroutineScope 객체가 블록의 this로 지정되어야하는 이유❓
        - launch가 받는 블록 타입이 suspend CoroutineScope.() -> Unit이기 때문

#### ☑️ GlobalScope를 사용하는 코드 
```Kotlin
fun log(msg: String) // 생략 

fun launchInGlobalScope() {
    GlobalScope.launch {
        log("coroutine started.")
    }
}

fun main() {
    log("main() started")
    launchInGlobalScope()
    log("launchInGlobalScope() executed")
    Thread.sleep(5000L)
    log("main() terminated")
}
```
- 실행 결과 
```
mainThread: main() started
mainThread: launchInGlobalScope() executed
DefaultDispatcher-worker-2 Thread: coroutine started
mainThread: main() terminated
```
- 📝 실행 결과로부터 알 수 있는 점
    - 메인 함수와 GlobalScope.launch가 만들어낸 코루틴이 서로 다른 스레드에서 실행됨
    - ❗️GlobalScope는 메인 스레드가 실행 중인 동안만 코루틴의 동작을 보장해줌 
        - 따라서, 코드 속 Thread.sleep(5000L)을 없애면 코루틴이 아예 실행되지 않을 것! 
        - Thread.slee()을 없에면 코루틴이 실행되지 않는 이유❓
            - launchInGlobalScope()가 호출한 **launch는 스레드가 생성되고 시작되기 전에 메인 스레드의 제어를 main()에 돌려주기 때문에** 따로 sleep()을 해주지 않으면 main()이 바로 끝나고 메인 스레드가 종료되면서 바로 프로그램 전체가 끝나버림 -> **GlobalScope()를 사용할 때 주의할 점**
        - GlobalScope.launch()를 사용했을 때 main()함수가 끝나면 프로그램 전체가 끝나버리는 것을 방지하기 위한 방법 
            - 비동기적으로 launch를 실행
            - launch가 모두 다 실행될 때까지 기다리기
                - runBlocking()
                    - 코루틴의 실행이 끝날 때까지 현재 스레드를 블록시키는 함수 
                    - runBlocking은 CoroutineScope의 확장 함수가 아닌 일반 함수이기 때문에 별도의 코루틴 스코프 객체 없이 사용 가능! 

####  ☑️ runBlocking()을 사용하는 코드 
```Kotlin
fun runBlockingexample(){
    runBlocking {
        launch {
            log("coroutine started.")
        }
    }
}

fun main() {
    log("main() started")
    runBlockingexample()
    log("runBlockingexample() executed")
    Thread.sleep(5000L)
    log("main() terminated")
}
```
- 실행 결과
```
mainThread: main() started
mainThread: coroutine started
mainThread: runBlockingexample() executed
mainThread: main() terminated
```
- 📝 실행 결과로부터 알 수 있는 점
    - 모두 main 스레드에서 실행됨 
    - 그러나 이 코드는 스레드나 다른 비동기 도구와 다른 장점을 찾아볼 수 없음 -> yield() 사용해 협력 

#### ☑️ runBlocking() & yield()을 사용하는 코드 
```kotlin 
fun yieldExample() {
    runBlocking {
        launch {
            log("1")
            yield()
            log("3")
            yield()
            log("5")
        }
        log("after first launch")
        launch {
            log("2")
            delay(1000L)
            log("4")
            delay(1000L)
            log("6")
        }
        log("after second launch")
    }
}
```
- 실행 결과 
```
mainThread: main() started
mainThread: after first launch
mainThread: after second launch
mainThread: 1 (34초)
mainThread: 2 (34초)
mainThread: 3 (34초)
mainThread: 5 (34초)
mainThread: 4 (35초)
mainThread: 6 (36초)
mainThread: after runBlocking
mainThread: yieldExample() executed
mainThread: main() terminated 
```
- 📝 실행 결과를 통해 알 수 있는 점 
    - launch는 즉시 반환됨
    - runBlocking은 내부 코루틴이 모두 끝난 다음에 반환됨
    - delay()를 사용한 코루틴은 그 시간이 지날 때까지 다른 코루틴에게 실행을 양보함
        - delay(1000L) 대신 yield()를 쓰면 차례대로 1, 2, 3, 4, 5, 6이 표시될 것
        - 그러나 두 번째 코루틴이 delay() 상태에 있었기 때문에 다시 제어가 첫 번째 코루틴에게 돌아왔음 

### CoroutineScope.async
- launch와 같은 역할을 수행함
> launch와 차이점
> - launch는 Job을 반환
> - async는 Deffered를 반환 
>   - Deffered는 Job을 상속한 클래스이기 때문에 launch 대신 async를 사용해도 문제 없음 
>
> Job VS Deffered
> - Job은 아무 타입 파라미터가 없으나 Deffered는 타입 파라미터가 있는 제네릭 타입이다.
> - Deffered 안에는 await() 함수가 정의되어 있음

- async는 코드 블록을 비동기로 실행할 수 있음 
    - (제공하는 코루틴 컨텍스에 따라 여러 스레드를 사용하거나 한 스레드 안에서 제어만 왔다 갔다 할 수 도 있음)
- async가 반환하는 Deffered의 await을 사용해서 코루틴이 결과 값을 내놓을 때까지 기다렸다가 결과값을 얻어낼 수 있음 

#### ☑️ 1부터 3까지 수를 더하는 과정을 async/await을 사용해 처리하는 코드 
```kotlin
fun sumAll() {
    runBlocking {
        val d1 = async { delay(1000L); 1}
        log("after async(d1)")

        val d2 = async { delay(2000L); 2}
        log("after async(d2)")

        val d3 = async { delay(3000L); 3}
        log("after async(d3)")

        log("1+2+3 = ${d1.await() + d2.await() + d3.await()}")
        log("after await all & add")
    }
}
```
- 실행 결과 
```
mainThread: after async(d1) (45초)
mainThread: after async(d2) (45초)
mainThread: after async(d3) (45초)
mainThread: 1+2+3 = 6 (48초)
mainThread: after await all & add (48초)
```
- 📝 실행 결과를 통해 알 수 있는 점 
    - 6이라는 결과를 얻을 때까지 총 3초가 걸렸음
    - **모든 async 함수들이 메인 스레드 안에서 실행**됨
        - 이 부분이 **스레드를 여럿 사용하는 병렬 처리 방식과 async/await을 사용하는 병렬 처리 방식의 가장 큰 차이점**
        - 이 코드에서는 3개의 비동기 코드만 실행했으나, 비동기 코드가  늘어남에 따라 async/await을 사용한 비동기가 **성능이 훨씬 좋을 것**임 
    - ✨ 실행하려는 작업이 시간이 얼마 걸리지 않거나 I/O에 의한 대기 시간이 크고 CPU 코어 수가 작아 동시에 실행할 수 있는 스레드 개수가 한정된 경우에 코루틴과 일반 스레드를 사용한 비동기 처리 사이에 차이가 커짐 

### 코루틴 컨텍스트와 디스패처 
#### CoroutineContext
- CoroutineContext는 실제로 코루틴이 실행 중인 여러 작업(Job 타입)과 디스패처를 저장하는 일종의 맵
- Element의 집합 
    - 코루틴을 어떻게 처리할 것인지에 대한 여러가지 정보(element)를 포함 
- Interface로 코루틴에 대한 설정 요소(Element)를 등록하고 Scope의 속성이 됨 
- coroutineContext property를 통해 현재 coroutine context에 접근 가능함
- coroutineContext는 immutable
- plus op를 통해 element들을 추가 가능하고 이를 통해 새로운 context 객체가 생성됨 

#### Job
- 우리가 말하는 coroutine은 Job으로 표현됨
- coroutine의 생명 주기, 취소, 부모-자식 관계 등을 책임짐
- 현재 Job은 현재 coroutine context에서 접근 가능 

#### CoroutineScope
- coroutineContext property 하나만 가지고 있음
- CoroutineScope는 CoroutineContext 필드를 launch 등의 확장 함수 내부에서 사용하기 위한 매개체 역할만 담당 
    - launch, async 등은 모두 CoroutineScope의 확장함수 

> 코틀린 런타임은 CoroutineContext를 사용해서 다음에 실행할 작업을 선정하고 어떻게 스레드에 배정할지에 대한 방법을 결정함 

```kotlin
launch { // 부모 컨텍스트를 사용 (이 경우 main)

}

launch(Dispatchers.Unconfined) { // 특정 스레드에 종속되지 않음 ? 메인 스레드 사용 

}

launch(Dispatchers.Default) { // 기본 디스패처를 사용 

}

launch(newSingleThreadContext("MyOwnThread")) { // 새 스레드를 사용 

}
```
> 같은 launch를 사용하더라도 전달하는 컨텍스트에 따라 서로 다른 스레드 상에서 코루틴이 실행됨을 알 수 있음 

### 코루틴 빌더와 일시 중단 함수 
#### 코루틴 빌더
- 코루틴을 만들어주는 함수
- launch, async, runBlocking, produce, actor 
    - produce
        - 정해진 채널로 데이터를 스트림으로 보내는 코루틴을 만듦
        - ReeiveChannel<>을 반환하고 채널로부터 메세지를 전달받아 사용할 수 있음 
    - actor
        - 정해진 채널로 메시지를 받아 처리하는 액터를 코루틴으로 만듦 
        - SendChannel<>을 반환하고 채널의 send() 메소드를 통해 액터에게 메시지를 보낼 수 있음 

#### 일시 중단 함수 
- delay, yield, withContext, withTimeout, withTimeoutOrNull ...
- withContext
    - 다른 context로 코루틴을 전환
- withTimeout
    - 코루틴이 정해진 시간 안에 실행되지 않으면 예외를 발생시키게 함
- withTimeoutOrNull
    - 코루틴이 정해진 시간 안에 실행되지 않으면 null을 결과로 돌려준다.
- awaitAll
    - 모든 작업의 성공을 기다림. 작업 중 어느 하나가 예외로 실패하면 awaitAll도 그 예외로 실패함
- joinAll
    - 모든 작업이 끝날 때까지 현재 작업을 일시 중단시킴 

## E.3 suspend 키워드와 코틀린의 일시 중단 함수 컴파일 방법
- 일시 중단 함수를 코루틴이나 일시 중단 함수가 아닌 함수에서 호출하는 것은 컴파일 수준에서 금지된다. 
    - main() 함수 안에 yield()를 넣으면 인텔리제이에서 보면 빨간 줄이 그어지면서 ```"Suspend function 'yield' should be called only from a coroutine or another suspend function``` 오류 발생 

- 일시 중단 함수를 만드는 방법
    - 함수 정의의 ```fun``` 앞에 ```suspend```를 넣으면 일시 중단 함수를 만들 수 있음 

- suspend 함수는 어떻게 작동할까❓
    - 예를 들어 일시 중단 함수 안에서 yield()를 해야하는 경우, 어떤 동작이 필요할지 생각해보기 
        - (1) 코루틴에 진입할 때와 코루틴에서 나갈 때 코루틴이 실행 중이던 상태를 저장하고 복구하는 등의 작업을 할 수 있어야 한다. 
        - (2) 현재 실행 중이던 위치를 저장하고 다시 코루틴이 재개될 때 해당 위치부터 실행을 재개할 수 있어야한다.
        - (3) 다음에 어떤 코루틴을 실행할지 결정한다. 
    - **(3) 동작**은 코루틴 컨텍스트에 있는 **디스패처에 의해 수행**됨
    - 일시 중단 함수를 컴파일하는 컴파일러는 (1), (2) 작업을 할 수 있는 코드를 생성해내야 함 
        - 이때, 코틀린는 continuation passing style(CPS) 변환과 상태 기계(state machine)을 활용해 코드를 생성해냄 

#### CPS 변환
- CPS 변환은 프로그램의 실행 중 continuation을 뽑고, 그 함수에게 **현재 시점까지 실행한 결과를 넘겨서 처리하게 만드는 소스코드 변환 기술**이다. 
    - continuation
        - 특정 시점 이후에 진행해야하는 내용을 별도의 함수
- CPS의 장점 
    - CPS를 사용하는 경우 프로그램이 다음에 해야할 일이 항상 continuation이라는 함수 형태로 전달되므로 **나중에 할 일을 명확히 알 수 있고, 그 컨티뉴에이션에 넘겨야할 값이 무엇인지도 명확하게 알 수 있기 때문에** 프로그램이 실행 중이던 특정 시점의 맥락을 잘 저장했다가 필요할 때 다시 재개할 수 있음
- CPS는 콜백 스타일 프로그래밍과 유사함 
- 예제 
    ```kotlin
    suspend fun example(v: Int): Int {
        return v*2
    }
    ```
    - 코틀린 컴파일러는 위의 suspend 함수를 컴파일하면서 뒤에 Continuation을 인자로 만들어 붙여줌 
    ```kotlin
    public static final Object example(int v, @NotNull Continuation var1) 
    ```
    - 이 함수를 호출할 때는 함수 호출이 끝난 후 수행해야할 작업을 var1에 Continuation으로 전달하고 함수 내부에서는 필요한 모든 일을 수행한 다음에 결과를 var1에 넘기는 코드를 추가함 
        - v*2를 인자로 Continuation을 호출하는 코드 

- CPS를 사용하면 코루틴을 만들기 위해 필수적인 일시 중단 함수를 만드는 문제가 쉽게 해결될 수 있다. 
- 하지만, 모든 코드를 전부 CPS로만 변환하면 지나치게 많은 중간 함수들이 생길 수 있으므로, 상태 기계를 적당히 사용해 코루틴이 제어를 다른 함수에 넘겨야하는 시점에서만 Continuation이 생기도록 만들 수 있음 