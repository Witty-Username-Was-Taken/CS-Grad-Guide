package edu.uga.cs.msproject.gradhelper.facultyDirectory

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import edu.uga.cs.msproject.gradhelper.R
import edu.uga.cs.msproject.gradhelper.dataObjects.Professor


class FacultyDetailActivity : AppCompatActivity() {

    lateinit var prof : Professor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty_detail)

        prof = intent.getParcelableExtra(DirectoryListActivity.INTENT_PROF_KEY)

        val prof_image = findViewById<ImageView>(R.id.professor_image)
        val prof_name = findViewById<TextView>(R.id.professor_name)
        val prof_title = findViewById<TextView>(R.id.professor_title)
        val prof_email = findViewById<TextView>(R.id.email_content)
        val prof_office = findViewById<TextView>(R.id.office_content)
        val prof_website = findViewById<TextView>(R.id.website_content)
        val prof_research = findViewById<TextView>(R.id.research_content)
        val directions_button = findViewById<Button>(R.id.button)

        val resId = resources.getIdentifier(prof.image,
            "drawable", "edu.uga.cs.msproject.gradhelper")

        prof_image.setImageResource(resId)
        prof_name.text = prof.fname + " " + prof.lname
        prof_title.text = prof.title
        prof_email.text = prof.email
        prof_office.text = prof.office + " " + prof.building
        prof_website.text = prof.website
        prof_research.text = prof.research

        directions_button.setOnClickListener {
            val gmmIntentUri =
                Uri.parse("https://plus.codes/WJWG+94 Athens, Georgia" /*+ prof.building*/)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        prof_website.setOnClickListener{
            val webpage: Uri = Uri.parse(prof.website)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

    }
}
