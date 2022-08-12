package local.yaudio.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import local.yaudio.utils.RunnablePeriodic
import kotlin.math.log

class SearchViewModel : ViewModel() {

    private val _text = MutableLiveData("Search")
    val text: LiveData<String> = _text

}