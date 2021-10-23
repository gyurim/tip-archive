# Kotlin으로 개발하기 VS Java로 개발하기 
## Null Safe 
```kotlin
Integer a = 100; // 자바 코드
val b: Int? = 100
val c: Int = 100

a = null; // Integer 타입은 클래스 형식이라 null 들어갈 수 있음 
// 중략 
a.sum(); // NullPointException이 날 수도 있음 

// null safe한 코드를 구성해야함 
if (a != null) {
	a.sum();
}

b?.sum() // null일 경우 실행하지 않음 
c.sum() // 애초에 nullsafe함 
```

## Scope Funtion (apply, with, let, also, run)
- 코드의 가독성 높힘 

### Apply 함수
- 객체의 확장 함수 
- 블럭 함수를 literal function receiver로 받기 때문에, 내부에서는 this를 통해 객체에 접근할 수 있다. (this는 생략 가능) 
- literal function receiver는 람다의 객체를 축약해서 넘겨준다는 의미 
- 반환값은 이 객체를 반환함 

- apply 함수는 블럭 내부에서 객체의 property에 접근할 수 있고 반환값이 객체 자신이 되기 때문에, **주로 객체를 초기화할 때 사용**한다. 

```kotlin
val person = Person().apply {
	firstName = "Fast" // this 생략 
	lastName = "Campus"
}

// 동일한 자바 코드 
Person person = new Person();
person.firstName = "Fast";
person.lastName = "Campus";
```

### Also 함수 
- 객체가 parameter를 통해 전달됨
- 람다의 입력값으로 내려오게 되고 람다 입력값은 it으로 접근 가능 
- 반환값은 이 객체를 반환함 
- also 함수는 람다 입력값으로 받고, 그 객체를 다시 반환하기 때문에 **객체의 유효성을 확인 혹은 print 함수를 통한 디버깅 용도로 사용**한다. 
```kotlin
Random.nextInt(100).also {
	print("getRandomInt() generated value $it")
}

Random.nextInt(100).also { value ->
	print("getRandomInt() generated value $value")
} // 결과값을 람다의 parameter인 value로 받음 

// 동일한 자바 코드 
int value = Random().nextInt(100);
System.out.print(value);
```

### Let 함수 
- let 함수는 null이 아닌 객체에서 람다를 사용(실행)할 때, 사용한다.
- let 함수는 apply, also 함수와 다르게 코드 블럭의 수행 결과가 반환됨 
- let 함수는 **null safe한 코드를 작성하기 위해 사용**된다. 
 
```kotlin
val number: Int?
val snumNumberStr = number?.let { // let 안으로 들어오는 변수는 null이 아니여야지만, 뒤에 있는 함수가 실행됨 
	"${sum(10, it)}"
}

// 동일한 자바 코드
Integer number = null;
String snumNumberStr = null;

if (number != null) {
	snumNumberStr = "" + sum(10, number);
}
```

```kotlin
val number: Int?
val snumNumberStr = number?.let { // let 안으로 들어오는 변수는 null이 아니여야지만, 뒤에 있는 함수가 실행됨 
	"${sum(10, it)}"
}.orEmpty() // string이 nullable일 경우에만 orEmpty() 사용 가능 

// 동일한 자바 코드
Integer number = null;
String snumNumberStr = null;

if (number != null) {
	snumNumberStr = "" + sum(10, number);
} else {
	snumNumberStr = "";
}
```

### With 함수 
- with 함수의 반환값은 람다의 결과값
- with함수는 객체로 떨어질 수 있다. 
```kotlin
val person = Person()

with(person) { // person 안에 있는 객체의 함수, 변수를 this를 통해 호출할 수 있다. 
	work()
	sleep()
	println(age)
} // person에 있는 함수 work(), sleep()와 println(age)를 한 번에 실행하겠다는 의미 

// 동일한 자바 코드
Person person = new Person();

person.work();
person.sleep();
System.out.println(person.age);
```

### Run 함수 
- run 함수는 **어떤 값을 계산할 필요가 있거나, 객체의 구성( = 초기화)과 결과 구성이 한 번에 있을 때 유용**함. 
- 반환값은 람다의 결과값 
- object reference인 this가 들어오기 때문에 with 함수와 비슷하지만, with 함수는 확장함수 (ex. person.with)로 사용 불가능, run 함수는 확장함수(ex. service.run) 로 사용 가능  
```kotlin
val result = service.run {
	port = 8080
	query()
}

service.port = 8080
Result result = service.query()
```

## Data Class 
- 데이터를 저장하는 목적 
- 일반 자바 클래스와 다르게, copy(), toString(), hashCode(), equal() 등이 자동으로 만들어짐 
- 주로 **model 클래스를 만들 때, data class를 사용**한다. 
- 코틀린에서는 getter & setter를 사용해 객체에 접근하기 보단, property에 직접 접근하는 형식으로 사용하기 때문에, getter & setter 함수는 코틀린 클래스를 만들 때, 자동으로 생성해준다. 
	- 이유: 코틀린 클래스 파일에서 작성해주었지만, 자바 코드에서 이 코틀린 코드를 사용할 수도 있기 때문. 
	- 자바 코드에서 코틀린 코드를 사용할 때, 바로 property의 직접 접근이 불가능하기 때문에 getter & setter 함수를 자동으로 만들어주고 있음 
	- getter & setter가 자동으로 생성되는 조건: 
		- data class JavaObject(val s: String) : s는 val, 따라서 setter 생성 X 
		- data class JavaObject(var s: String) : getter, setter가 둘 다 생성

```kotlin
data class JavaObject(val s: String)

// 동일한 자바 코드  
public class JavaObject {
	private String a;

	JavaObject(String a) {
		this.s = s;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}
	// copy
	// toString
	// hashCode 등등 생략 
}
```

## Lambda Expression 
- 코틀린은 함수형 언어에 가까운 특징을 가졌기 때문에, 람다식을 쉽게 이용할 수 있도록 제공함 
- 람다식은 함수에 함수를 전달하고, 전달된 함수에서 함수를 실행시키는 역할 
```kotlin
button.setOnClickListener { v -> 
}
// 인터페이스에 메서드가 1개밖에 없을 땐, 람다식으로 구현해 넘겨줄 수 있다. 
// v 대신 it으로도 접근 가능 

// 동일한 자바 코드 
button.setOnClickListener(new View.OnClickListener() {
	@override
	public void onClick(View view) {
	// 생략  
	} 
}) 
// OnClickListener() 인터페이스 안에는 onClick 메서드가 있어서, 메서드 구현체를 button에 넘긴 다음, 버튼에 실제 클릭이 일어났을 때, 구현체에 있는 onClick을 실행하게 되는 방식
```

## lateinit
- NullSafe한 코드를 사용하기 위해서 non-null Type으로 변수를 선언함 
- 초기값이 없는 변수는 어떻게 초기화를 해야할까? 초기값이 없으면 변수 선언 자체가 안되는데 
- **lateinit은 non-null한 타입만 가능하고 로컬 변수에서는 사용 불가능함 (따라서 전역변수로 선언했을 때 사용)**
```kotlin
var nullableNumber: Int? = null
lateinit var lateinitNumber: Int

// 추후 초기화하는 코드
lateinitNumber = 10 // 초기화하지 않으면 실행 시 에러 
// 사용할 때
nullableNumber?.add()

lateinitNumber.add() // null safe한 코드로 사용 가능 
```

## lazy init
- 나중에 init을 해주겠다는 의미 
- NullSafe한 코드를 사용하기 위해서 non-null Type으로 변수를 선언함
- 변수는 미리 선언해놓고 사용할 때 할당해주면 안될까? 
- **lazy init 변수는 사용하기 전 할당되지 않음**
- android에서 view를 나중에 할당하는 경우가 있음. view는 다른 곳에서도 값이 할당 가능한데, view의 속성들을 lazy init으로 넣어놓고 변수를 사용하지 않을 경우엔 lazy init에 있는 할당값이 실행되지 않기 때문에 view가 제대로 그려지지 않음. 
- 따라서, **lazy init을 사용할 때는 꼭 사용해줘야함** 

```kotlin
val lazyNumber: Int by lazy {
	100 
}

// 사용하기 전까지는 lazyNumber라는 변수에 100이 할당되지 않음. 
lazyNumber.add()
// 사용할 때 100이 할당됨 
```