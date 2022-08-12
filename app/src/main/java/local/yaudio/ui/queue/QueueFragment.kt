package local.yaudio.ui.queue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import local.yaudio.databinding.FragmentQueueBinding

class QueueFragment : Fragment() {

    private var _binding: FragmentQueueBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val queueViewModel =
            ViewModelProvider(this).get(QueueViewModel::class.java)

        _binding = FragmentQueueBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textQueue
        queueViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}