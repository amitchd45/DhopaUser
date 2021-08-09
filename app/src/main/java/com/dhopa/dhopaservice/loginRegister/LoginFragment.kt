//package com.dlivr.dliveruser.Fragments
//
//import android.app.AlertDialog
//import android.app.ProgressDialog
//import android.content.Intent
//import android.content.IntentSender
//import android.os.Bundle
//import android.text.method.HideReturnsTransformationMethod
//import android.text.method.PasswordTransformationMethod
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import androidx.navigation.Navigation
//import com.dlivr.dliveruser.GoogleLogin
//import com.dlivr.dliveruser.HomeActivity
//import com.dlivr.dliveruser.Mvvm.LoginRegisterMvvm
//import com.dlivr.dliveruser.R
//import com.dlivr.dliveruser.Utils.App
//import com.dlivr.dliveruser.Utils.AppConstants
//import com.dlivr.dliveruser.Utils.CommonUtils
//import com.facebook.*
//import com.facebook.appevents.AppEventsLogger
//import com.facebook.login.LoginManager
//import com.facebook.login.LoginResult
//import com.google.android.gms.auth.api.Auth
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.auth.api.signin.GoogleSignInResult
//import com.google.android.gms.common.ConnectionResult
//import com.google.android.gms.common.api.GoogleApiClient
//import com.google.android.gms.common.api.PendingResult
//import com.google.android.gms.common.api.Status
//import com.google.android.gms.location.*
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.iid.FirebaseInstanceId
//import kotlinx.android.synthetic.main.fragment_login.*
//import org.json.JSONObject
//import java.net.URL
//import java.util.*
//import java.util.regex.Matcher
//import java.util.regex.Pattern
//import kotlin.collections.HashMap
//
//
//class LoginFragment : Fragment(), View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
//    GoogleApiClient.OnConnectionFailedListener {
//
//    private lateinit var view1: View
//    private lateinit var txt_sign_up: TextView
//    private lateinit var btn_login: Button
//    private lateinit var txt_pin: TextView
//    private lateinit var txt_forget_password: TextView
//    private lateinit var email: String
//    private lateinit var password: String
//    private lateinit var et_password: EditText
//    private lateinit var loginEyeIV: ImageView
//    lateinit var loginRegisterVM: LoginRegisterMvvm
//    private lateinit var progressDialog: ProgressDialog
//    private lateinit var btnGoogle: ImageView
//    private lateinit var img_facebook: ImageView
//    var show: Int = 0
//    private lateinit var auth: FirebaseAuth
//    private var fbId = ""
//    private var fbFirstName: kotlin.String? = ""
//    private var fbLastName: kotlin.String? = ""
//    private var fbEmail: kotlin.String? = ""
//    private var fbSocialUserName: kotlin.String? = ""
//    private var fbPhoneNumber: kotlin.String? = ""
//    private var fbGender: kotlin.String? = ""
//    private var fbDateOfBirth: kotlin.String? = ""
//    private var fbCountry: kotlin.String? = ""
//    private var fbProfilePicture: kotlin.String? = ""
//    private val RC_SIGN_IN = 8
//
//    private var userName = ""
//    private var userStringEmail: kotlin.String? = ""
//    private var socialId: kotlin.String? = ""
//    private var loginType: String = ""
//    private var userImage: kotlin.String? = ""
//    private var phoneNumber: kotlin.String? = ""
//    private var gender: kotlin.String? = ""
//    private var dateofbirth: kotlin.String? = ""
//    private var country: kotlin.String? = null
//    private var mGoogleSignInClient: GoogleSignInClient? = null
//    private var fbProfilePicturenew: URL? = null
//    private var reg_id: String = ""
//    lateinit var dailogbox: AlertDialog
//    var googleLogin: GoogleLogin? = null
//    private var googleApiClient: GoogleApiClient? = null
//
//
//    companion object {
//        var callbackManager: CallbackManager? = null
////        var callbackManager: CallbackManager? = null
//    }
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        view1 = inflater.inflate(R.layout.fragment_login, container, false)
//        turnGPSOn()
//        loginRegisterVM =
//            ViewModelProviders.of(this@LoginFragment).get(LoginRegisterMvvm::class.java)
//
//        val gso: GoogleSignInOptions =
//            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
//        // Build a GoogleSignInClient with the options specified by gso.
//        // Build a GoogleSignInClient with the options specified by gso.
//        mGoogleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }
//
////        FacebookSdk.sdkInitialize(requireActivity().application)
////        AppEventsLogger.activateApp(requireActivity().application)
//        callbackManager = CallbackManager.Factory.create()
//
//        reg_id = FirebaseInstanceId.getInstance().token.toString()
//
//        findIds()
//        setUp()
//
//        progressDialog = ProgressDialog(activity)
//        progressDialog.setMessage("Please wait...")
//        progressDialog.setCanceledOnTouchOutside(false)
//
//        return view1
//    }
//
//    private fun turnGPSOn() {
//        if (googleApiClient == null) {
//            googleApiClient = GoogleApiClient.Builder(requireContext())
//                .addApi(LocationServices.API).addConnectionCallbacks(this)
//                .build()
//            googleApiClient?.connect()
//            val locationRequest: LocationRequest = LocationRequest.create()
//            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//            locationRequest.interval = 30 * 1000
//            locationRequest.fastestInterval = 5 * 1000
//            val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
//                .addLocationRequest(locationRequest)
//
//// **************************
//            builder.setAlwaysShow(true) // this is the key ingredient
//// **************************
//            val result: PendingResult<LocationSettingsResult> = LocationServices.SettingsApi
//                .checkLocationSettings(googleApiClient, builder.build())
//            result.setResultCallback { p0 ->
//                val status: Status = p0.status
//                val state: LocationSettingsStates = p0
//                    .locationSettingsStates
//                when (status.statusCode) {
//                    LocationSettingsStatusCodes.SUCCESS -> {
//// permissions()
//// Toast.makeText(activity,"lat.toString()+long.toString()",Toast.LENGTH_SHORT).show()
//                    }// All location settings are satisfied. The client can
//// initialize location
//// requests here.
//
//// permissions()
//                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> // Location settings are not satisfied. But could be
//// fixed by showing the user
//// a dialog.
//                        try {
//// Show the dialog by calling
//// startResolutionForResult(),
//// and check the result in onActivityResult().
//                            status.startResolutionForResult(requireActivity(), 1000)
//                        } catch (e: IntentSender.SendIntentException) {
//// Ignore the error.
//                        }
//                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> // Location settings are not satisfied. However, we have
//// no way to fix the
//// settings so we won't show the dialog.
//                        Toast.makeText(requireContext(), "off", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//
//    private fun setUp() {
//        btn_login.setOnClickListener(this)
//        txt_sign_up.setOnClickListener(this)
//        txt_pin.setOnClickListener(this)
//        txt_forget_password.setOnClickListener(this)
//        loginEyeIV.setOnClickListener(this)
//        btnGoogle.setOnClickListener(this)
//        img_facebook.setOnClickListener(this)
//    }
//
//    private fun findIds() {
//        txt_sign_up = view1.findViewById(R.id.txt_sign_up)
//        btn_login = view1.findViewById(R.id.btn_login)
//        txt_pin = view1.findViewById(R.id.txt_pin)
//        txt_forget_password = view1.findViewById(R.id.txt_forget_password)
//        loginEyeIV = view1.findViewById(R.id.loginEyeIV)
//        et_password = view1.findViewById(R.id.et_password)
//        img_facebook = view1.findViewById(R.id.img_facebook)
//        btnGoogle = view1.findViewById(R.id.btnGoogle)
//    }
//
//    override fun onClick(p0: View?) {
//        when (p0?.id) {
//            R.id.txt_sign_up -> {
//                Navigation.findNavController(view1)
//                    .navigate(R.id.action_loginFragment_to_registerFragment)
//            }
//
//            R.id.btn_login -> {
////                Navigation.findNavController(view1)
////                    .navigate(R.id.action_loginFragment_to_homeActivity)
//                validate()
////                Navigation.findNavController(view1).navigate(R.id.action_loginFragment_to_homeActivity)
//            }
//            R.id.img_facebook -> {
//                FBLogin()
//            }
//            R.id.btnGoogle -> {
//                SignIn()
//            }
//
//            R.id.txt_pin -> {
//                Navigation.findNavController(view1)
//                    .navigate(R.id.action_loginFragment_to_quickLoginFragment)
//            }
//            R.id.txt_forget_password -> {
//
//                Navigation.findNavController(view1)
//                    .navigate(R.id.action_loginFragment_to_resetPasswordFragment)
//            }
//
//            R.id.loginEyeIV -> {
//                if (show == 0) {
//                    show = 1
//                    // show password
//                    et_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
//                    loginEyeIV.setColorFilter(resources.getColor(R.color.grey))
//                } else {
//                    show = 0
//                    // hide password
//                    et_password.transformationMethod = PasswordTransformationMethod.getInstance()
//                    loginEyeIV.setColorFilter(resources.getColor(R.color.app_color))
//                }
//            }
//
//        }
//    }
//
//    private fun validate() {
//        email = et_email.text.toString().trim()
//        password = et_password.text.toString().trim()
//
//        when {
//            email.isEmpty() || !emailValidator(email) -> {
//                if (email.isEmpty()) {
//                    CommonUtils.showAlertAndVibrate(requireActivity(), "Please enter your email")
//                } else {
//                    CommonUtils.showAlertAndVibrate(requireActivity(), "Please enter valid email")
//                }
//            }
//            password.isEmpty() -> {
//                CommonUtils.showAlertAndVibrate(requireActivity(), "Please enter your password")
//            }
//            else -> {
//                loginApi()
//
//            }
//        }
//    }
//
//    fun emailValidator(email: String?): Boolean {
//        val pattern: Pattern
//        val matcher: Matcher
//        val EMAIL_PATTERN =
//            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
//        pattern = Pattern.compile(EMAIL_PATTERN)
//        matcher = pattern.matcher(email)
//        return matcher.matches()
//    }
//
//    private fun loginApi() {
//
//        progressDialog.show()
//
//        val data: HashMap<String, String> = HashMap()
//        data["email"] = email
//        data["password"] = password
//        data["reg_id"] = "regid"
//        data["device_type"] = "ANDROID"
//        data["latitude"] = "0.0"
//        data["longitude"] = "0.0"
//
//        loginRegisterVM.userLogin(requireActivity(), data).observe(requireActivity(), Observer {
//
//            if (it.success) {
//                progressDialog.dismiss()
//                App.getAppPreference()?.SaveString(AppConstants.TOKEN, "1")
//                App.getAppPreference()?.SaveString(AppConstants.USER_ID, it.results?.id)
////                App.getAppPreference()?.saveUserDetails(AppConstants.LOGIN_DETAIL, it.details)
////                App.getAppPreference()?.SaveString(AppConstants.USERNAME, it.details.username)
////                App.getAppPreference()?.SaveString(AppConstants.GOLDID, it.details.goldId)
//
//                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
//                Navigation.findNavController(view1)
//                    .navigate(R.id.action_loginFragment_to_homeActivity)
//
//                requireActivity().finish()
//
////                    startActivity(Intent(requireActivity(), HomeActivity::class.java))
////                    requireActivity().finishAffinity()
//
//
//            } else {
//                progressDialog.dismiss()
//                if (it.code == 400) {
//                    CommonUtils.showAlertAndVibrate(requireActivity(), it.message)
//                }
//                if (it.code == 401) {
//                    CommonUtils.showAlertAndVibrate(requireActivity(), it.message)
//                }
//                if (it.code == 404) {
//                    CommonUtils.showAlertAndVibrate(requireActivity(), it.message)
//                }
//                if (it.code == 409) {
//                    CommonUtils.showAlertAndVibrate(
//                        requireActivity(),
//                        "Phone Number or Email Already Exist"
//                    )
//                } else {
//                    CommonUtils.showAlertAndVibrate(requireActivity(), it.message)
//                }
//
//            }
//        })
//    }
//
//    private fun googleSignOut() {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()
//        val mGoogleSignInClient: GoogleSignInClient
//        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)
//        val account = GoogleSignIn.getLastSignedInAccount(activity)
//        if (account == null) {
//        } else {
//            mGoogleSignInClient.signOut()
//        }
//    }
//
//    private fun socialRegisterApi() {
//        var data: HashMap<String, String> = HashMap()
//        data["username"] = userName
//        data["phone"] = ""
//        data["email"] = userStringEmail!!
//        data["social_id"] = socialId!!
//        data["reg_id"] = reg_id
//        data["device_type"] = "Android"
//        data["image"] = userImage!!
//        data["latitude"] = "0.0"
//        data["longitude"] = "0.0"
//        data["login_type"] = loginType
//
//        loginRegisterVM.socialRegisterDetails(requireActivity(), data)
//            .observe(requireActivity(), Observer {
//                if (it.success.equals("1")) {
//                    Toast.makeText(activity, it.message.toString(), Toast.LENGTH_SHORT).show()
//                    App.getAppPreference()?.SaveString(AppConstants.TOKEN, "1")
//                    App.getAppPreference()?.SaveString(AppConstants.USER_ID, it.results?.id)
//
////                    App.getAppPreference()?.saveUserDetails(AppConstants.LOGIN_DETAIL, it.details)
////                    App.getAppPreference()?.SaveString(AppConstants.USERNAME, it.details.username)
////                    App.getAppPreference()?.SaveString(AppConstants.GOLDID, it.details.goldId)
////                    App.getAppPreference()?.SaveString(AppConstants.USER_IMAGE, it.details.image)
////                    App.getAppPreference()?.SaveString(AppConstants.ID, it.details.id)
////                    App.getAppPreference()?.SaveString(AppConstants.PINCODE, it.details.pinCode)
////                    App.getAppPreference()?.SaveString(AppConstants.PHONE, it.details.phone)
//
//                    startActivity(Intent(requireActivity(), HomeActivity::class.java))
//                    requireActivity().finishAffinity()
//                } else {
//                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
//
//                }
//            })
//    }
//
//    private fun SignIn() {
//        val signInIntent: Intent = mGoogleSignInClient!!.getSignInIntent()
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//    private fun handleSignInResult(result: GoogleSignInResult) {
//        CommonUtils.showProgress(activity, "")
//        if (result.isSuccess) {
//            CommonUtils.dismissProgress()
//            // Signed in successfully, show authenticated UI.
//            val acct = result.signInAccount
//            Log.d("Account: ", acct!!.displayName.toString())
//            Log.d("Account: ", acct.id.toString())
//            Log.d("Account: ", acct.email.toString())
//            socialId = acct.id
//            userName = acct.displayName!!
//            userStringEmail = acct.email
//            loginType = "Google"
//            userImage = if (acct.photoUrl != null) {
//                acct.photoUrl.toString()
//            } else {
//                ""
//            }
//            socialRegisterApi()
//        } else {
//            Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
//            CommonUtils.dismissProgress()
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        //        callbackManager.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN) {
//            val task = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
//            handleSignInResult(task)
//        } else {
//            callbackManager?.onActivityResult(requestCode, resultCode, data)
//        }
//    }
//
//    private fun FBLogin() {
//        if (CommonUtils.isNetworkConnected(requireContext())) {
//            CommonUtils.showProgress(activity, "")
//            LoginManager.getInstance().logInWithReadPermissions(
//                activity,
//                Arrays.asList("public_profile")
//            )
//            LoginManager.getInstance().registerCallback(callbackManager, object :
//                FacebookCallback<LoginResult> {
//                override fun onSuccess(loginResult: LoginResult) {
//                    CommonUtils.dismissProgress()
//                    Log.d("onSuccess: ", loginResult.accessToken.token)
//                    getFacebookData(loginResult)
//                    //                    Toast.makeText(activity, ""+userStringEmail, Toast.LENGTH_SHORT).show();
//                }
//
//                override fun onCancel() {
//                    CommonUtils.dismissProgress()
//                    Toast.makeText(activity, "Cancel", Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onError(error: FacebookException) {
//                    CommonUtils.dismissProgress()
//                    if (error is FacebookAuthorizationException) {
//                        if (AccessToken.getCurrentAccessToken() != null) {
//                            LoginManager.getInstance().logOut()
//                        }
//                    }
//                    FBLogin()
//                }
//            })
//        } else { // CommonUtils.dismissProgress();
//            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun getFacebookData(loginResult: LoginResult) {
//        CommonUtils.showProgress(activity, "")
//        val graphRequest = GraphRequest.newMeRequest(
//            loginResult.accessToken
//        ) { `object`, response ->
//            CommonUtils.dismissProgress()
//            try {
//                if (`object`.has("id")) {
//                    fbId = `object`.getString("id")
//                    Log.e("LoginActivity", "id$fbId")
//                }
//                //check permission first userName
//                if (`object`.has("first_name")) {
//                    fbFirstName = `object`.getString("first_name")
//                    Log.e("LoginActivity", "first_name$fbFirstName")
//                }
//                //check permisson last userName
//                if (`object`.has("last_name")) {
//                    fbLastName = `object`.getString("last_name")
//                    Log.e("LoginActivity", "last_name$fbLastName")
//                }
//                //check permisson email
//                if (`object`.has("email")) {
//                    fbEmail = `object`.getString("email")
//                    Log.e("LoginActivity", "email$fbEmail")
//                }
//                if (`object`.has("phoneNumber")) {
//                    fbPhoneNumber = `object`.getString("phoneNumber")
//                    Log.e("LoginActivity", "email$fbPhoneNumber")
//                }
//                if (`object`.has("gender")) {
//                    fbGender = `object`.getString("gender")
//                    Log.e("LoginActivity", "email$fbGender")
//                }
//                if (`object`.has("dateofbirth")) {
//                    fbDateOfBirth = `object`.getString("dateofbirth")
//                    Log.e("LoginActivity", "email$fbDateOfBirth")
//                }
//                if (`object`.has("country")) {
//                    fbCountry = `object`.getString("country")
//                    Log.e("LoginActivity", "email$fbCountry")
//                }
//                val jsonObject = JSONObject(`object`.getString("picture"))
//                if (jsonObject != null) {
//                    val dataObject = jsonObject.getJSONObject("data")
//                    Log.e(
//                        "LoginActivity",
//                        "json oject get picture$dataObject"
//                    )
//                    fbProfilePicturenew =
//                        URL("https://graph.facebook.com/$fbId/picture?width=500&height=500")
//                    Log.e(
//                        "LoginActivity",
//                        "json object=>$`object`"
//                    )
//                }
//                fbSocialUserName = "$fbFirstName $fbLastName"
//                userName = fbSocialUserName!!
//                userStringEmail = fbEmail
//                socialId = fbId
//                phoneNumber = fbPhoneNumber
//                gender = fbGender
//                dateofbirth = fbDateOfBirth
//                country = fbCountry
//                loginType = "facebook"
//                userImage = if (fbProfilePicture != null) {
//                    fbProfilePicturenew.toString()
//                } else {
//                    ""
//                }
//                socialRegisterApi()
//            } catch (e: Exception) {
//                CommonUtils.dismissProgress()
//            }
//        }
//        val bundle = Bundle()
//        Log.e("LoginActivity", "bundle set")
//        bundle.putString("fields", "id, first_name, last_name,email,picture,gender,location")
//        graphRequest.parameters = bundle
//        graphRequest.executeAsync()
//    }
//
//    override fun onConnected(p0: Bundle?) {
//
//    }
//
//    override fun onConnectionSuspended(p0: Int) {
//    }
//
//    override fun onConnectionFailed(p0: ConnectionResult) {
//    }
//
//
//}