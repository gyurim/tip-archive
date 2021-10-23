# 30개 프로젝트로 배우는 Android 앱 개발
# Part 1. Android 개발 들어가기에 앞서
## Ch 01. Kotlin
- 세미콜론 생략
- System.out 수식어 사용 X -> print 사용
- 처음 시작하는 함수: main

### Function Expression 함수 선언 
```kotlin
// statements (구문식) 
// fun 함수 이름 (input value): 리턴 타입 
fun sum(a: Int, b: Int): Int {
	return a + b
}

// expressions (표현식) : 코틀린의 특징 
// 동일하게 작동함 
fun sum(a: Int, b: Int) = a + b 
```

### Value, Variable 상수, 변수 선언 
```kotlin
val a: Int = 1 // 명시적으로 Int형임을 선언 
val b = 2 // 2가 할당되는 순간, b는 Int형임을 추론하고 Int형으로 할당해줌
// 코틀린은 자료형을 써놓지 않아도, 타입 추론이 가능함 
val c = 3.14 // double형으로 자동 추론, 할당됨 
val d: String // String으로 선언했다면, 초기화
d = "필수로 있어야하는 구문" 
// d = "d의 초기값이 없으면 null이 될 수 있는데, d는 null이 될 수 없기 때문에."
// 상수(val)는 두 번 이상 초기화할 수 없다. (변수만 가능)
// 코틀린은 null-safe라는 개념이 있어서, null에 대해서 안전하게 참조할 수 있도록 코드를 작성해야함. 
val e: String?
var d: String = "첫번째 초기화"
e = "두번째 초기화"
```

### Type
- 정수형: Byte, Short, Int, Long
- 실수형: Float, Double
- Char, String, Boolean 
- 코틀린은 자바(JVM)와 100% 호환되기 때문에, 자바에 있는 타입들과 1:1 호환됨
- 타입 + ?: nullable하다. null이 될 수 있는 타입

### For 반복문 
- range 개념 사용
```kotlin
for (i in 1..5) {
	println(i)
}
// 1 2 3 4 5 

for (i in 6 downTo 0 step 2) {
	println(i)
}
// 6 4 2 0

for (i in 1..5 step 3) {
	println(i)
}
// 1 4

val numberList = listOf(100, 200, 300)
for (number in numberlist) {
	println(number)
}
// 100 200 300
```
- until
```kotlin
for (i in 0 until 10) {
	println(i)
}
// 0 1 2 3 4 5 6 7 8 9
```

### While 반복문 
- 자바와 비슷 
```kotlin
var x = 5
while (x > 0) {
	println(x)
	x--
}
// 5 4 3 2 1

x = 0
while (x > 0) {
	println(x)
	x--
}
//출력 없음

var y = 0
do {
	print(y)
	y--
} whlile(y >0)
// 0
```

### If 문 
- 자바와 비슷 
```kotlin
var max: Int
if (a>b) {
	max = a
} else {
	max = b
}

// As expression
val max = if (a > b) {
	print("Choose a")
	a
} else {
	print("Choose b")
	b
}
```

### When 문
- 자바의 Switch문과 동일 
- else 문은 필수로 들어가야 함 
```kotlin
when(x) {
	1 -> print("x==1")
	2 -> print("x==2")
	else -> { print("x is neither 1 nor 2")
	}
}

when(x) {
	0, 1 -> print("x==0 or x==1") // 콤마를 사용해 조건을 여러개로 할 수 있음 
	else -> print("otherwise")
}

when(x) {
	in 1..10 -> print("x는 1부터 10 범위 안에 있음")
	!in 10..20 -> print("x는 10부터 20 범위 안에 없음")
	else -> print("otherwise")
}

when(x) {
	is Int -> print("x는 Int임")
	else -> print("x는 Int형이 아님")
}
// is 연산자는 타입을 보는 연산자 
```

### Print, Println
- 차이점: print()는 출력 후 개행 X, println()은 출력 후 개행 