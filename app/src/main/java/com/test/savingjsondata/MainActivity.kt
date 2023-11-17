package com.test.savingjsondata

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.test.savingjsondata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        activityMainBinding.apply {
            mainLoginBtn.setOnClickListener {
                loginUser()
            }
        }
    }

    private fun loginUser() {
        activityMainBinding.mainPb.visibility = VISIBLE
        requestTaps.launch(googleSignInClient.signInIntent)
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            navigateUserToHomePage()
        }
    }

    private fun navigateUserToHomePage() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private val requestTaps = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        activityMainBinding.mainPb.visibility = GONE
        if (it.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)

            try {
                val account = task.result
                firebaseAuthWithGoogle(account)
            } catch (e: Exception) {
                Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, getString(R.string.login_text), Toast.LENGTH_SHORT).show()
                    navigateUserToHomePage()
                } else {
                    Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}