package com.example.challengebinar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.challengebinar.databinding.FragmentFormRegistrasiBinding
import com.example.challengebinar.room.DatabaseStorange
import com.example.challengebinar.room.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking


class fragment_form_registrasi : Fragment() {
      private  var mYdB : DatabaseStorange?= null
     private var _binding : FragmentFormRegistrasiBinding? = null
    private val binding get() =  _binding!!

    companion object {
        const val EMAIL = "email"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormRegistrasiBinding.inflate(layoutInflater, container, false )
        // Inflate the layout for this fragment
        return binding.root
}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mYdB = DatabaseStorange.getInstance(requireContext())

        binding.btnSignUp.setOnClickListener {
            when {
                binding.etEmailRegistrasi.text.isNullOrEmpty() -> {
                    binding.wrapRegistrasiEmail.error ="Kamu belum Isi Form Email"
                }
                binding.etPasswordRegistrasi.text.isNullOrEmpty() ->{
                    binding.wrapRegistrasiPassword.error = "Kamu belum isi Password"
                }
                binding.etKonfirmasiPassword.text.isNullOrEmpty() -> {
                    binding.wrapRegistrasiKonfrimPassword.error = "Kamu belum isi password konfirmasi"
                }
                binding.etPasswordRegistrasi.text.toString() != binding.etKonfirmasiPassword.text.toString() ->{
                    binding.wrapRegistrasiKonfrimPassword.error ="Kamu memasukan password tidak sama"
                    binding.etKonfirmasiPassword.setText("")
                }else -> {
                    val objectUser = User (
                        null,
                        binding.etEmailRegistrasi.text.toString(),
                        binding.etPasswordRegistrasi.text.toString(),
                    )
                    GlobalScope.async {
                        val result = mYdB?.storeageDao()?.addUser(objectUser)
                        runBlocking (Dispatchers.Main){
                            if (result != 0.toLong()){
                                Toast.makeText(activity, "Kamu berhasil mendaftar", Toast.LENGTH_SHORT).show()
                                val email = binding.etEmailRegistrasi.text.toString()
                                val intent = Intent().apply {
                                    putExtra(EMAIL, email)
                                }
                                findNavController().navigate(R.id.action_fragment_form_registrasi_to_fragmentlogin)
                            }else{
                                Toast.makeText(activity, "Kamu gagal mendaftar", Toast.LENGTH_SHORT).show()
                            }
                            onStop()
                        }
                    }
                }
            }

            }
        }
    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }
    }