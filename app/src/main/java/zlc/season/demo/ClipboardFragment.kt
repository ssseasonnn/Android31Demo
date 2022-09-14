package zlc.season.demo

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import zlc.season.demo.databinding.FragmentClipboardBinding


class ClipboardFragment : Fragment() {
    private var binding: FragmentClipboardBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentClipboardBinding.inflate(inflater, container, false)
            .also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            btnCopyNormal.setOnClickListener {
                copyText(tvNormal.text.toString())
            }
            btnCopyPass.setOnClickListener {
                copyPass(tvPrivate.text.toString())
            }
        }
    }

    fun copyText(textCopied: String) {
        val clipboardManager =
            requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", textCopied))
    }

    fun copyPass(pass: String) {
        val clipboardManager =
            requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("", pass)
        clipData.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                description.extras = PersistableBundle().apply {
                    if (Build.VERSION.SDK_INT >= 33) {
                        putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
                    } else {
                        putBoolean("android.content.extra.IS_SENSITIVE", true)
                    }
                }
            }
        }
        clipboardManager.setPrimaryClip(clipData)
    }
}