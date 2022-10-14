package com.yuno.payments.example.ui.views

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View.OnFocusChangeListener
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import com.yuno.payments.example.R
import com.yuno.payments.example.extensions.setCursorColor

class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : AppCompatEditText(context, attrs) {

    init {
        setStyleEditText()
        this.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                setEditTextState(EditTextState.FOCUS)
            } else {
                setEditTextState(EditTextState.NORMAL)
            }
        }
    }

    private fun setStyleEditText() {
        ellipsize = TextUtils.TruncateAt.END
        maxLines = 1
        TextViewCompat.setTextAppearance(this, R.style.TextBody_Black)
        this.setPadding(
            context.resources.getDimensionPixelOffset(com.yuno.payments.R.dimen.yuno_spacing_large),
            context.resources.getDimensionPixelOffset(com.yuno.payments.R.dimen.yuno_spacing_medium),
            context.resources.getDimensionPixelOffset(com.yuno.payments.R.dimen.yuno_spacing_large),
            context.resources.getDimensionPixelOffset(com.yuno.payments.R.dimen.yuno_spacing_medium)
        )
        setEditTextState(EditTextState.NORMAL)
    }

    private fun onViewError(hasError: Boolean) {
        if (hasError) {
            setEditTextState(EditTextState.ERROR)
        } else if (hasFocus()) {
            setEditTextState(EditTextState.FOCUS)
        } else setEditTextState(EditTextState.NORMAL)
    }

    private fun setEditTextState(state: EditTextState) {
        when (state) {
            EditTextState.ERROR -> {
                setBackgroundResource(R.drawable.bg_error_edit_text)
                setCursorColor(this.context, R.color.yuno_font_black_light)
            }
            EditTextState.FOCUS -> {
                setBackgroundResource(R.drawable.bg_focus_edit_text)
                setCursorColor(this.context, R.color.yuno_purple_light)
            }
            EditTextState.NORMAL -> {
                setBackgroundResource(R.drawable.bg_edit_text)
                setCursorColor(this.context, R.color.yuno_font_black_light)
            }
        }
    }

    val isValid: Boolean
        get() {
            return if (isVisible) {
                if (text.toString().isNotEmpty()) {
                    onViewError(false)
                    true
                } else {
                    onViewError(true)
                    false
                }
            } else true
        }
}

enum class EditTextState { FOCUS, ERROR, NORMAL }