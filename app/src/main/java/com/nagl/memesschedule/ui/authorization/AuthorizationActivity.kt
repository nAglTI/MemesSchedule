package com.nagl.memesschedule.ui.authorization

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.nagl.memesschedule.BaseActivity
import com.nagl.memesschedule.MainActivity
import com.nagl.memesschedule.R
import com.nagl.memesschedule.data.model.net.UserRequest
import com.nagl.memesschedule.databinding.ActivityAuthorizationBinding
import com.nagl.memesschedule.hideSoftKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthorizationActivity : BaseActivity() {

    private lateinit var binding: ActivityAuthorizationBinding
    private val authViewModel by viewModels<AuthorizationViewModel> { viewModelFactoryProvider }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModels()
        initListeners()
        authViewModel.isUserAuth()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun observeViewModels() {
        authViewModel.isLoading.observe(this) { state ->
            when (state) {
                true -> {
                    showLoading()
                }

                false -> {
                    hideLoading()
                }
            }
        }

        authViewModel.isAuthPassed.observe(this) { state ->
            when (state) {
                true -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

                false -> {
                    showShortSnackBar(
                        getString(R.string.str_home_activity_auth_error_snack),
                        binding.root
                    )
                }
            }
        }

        authViewModel.isError.observe(this) { state ->
            if (state) {
                showShortSnackBar(
                    getString(R.string.str_home_activity_server_error_snack),
                    binding.root
                )
            }
        }

        authViewModel.isAuth.observe(this) {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun initListeners() {
        binding.signAuthorizationButton.setOnClickListener {
            hideSoftKeyboard()
            if (isNetworkAvailable()) // UPD: авторизацию вроде починили
                authViewModel.getUserInfo(
                    UserRequest(
                        binding.loginAuthorizationEditText.text.toString(),
                        binding.passAuthorizationEditText.text.toString()
                    )
                )
            else showShortSnackBar(getString(R.string.str_home_activity_error_snack), binding.root)
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }

    private fun showLoading() {
        binding.apply {
            loginLinearLayout.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun hideLoading() {
        binding.apply {
            loginLinearLayout.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }
}