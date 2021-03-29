package com.taufik.gitser.utils

import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

class Utils {
    companion object {
        fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>){
            val spannableString = SpannableString(this.text)
            var startIndexOfLink = -1

            for (link in links) {
                val clickableSpan = object : ClickableSpan(){

                    override fun updateDrawState(ds: TextPaint) {
                        ds.color = ds.linkColor
                        ds.isUnderlineText = false
                    }

                    override fun onClick(view: View) {
                        Selection.setSelection((view as TextView).text as Spannable, 0)
                        view.invalidate()
                        link.second.onClick(view)
                    }
                }

                startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
                spannableString.setSpan(
                        clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            this.movementMethod = LinkMovementMethod.getInstance()
            this.setText(spannableString, TextView.BufferType.SPANNABLE)
        }
    }
}