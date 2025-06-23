package com.example.backjeon

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.graphics.Color

class QuoteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val textView = TextView(context)

        // 다크모드 여부에 따라 텍스트 색상 설정
        val isDarkMode = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> true
            else -> false
        }

        val quotes = listOf(
            "“계속 가다 보면 결국 도착한다.”",
            "“생각이 운명을 만든다.”",
            "“성공은 우연이 아니라 노력의 결과다.”"
        )
        val randomQuote = quotes.random()

        textView.text = randomQuote
        textView.textSize = 18f
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.setPadding(72, 12, 32, 32)
        textView.setTextColor(if (isDarkMode) Color.WHITE else Color.BLACK)

        return textView
    }

}
