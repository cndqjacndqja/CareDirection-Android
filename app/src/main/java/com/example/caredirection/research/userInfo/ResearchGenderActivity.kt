package com.example.caredirection.research.userInfo

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import com.example.caredirection.R
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.WindowManager
import com.example.caredirection.common.toast
import com.example.caredirection.research.DB.ResearchKeeper
import kotlinx.android.synthetic.main.activity_research_gender.*


class ResearchGenderActivity : AppCompatActivity() {

    private lateinit var keeper :ResearchKeeper

    private var gender_selec: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_research_gender)
        keeper = ResearchKeeper(this)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        cl_gender.setPadding(0, statusBarHeight(this), 0, 0)

        keeper.gender?.let {
            when (it) {
                ResearchKeeper.MALE -> {
                    chtxt_gender_women.isChecked = false
                    chtxt_gender_man.isChecked = true
                    gender_selec = true
                }
                else -> {
                    chtxt_gender_women.isChecked = true
                    chtxt_gender_man.isChecked = false
                    gender_selec = true
                }
            }
        }

        keeper.year?.let {
            txt_year_picker.text = it
        }

        checkSelectButton()
        makeController()
        setColorInPartitial()
    }

    // 상태바 배경투명 설정
    fun statusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")

        return if (resourceId > 0) context.resources.getDimensionPixelSize(resourceId)
        else 0
    }

    // 사용자 입력받아서 초기화
    private fun makeController(){
        val name = keeper.name

        txt_gender_nametitle?.text = name + "님,"
        txt_gender_namesubtitle?.text = name + "님만의"

        btn_gender_next?.setOnClickListener{
            keeper.gender = if (chtxt_gender_women.isChecked) ResearchKeeper.FEMALE else ResearchKeeper.MALE
            keeper.year = txt_year_picker.text.toString()

            val disease_intent = Intent(this, ResearchDiseaseActivity::class.java)
            startActivity(disease_intent)
        }

        chtxt_gender_women?.setOnClickListener{
            chtxt_gender_women?.isChecked = true
            chtxt_gender_man?.isChecked = false
            gender_selec = true
            checkSelectButton()
        }

        chtxt_gender_man?.setOnClickListener{
            chtxt_gender_women?.isChecked = false
            chtxt_gender_man?.isChecked = true
            gender_selec = true
            checkSelectButton()
        }

        txt_year_picker?.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_year_picker, null)

            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface, i ->
                    val picker = dialogView.findViewById<NumberPicker>(R.id.number_picker)
                    txt_year_picker?.text = picker.value.toString()
                    checkSelectButton()
                }
                .setNegativeButton("취소") { dialogInterface, i ->
                    checkSelectButton()
                }
                .show()
        }
    }

    private fun checkSelectButton(){
        if(gender_selec && txt_year_picker.text.isNotBlank()){
            btn_gender_next.isEnabled = true
        }
        else{
            btn_gender_next.isEnabled = false
        }
    }

    // 강조타이틀 설정
    private fun setColorInPartitial(){
        val builder = SpannableStringBuilder(txt_gender_subtitle?.text.toString())

        builder.setSpan(
            ForegroundColorSpan(Color.parseColor("#ebf0b0")),
            0,
            9,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        txt_gender_subtitle?.text = builder
    }
}
