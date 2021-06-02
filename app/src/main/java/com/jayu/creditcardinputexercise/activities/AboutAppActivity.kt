package com.jayu.creditcardinputexercise.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.jayu.creditcardinputexercise.R

class AboutAppActivity : AppCompatActivity() {

    private lateinit var aboutAppDescription : TextView
    private lateinit var aboutMeDescription : TextView
    private lateinit var imgGitHub : ImageView
    private lateinit var imgPlayStore : ImageView
    private lateinit var imgInstagram : ImageView
    private lateinit var imgTwitter : ImageView
    private lateinit var imgLinkedIn : ImageView
    private lateinit var imgGmail : ImageView

    private val appDescription = "This app is created using Luhn Algorithm which is used for credit card number. Through this app, you can verify the card number is right or not. Don't worry your data will not be saved anywhere on cloud as this app is just basic app I have created as an assignment for my Internship and this app is also offline. This app is fully open source and the source code for this app is available at GitHub."
    private val meDescription = "My name is Jay Parmar. I'm a CE student. I am developer of Python, Android, Flutter, C language, C#, JAVA, etc. If you wanted any kind of help in any of given languages you can directly contact with me using Telegram. The link for same is given below. I am happy to help you. My all projects are open source. you can contact me or download any of my open source project source code from below mentioned button."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)
        supportActionBar?.title = "About App"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        aboutAppDescription = findViewById(R.id.aboutAppDescription)
        aboutMeDescription = findViewById(R.id.aboutMeDescription)
        imgGitHub = findViewById(R.id.imgGitHub)
        imgPlayStore = findViewById(R.id.imgPlayStore)
        imgInstagram = findViewById(R.id.imgInstagram)
        imgTwitter = findViewById(R.id.imgTwitter)
        imgLinkedIn = findViewById(R.id.imgLinkedIn)
        imgGmail = findViewById(R.id.imgGmail)

        aboutAppDescription.text = appDescription
        aboutMeDescription.text = meDescription

        imgGitHub.setOnClickListener {
            val uri: Uri = Uri.parse("http://github.com/DeveloperJayu")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        imgPlayStore.setOnClickListener {
            val uri = Uri.parse("https://play.google.com/store/apps/developer?id=Developer%20Jayu")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        imgInstagram.setOnClickListener {
            val uri = Uri.parse("https://www.instagram.com/km_60_baki_majama")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        imgGmail.setOnClickListener {
            val uri = Uri.parse("mailto:developerjayu@gmail.com")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        imgTwitter.setOnClickListener {
            val uri = Uri.parse("https://twitter.com/ParmarJay38")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        imgLinkedIn.setOnClickListener {
            val uri = Uri.parse("https://www.linkedin.com/in/developerjayu/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}