package com.naufal.berita

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.naufal.berita.api.ApiClient
import com.naufal.berita.model.AddBeritaResponse

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddBeritaActivity : AppCompatActivity() {

    private lateinit var etJudul: EditText
    private lateinit var etIsi: EditText
    private lateinit var btnGambar: Button
    private lateinit var btnTambah: Button
    private lateinit var imgGambar: ImageView
    private lateinit var progressBar: ProgressBar
    private var imageFile: File? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_berita)

        btnGambar = findViewById(R.id.btnGambar)
        imgGambar = findViewById(R.id.imgGambar)
        etIsi = findViewById(R.id.etIsi)
        btnTambah = findViewById(R.id.btnTambah)
        etJudul = findViewById(R.id.etJudul)
        progressBar = findViewById(R.id.progressBar)


        btnGambar.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        btnTambah.setOnClickListener {
            imageFile?.let {
                    file ->
                tambahBerita(etJudul.text.toString(), etIsi.text.toString(), file)
            }
        }

    }

    //menampilkan gambar
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data!!
            imageFile = File(uri.path!!)
            imgGambar.visibility = View.VISIBLE
            imgGambar.setImageURI(uri)

        }
    }


    //proses tambah berita
    private fun tambahBerita(judul: String, isiBerita: String, filegambar: File) {
        progressBar.visibility = View.VISIBLE
        val requestBody = filegambar.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val partFileGambar =
            MultipartBody.Part.createFormData("filegambar", filegambar.name, requestBody)
        val title = judul.toRequestBody("text/plain".toMediaTypeOrNull())
        val deskripsiBerita = isiBerita.toRequestBody("text/plain".toMediaTypeOrNull())

        ApiClient.apiService.addBerita(title, deskripsiBerita, partFileGambar)
            .enqueue(object : Callback<AddBeritaResponse> {
                override fun onResponse(
                    call: Call<AddBeritaResponse>,
                    response: Response<AddBeritaResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.succes) {
                            startActivity(
                                Intent(
                                    this@AddBeritaActivity,
                                    DashboardActivity::class.java
                                )
                            )

                        } else {
                            Toast.makeText(
                                this@AddBeritaActivity,
                                response.body()!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    } else {
                        Toast.makeText(
                            this@AddBeritaActivity,
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<AddBeritaResponse>, t: Throwable) {
                    Toast.makeText(
                        this@AddBeritaActivity,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar.visibility = View.GONE
                }
            })

    }
}