package com.naufal.berita

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class DetailBeritaActivity : AppCompatActivity() {
    private lateinit var imgBerita : ImageView
    private lateinit var tvJudul : TextView
    private lateinit var tvTglBerita : TextView
    private lateinit var tvRating : TextView
    private lateinit var ratingBar : RatingBar
    private lateinit var tvIsiBerita : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_berita)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgBerita = findViewById(R.id.imgBerita)
        tvJudul = findViewById(R.id.tvJudul)
        tvTglBerita = findViewById(R.id.tvTglBerita)
        tvRating = findViewById(R.id.tvRating)
        ratingBar = findViewById(R.id.ratingBar)
        tvIsiBerita = findViewById(R.id.tvIsiBerita)

        Picasso.get().load(intent.getStringExtra("gambar")).into(imgBerita)
        tvJudul.text = intent.getStringExtra("judul")
        tvTglBerita.text = intent.getStringExtra("tgl_berita")
        tvRating.text = "${intent.getDoubleExtra("rating",0.0)}"
        ratingBar.rating = intent.getDoubleExtra("rating",0.0).toFloat()
        tvIsiBerita.text = intent.getStringExtra("isi")

    }
}
