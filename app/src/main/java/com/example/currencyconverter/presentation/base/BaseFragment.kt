package com.example.currencyconverter.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.currencyconverter.presentation.util.ErrorHandler

abstract class BaseFragment<T : ViewBinding?, V : BaseViewModel?> : Fragment() {

    private lateinit var mActivity: BaseActivity<Nothing, Nothing>

    private var mViewModel: V? = null

    protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T
    private var _binding: T? = null
    protected val binding: T get() = requireNotNull(_binding)

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun getViewModel(): V

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            mActivity = context as BaseActivity<Nothing, Nothing>
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()

        setHasOptionsMenu(false)
        mViewModel?.getErrorLiveData()?.observe(this) { throwable ->
            hideLoading()
            try {
                val errorHandler = ErrorHandler(mActivity)
                val message: String = errorHandler.handleError(throwable)
                mActivity.showMessage(message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = bindingInflater.invoke(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel?.loadingState?.observe(
            viewLifecycleOwner
        ) { state -> if (state) showLoading() else hideLoading() }
    }

    val baseActivity: BaseActivity<Nothing, Nothing>
        get() = mActivity

    private fun showLoading() {
        mActivity.showLoading()
    }

    private fun hideLoading() {
        mActivity.hideLoading()
    }
}
