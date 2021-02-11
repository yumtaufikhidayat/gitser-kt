package com.taufik.gitser.ui.fragment.bottomsheet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.taufik.gitser.R
import es.dmoral.toasty.Toasty
import java.lang.Exception

class BottomSheetProfileInfo : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var tvProfileInfoEmail: TextView
    private lateinit var tvProfileInfoGithubUrl: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_profile_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvProfileInfoEmail = view.findViewById(R.id.tvProfileInfoEmail)
        tvProfileInfoGithubUrl = view.findViewById(R.id.tvProfileInfoGithubUrl)

        setInitOnClick()
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetMenuTheme
    }

    private fun setInitOnClick() {
        tvProfileInfoEmail.setOnClickListener(this)
        tvProfileInfoGithubUrl.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvProfileInfoEmail -> developerEmail()
            R.id.tvProfileInfoGithubUrl -> developerProject()
        }
    }

    private fun developerEmail() {

        val email = "yumtaufik1997@gmail.com"
        tvProfileInfoEmail.makeLinks(Pair(email, View.OnClickListener {
            try {
                val intentEmail = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null))
                intentEmail.putExtra(Intent.EXTRA_EMAIL, email)
                intentEmail.putExtra(Intent.EXTRA_SUBJECT, "")
                intentEmail.putExtra(Intent.EXTRA_TEXT, "")
                startActivity(Intent.createChooser(intentEmail, "Send email"))
            } catch (e: Exception) {
                Toasty.error(requireContext(), "Silakan install aplikasi email terlebih dulu", Toast.LENGTH_SHORT).show()
                Log.e("errorEmail", "developerEmail: ${e.localizedMessage}")
            }
        }))
    }

    private fun developerProject() {
        val projectUrl = "https://github.com/yumtaufikhidayat/gitser-kt"
        tvProfileInfoGithubUrl.makeLinks(Pair(projectUrl, View.OnClickListener {
            try {
                val intentProject = Intent(Intent.ACTION_VIEW, Uri.parse(projectUrl))
                startActivity(Intent.createChooser(intentProject, "Open with:"))
            } catch (e: Exception) {
                Toasty.error(requireContext(), "Silakan install aplikasi browser terlebih dulu", Toast.LENGTH_SHORT).show()
                Log.e("errorProject", "developerProject: ${e.localizedMessage}")
            }
        }))
    }

    private fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>){
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