# BMI 계산기
- margin: 어떤 컴포넌트의 밖에 여백을 추가 
- padding: 어떤 컴포넌트의 안에 여백을 추가 (컴포넌트 안 컨텐츠의 사이즈를 줄여서)
- dp: 화면이 가지는 사이즈의 기본 단위 
	- dp를 사용하는 이유: 안드로이드 핸드폰의 해상도가 다를 수 있기 때문에 통합하여 사용하기 위함
	- 설정의 텍스트 크기 값의 변화에 상관없이 일정한 크기를 유지함 
- sp: 휴대폰 속성에서 텍스트 사이즈를 설정할 수 있는데, 사용자가 설정한 값에 영향을 받아 텍스트 크기가 커지거나 작아진다. 

- Reformat Code 단축키 : option + command + L 

- Intent 
	- Activity A에서 startActivity()를 호출하면, Intent는 먼저 Android System에 전달되게 된다. 그 다음 Android System은 목적지 Activity(= B Activity)를 찾는다. (이때, Manifest에서 찾음. 따라서 Manifest에 Activity를 등록해줘야함)  다음 목적지 Activity를 실행함. 