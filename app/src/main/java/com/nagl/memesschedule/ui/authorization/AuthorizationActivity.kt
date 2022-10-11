package com.nagl.memesschedule.ui.authorization

import android.content.Intent
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
    private val authViewModel by viewModels<AuthorizationViewModel> {viewModelFactoryProvider}

    // TODO: if isAuth true, do not show input layout or show progressbar instantly while process isAuth checking
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
                    showShortSnackBar(getString(R.string.str_home_activity_auth_error_snack), binding.root)
                }
            }
        }

        authViewModel.isError.observe(this) { state ->
            if (state) {
                showShortSnackBar(getString(R.string.str_home_activity_error_snack), binding.root)
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
            checkUserData(
                binding.loginAuthorizationEditText.text.toString(),
                binding.passAuthorizationEditText.text.toString()
            )
        }
    }

    private fun checkUserData(login: String, pass: String) {
        authViewModel.getUserInfo(UserRequest(login, pass))
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