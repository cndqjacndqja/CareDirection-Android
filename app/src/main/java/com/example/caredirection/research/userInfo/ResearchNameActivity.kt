package com.example.caredirection.research.userInfo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.example.caredirection.R
import com.example.caredirection.common.toast
import kotlinx.android.synthetic.main.activity_research_name.*

class ResearchNameActivity : AppCompatActivity() {

    private var edt_username: EditText? = null
    private var btn_name_next: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_research_name)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        cl_name.setPadding(0, statusBarHeight(this), 0, 0)

        //TODO: ResearchController 를 확인해서, 어느 지점까지 유요한 정보가 들어있는지 판단하고
        //TODO: 유효하지 않은 정보가 들어있는 화면까지 Intent list 를 만들어서
        //TODO: startActivities 를 호출해주면 됩니다.

        makeController()
    }

    // 상태바 배경투명 설정
    fun statusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")

        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId)
        else 0
    }

    // 사용자 입력받아서 초기화
    private fun makeController(){
        edt_username = findViewById((R.id.edt_username))
        btn_name_next = findViewById(R.id.btn_gender_next)

        // 이름 입력 검사
        edt_username?.addTextChangedListener(object: TextWatcher {
            var txt_length = 0
            override fun afterTextChanged(p0: Editable?) {
                txt_length = edt_username?.length()!!
                if(txt_length > 0){
                    btn_name_next?.isEnabled = true
                    btn_name_next?.setTextColor(resources.getColor(R.color.colorPrimary))
                }
                else{
                    btn_name_next?.isEnabled = false
                    btn_name_next?.setTextColor(resources.getColor(R.color.colorTranslucenceLight))
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        // 버튼 활성화처리
        btn_name_next?.setOnClickListener{
            val name = edt_username?.text.toString()

            // 이름 빈칸일 경우,
            if(name.isEmpty()){
                toast("아직 이름이 정해지지 않았습니다.")
            }
            else{
                val gender_intent = Intent(this,ResearchGenderActivity::class.java)
                gender_intent.putExtra("username",name)

                startActivity(gender_intent)
            }
        }
    }
}
