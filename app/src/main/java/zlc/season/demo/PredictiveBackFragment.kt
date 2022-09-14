package zlc.season.demo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import zlc.season.demo.databinding.FragmentPredictiveBackBinding


class PredictiveBackFragment : Fragment() {

    private var binding: FragmentPredictiveBackBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentPredictiveBackBinding.inflate(inflater, container, false)
            .also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            btnOpen.setOnClickListener {
                val intent = Intent()
                intent.action = "android.intent.action.VIEW"
                val contentUrl = Uri.parse("http://www.baidu.com")
                intent.data = contentUrl
                startActivity(intent)
            }
        }
    }
}