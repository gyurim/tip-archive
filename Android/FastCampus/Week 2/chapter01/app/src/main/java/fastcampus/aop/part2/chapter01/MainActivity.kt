package fastcampus.aop.part2.chapter01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val heightEditText: EditText = findViewById(R.id.heightEditText)
        val weightEditText = findViewById<EditText>(R.id.weightEditText)

        val resultButton = findViewById<Button>(R.id.resultButton)
        resultButton.setOnClickListener {
            // 람다식 (Action 추가)
            Log.d("MainActivity", "ResultButton 이 클릭되었습니다.")

            if (heightEditText.text.isEmpty() || weightEditText.text.isEmpty()) {
                Toast.makeText(this, "빈 값이 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // 어떤 부분에서 리턴할 것인지 명시
            }

            // 이 아래로는 절대 빈 값이 올 수 없음
            val height: Int = heightEditText.text.toString().toInt()
            val weight = weightEditText.text.toString().toInt()

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("height", height)
            intent.putExtra("weight", weight)
            startActivity(intent)
        }
    }
}