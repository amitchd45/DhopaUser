package com.dhopa.dhopaservice.loginRegister.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.beaute.user.SharePrefrence.App
import com.beaute.user.SharePrefrence.AppConstants
import com.dhopa.dhopaservice.R
import com.dhopa.dhopaservice.databinding.FragmentLoginBinding
import com.dhopa.dhopaservice.home.activities.HomeActivity
import com.dhopa.dhopaservice.mvvm.CommonViewModel
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.omninos.util_data.CommonUtils
import com.tapadoo.alerter.Alerter
import org.json.JSONObject
import java.net.URL
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.HashMap

class LoginFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var commonViewModel: CommonViewModel

    private lateinit var view1: View
    private lateinit var strEmail: String
    private lateinit var strPassword: String

    private lateinit var auth: FirebaseAuth
    private var fbId = ""
    private var fbFirstName: kotlin.String? = ""
    private var fbLastName: kotlin.String? = ""
    private var fbEmail: kotlin.String? = ""
    private var fbSocialUserName: kotlin.String? = ""
    private var fbPhoneNumber: kotlin.String? = ""
    private var fbGender: kotlin.String? = ""
    private var fbDateOfBirth: kotlin.String? = ""
    private var fbCountry: kotlin.String? = ""
    private var fbProfilePicture: kotlin.String? = ""
    private val RC_SIGN_IN = 8
    private var userName = ""
    private var userStringEmail: kotlin.String? = ""
    private var socialId: kotlin.String? = ""
    private var loginType: String = ""
    private var userImage: kotlin.String? = ""
    private var phoneNumber: kotlin.String? = ""
    private var gender: kotlin.String? = ""
    private var dateofbirth: kotlin.String? = ""
    private var country: kotlin.String? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var fbProfilePicturenew: URL? = null
    private var reg_id: String = ""
    lateinit var dailogbox: AlertDialog

    //    var googleLogin: GoogleLogin? = null
    private var googleApiClient: GoogleApiClient? = null


    companion object {
        var callbackManager: CallbackManager? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        commonViewModel =
            ViewModelProviders.of(this@LoginFragment).get(CommonViewModel::class.java)
        view1 = binding.root

        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }
        callbackManager = CallbackManager.Factory.create()

        reg_id = FirebaseInstanceId.getInstance().token.toString()

        init()

        return view1
    }

    private fun init() {
        binding.noAcRegister.setOnClickListener {
            view1.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnLogin.setOnClickListener {
            validation()
        }

        binding.googleBtn.setOnClickListener {
            SignIn()
        }

        binding.facebookBtn.setOnClickListener {
            FBLogin()
        }

        binding.tvForgotPass.setOnClickListener {
            view1.findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        binding.tvSkip.setOnClickListener {
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun validation() {
        strEmail = binding.etEmail.text.toString().trim()
        strPassword = binding.etPassword.text.toString().trim()

        if (strEmail.isEmpty()) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please enter email")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        } else if (!emailValidator(strEmail)) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please enter valid email")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        } else if (strPassword.isEmpty()) {
            Alerter.create(activity)
                .setTitle("Dhopa")
                .setIcon(R.drawable.ic_warning)
                .setText("Please enter Password...")
                .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                .show()
        } else {
            login()
        }
    }

    override fun onClick(v: View?) {

    }

    private fun login() {
        CommonUtils.showProgress(requireActivity())
        val data: HashMap<String, String> = HashMap()
        data["email"] = strEmail
        data["password"] = strPassword
        data["latitude"] = "30.647478"
        data["longitude"] = "70.798589"
        data["reg_id"] = "123"
        data["device_type"] = "Android"

        commonViewModel.login(requireActivity(), data).observe(requireActivity(), Observer {
            if (it.success == "1") {
                CommonUtils.dismissProgress()
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                App.appPreference1.saveUserDetails(AppConstants.USER_LOGIN_DETAILS, it.details)
                App.appPreference1.login(requireActivity(), true)

                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()

            } else {
                CommonUtils.dismissProgress()
                Alerter.create(activity)
                    .setTitle("Dhopa")
                    .setIcon(R.drawable.ic_warning)
                    .setText(it.message)
                    .setBackgroundColorRes(R.color.colorAccent) // or setBackgroundColorInt(Color.CYAN)
                    .show()
            }
        })
    }

    fun emailValidator(email: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(EMAIL_PATTERN)
        matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun SignIn() {
        val signInIntent: Intent = mGoogleSignInClient!!.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        CommonUtils.showProgress(requireActivity())
        if (result.isSuccess) {
            CommonUtils.dismissProgress()
            // Signed in successfully, show authenticated UI.
            val acct = result.signInAccount
            Log.d("Account: ", acct!!.displayName.toString())
            Log.d("Account: ", acct.id.toString())
            Log.d("Account: ", acct.email.toString())
            socialId = acct.id
            userName = acct.displayName!!
            userStringEmail = acct.email
            loginType = "Google"
            userImage = if (acct.photoUrl != null) {
                acct.photoUrl.toString()
            } else {
                ""
            }
            socialRegisterApi()
//            Toast.makeText(activity, "working...", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
            CommonUtils.dismissProgress()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            val task = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(task)
        } else {
            callbackManager?.onActivityResult(requestCode, resultCode, data)
        }
    }


    private fun FBLogin() {
        if (CommonUtils.isNetworkConnected(requireContext())) {
            CommonUtils.showProgress(requireActivity())
            LoginManager.getInstance().logInWithReadPermissions(
                this@LoginFragment,
                Arrays.asList("public_profile")
            )
            LoginManager.getInstance().registerCallback(callbackManager, object :
                FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    CommonUtils.dismissProgress()
                    Log.d("onSuccess: ", loginResult.accessToken.token)
                    getFacebookData(loginResult)
                    //                    Toast.makeText(activity, ""+userStringEmail, Toast.LENGTH_SHORT).show();
                }

                override fun onCancel() {
                    CommonUtils.dismissProgress()
                    Toast.makeText(activity, "Cancel", Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: FacebookException) {
                    CommonUtils.dismissProgress()
                    if (error is FacebookAuthorizationException) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut()
                        }
                    }
                    FBLogin()
                }
            })
        } else { // CommonUtils.dismissProgress();
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFacebookData(loginResult: LoginResult) {
        CommonUtils.showProgress(requireActivity())
        val graphRequest = GraphRequest.newMeRequest(
            loginResult.accessToken
        ) { `object`, response ->
            CommonUtils.dismissProgress()
            try {
                if (`object`.has("id")) {
                    fbId = `object`.getString("id")
                    Log.e("LoginActivity", "id$fbId")
                }
                //check permission first userName
                if (`object`.has("first_name")) {
                    fbFirstName = `object`.getString("first_name")
                    Log.e("LoginActivity", "first_name$fbFirstName")
                }
                //check permisson last userName
                if (`object`.has("last_name")) {
                    fbLastName = `object`.getString("last_name")
                    Log.e("LoginActivity", "last_name$fbLastName")
                }
                //check permisson email
                if (`object`.has("email")) {
                    fbEmail = `object`.getString("email")
                    Log.e("LoginActivity", "email$fbEmail")
                }
                if (`object`.has("phoneNumber")) {
                    fbPhoneNumber = `object`.getString("phoneNumber")
                    Log.e("LoginActivity", "email$fbPhoneNumber")
                }
                if (`object`.has("gender")) {
                    fbGender = `object`.getString("gender")
                    Log.e("LoginActivity", "email$fbGender")
                }
                if (`object`.has("dateofbirth")) {
                    fbDateOfBirth = `object`.getString("dateofbirth")
                    Log.e("LoginActivity", "email$fbDateOfBirth")
                }
                if (`object`.has("country")) {
                    fbCountry = `object`.getString("country")
                    Log.e("LoginActivity", "email$fbCountry")
                }
                val jsonObject = JSONObject(`object`.getString("picture"))
                if (jsonObject != null) {
                    val dataObject = jsonObject.getJSONObject("data")
                    Log.e(
                        "LoginActivity",
                        "json oject get picture$dataObject"
                    )
                    fbProfilePicturenew =
                        URL("https://graph.facebook.com/$fbId/picture?width=500&height=500")
                    Log.e(
                        "LoginActivity",
                        "json object=>$`object`"
                    )
                }
                fbSocialUserName = "$fbFirstName $fbLastName"
                userName = fbSocialUserName!!
                userStringEmail = fbEmail
                socialId = fbId
                phoneNumber = fbPhoneNumber
                gender = fbGender
                dateofbirth = fbDateOfBirth
                country = fbCountry
                loginType = "facebook"
                userImage = if (fbProfilePicture != null) {
                    fbProfilePicturenew.toString()
                } else {
                    ""
                }

//                CommonUtils.ShowMsg(requireActivity(), "working... fb")
                socialRegisterApi()
            } catch (e: Exception) {
                CommonUtils.dismissProgress()
            }
        }
        val bundle = Bundle()
        Log.e("LoginActivity", "bundle set")
        bundle.putString("fields", "id, first_name, last_name,email,picture,gender,location")
        graphRequest.parameters = bundle
        graphRequest.executeAsync()
    }

    private fun socialRegisterApi() {
        var data: HashMap<String, String> = HashMap()
        data["name"] = userName
        data["phone"] = ""
        data["email"] = userStringEmail!!
        data["social_id"] = socialId!!
        data["reg_id"] = reg_id
        data["device_type"] = "Android"
        data["image"] = userImage!!
        data["latitude"] = "0.0"
        data["longitude"] = "0.0"
        data["login_type"] = loginType

        commonViewModel.socialRegisterDetails(requireActivity(), data)
            .observe(requireActivity(), Observer {
                if (it.success.equals("1")) {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    App.appPreference1.saveUserDetails(AppConstants.USER_LOGIN_DETAILS, it.details)
                    App.appPreference1.login(requireActivity(), true)

                    val intent = Intent(activity, HomeActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                }
            })
    }
}