package com.intelbras.urna

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.intelbras.urna.components.End
import com.intelbras.urna.components.Printer
import com.intelbras.urna.components.SetupZone
import com.intelbras.urna.components.Start
import com.intelbras.urna.components.Welcome
import com.intelbras.urna.model.list
import com.intelbras.urna.presentation.UiState
import com.intelbras.urna.ui.theme.UrnaTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.net.ssl.SSLEngineResult.Status


class MainActivity : ComponentActivity() {

    private val SMS_CONSENT_REQUEST = 1010

    private val smsVerificationReciver = object : BroadcastReceiver() {

        override fun onReceive(p0: Context?, p1: Intent?) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
                val extras = intent.extras
                val smsRetrieverStatus =
                    extras?.get(SmsRetriever.EXTRA_STATUS) as com.google.android.gms.common.api.Status

                when (smsRetrieverStatus.statusCode) {
                    CommonStatusCodes.SUCCESS -> {
                        val consentIntent =
                            extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)

                        try {
                            startActivityForResult(consentIntent, SMS_CONSENT_REQUEST)
                        } catch (e: Exception) {
                        }

                    }
                    CommonStatusCodes.TIMEOUT -> {

                    }
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        lifecycleScope.launch {
            ArticlesIntermediary().articles()
                .collect {
                    Log.d("LIST", "---------------")
                    it.forEach {
                        Log.d("LIST", it.toString())
                    }
                }
        }


        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        registerReceiver(
            smsVerificationReciver,
            intentFilter,
            SmsRetriever.SEND_PERMISSION,
            null
        )
        initAutoRefill()



        setContent {

            val viewModel = viewModel<UrnaViewModel>()
            val state = viewModel.state.collectAsState()

            UrnaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    when (state.value.state) {
                        UiState.InititalState -> {
                            Welcome(
                                count = { viewModel.redirection(UiState.PrintState) },
                                conf = { viewModel.redirection(UiState.ConfigState) },
                                list = { viewModel.redirection(UiState.LIST) }
                            )
                        }
                        UiState.LIST -> {
                            com.intelbras.urna.components.List(list) {
                                viewModel.redirection(UiState.InititalState)
                            }
                        }
                        UiState.ConfigState -> {
                            SetupZone(viewModel)
                        }
                        UiState.PrintState -> {
                            Printer(viewModel)
                        }
                        UiState.VoteState -> {
                            Start(
                                viewModel = viewModel,
                                isZone = false,
                            ) {
                                viewModel.redirection(UiState.InititalState)
                            }
                        }
                        UiState.END -> {
                            Start(
                                viewModel = viewModel,
                                isZone = false,
                                isEnd = true
                            ) {
                                viewModel.redirection(UiState.InititalState)
                            }
                        }
                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SMS_CONSENT_REQUEST ->
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Log.d("LIST", "${data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)}")

                } else {
                    Log.d("LIST", "ERRO ")
                }
        }
    }


    private fun initAutoRefill() {
        SmsRetriever.getClient(this)
            .startSmsUserConsent(null)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("LIST", "listening")
                } else {
                    Log.d("LIST", "Fail")
                }
            }
    }

}















