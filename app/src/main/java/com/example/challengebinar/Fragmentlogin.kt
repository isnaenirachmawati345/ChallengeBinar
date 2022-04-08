package com.example.challengebinar

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.challengebinar.databinding.FragmentLoginBinding
import com.example.challengebinar.room.DatabaseStorange
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class Fragmentlogin : Fragment() {

    private var myDB : DatabaseStorange? = null
    private var _binding:FragmentLoginBinding?=null
    private val binding get() = _binding!!

    companion object {
        const val USERLOGIN = "login_user"
        const val EMAIL = "email"
        const val PASSWORD ="password"

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDB = DatabaseStorange.getInstance(requireContext())

        val preferences=this.activity?.getSharedPreferences(USERLOGIN, Context.MODE_PRIVATE)
        if (preferences!!. getString(EMAIL, null)!=null){
            findNavController().navigate(R.id.action_fragmentlogin_to_fragmen_Home_Main)
            val email = preferences.getString(EMAIL,null)
            Toast.makeText(context, "Welcome CUSTOMER $email", Toast.LENGTH_SHORT).show()
        }
        binding.tvSignUp.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentlogin_to_fragment_form_registrasi)
        }
        val user = arguments?.getString(fragment_form_registrasi.EMAIL)
        if (user.isNullOrEmpty()){
            binding.etEmail.hint=""
        }else{
            binding.etEmail.setText(user)
        }
           binding.btnLogin.setOnClickListener {
               when {
                   binding.etEmail.text.isNullOrEmpty() ->{
                       binding.wrapLoginEmail.error = "Silahkan Masukan Email Kembali"
                   }
                   binding.passwordLogin.text.isNullOrEmpty()->{
                       binding.wrapLoginPassword.error ="Silahkan Masukan Password Kembali"
                   }
                   else -> {
                       GlobalScope.async {
                           val result = myDB?.storeageDao()?.userCek(
                               binding.etEmail.text.toString(),
                               binding.passwordLogin.text.toString()
                           )
                           runBlocking(Dispatchers.Main){
                               if (result == false){
                                   closeKeyboard()
                                   val snackbar = Snackbar.make(it, "Yah gagal masuk, mungkin kamu salah memasukan email atau password kamu", Snackbar.LENGTH_INDEFINITE)
                                   snackbar.setAction("yes"){
                                       snackbar.dismiss()
                                       binding.etEmail.requestFocus()
                                       binding.etEmail.setText("")
                                       binding.passwordLogin.setText("")
                                   }
                                   snackbar.show()
                               }else{
                                   findNavController().navigate(R.id.action_fragmentlogin_to_fragmen_Home_Main)
                                   val editShpre : SharedPreferences.Editor = preferences!!.edit()
                                   editShpre.putString(EMAIL,binding.etEmail.text.toString())
                                   editShpre.putString(PASSWORD, binding.passwordLogin.text.toString())
                                   editShpre.apply()
                                   Toast.makeText(context, "Welcome Customer ${binding.etEmail.text}", Toast.LENGTH_SHORT).show()
                               }

                           }
                       }
                   }
               }
           }
    }
private fun closeKeyboard(){
    val activity = activity as MainActivity
    val view = activity.currentFocus
    if (view != null){
        val my =activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        my.hideSoftInputFromWindow(view.windowToken,0)
    }
}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}



