package edu.uga.cs.msproject.gradhelper

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import edu.uga.cs.msproject.gradhelper.classList.ClassListFragment
import edu.uga.cs.msproject.gradhelper.classList.ClassListFragmentDirections
import edu.uga.cs.msproject.gradhelper.dataObjects.ClassItem
import edu.uga.cs.msproject.gradhelper.dataObjects.Professor
import edu.uga.cs.msproject.gradhelper.dataObjects.Research
import edu.uga.cs.msproject.gradhelper.facultyDirectory.DirectoryListFragment
import edu.uga.cs.msproject.gradhelper.facultyDirectory.DirectoryListFragmentDirections
import edu.uga.cs.msproject.gradhelper.locationFinder.LocationListFragment
import edu.uga.cs.msproject.gradhelper.researchTopics.ResearchDetailFragment
import edu.uga.cs.msproject.gradhelper.researchTopics.ResearchDetailFragmentDirections
import edu.uga.cs.msproject.gradhelper.researchTopics.ResearchListFragment
import edu.uga.cs.msproject.gradhelper.researchTopics.ResearchListFragmentDirections

class MainActivity : AppCompatActivity(),
    DirectoryListFragment.OnDirectoryItemFragmentInteractionListener,
LocationListFragment.OnLocationListListener,
ResearchListFragment.ResearchItemInteractionListener,
ResearchDetailFragment.OnResearchDetailFragmentInteractionListener,
ClassListFragment.OnClassListInteractionListener {

//    private var directoryListFragment : NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host = supportFragmentManager.findFragmentById(R.id.nav_host)
        as NavHostFragment ?: return

        val navController = host.navController
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navController,appBarConfiguration)
//
//        setupBottomNavMenu(navController)

    }

    override fun onDirectoryItemClicked(professor: Professor) {

        val host = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment ?: return

        val action =
            DirectoryListFragmentDirections.actionDirectoryListFragmentToFacultyDetailFragment(
                professor
            )

        host.findNavController().navigate(action)
    }

    override fun onLocationItemClicked(value: String) {

        // TODO: Update to work based on current location?
        val gmmIntentUri: Uri = Uri.parse("geo:33.9480,-83.3773?q=" + value)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    override fun onResearchItemClicked(research: Research) {
        val host = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment ?: return

        val action = ResearchListFragmentDirections.actionResearchListFragmentToResearchDetailFragment(research)

        host.findNavController().navigate(action)
    }

    override fun onFacultyItemInteraction(professor: Professor) {
        val host = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment ?: return

        val action = ResearchDetailFragmentDirections.actionResearchDetailFragmentToFacultyDetailFragment(professor)

        host.findNavController().navigate(action)
    }

    override fun onClassListInteraction(classItem: ClassItem) {
        val host = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment ?: return

        val action = ClassListFragmentDirections.actionClassListFragmentToClassDetailFragment(classItem)

        host.findNavController().navigate(action)
    }

//    private fun setupBottomNavMenu(navController: NavController) {
//        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
//        bottomNav?.setupWithNavController(navController)
//    }

    companion object {
        const val INTENT_PROF_KEY = "prof"
    }
}
