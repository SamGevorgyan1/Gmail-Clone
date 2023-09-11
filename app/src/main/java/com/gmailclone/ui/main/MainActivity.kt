package com.gmailclone.ui.main

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.gmailclone.R
import com.gmailclone.databinding.ActivityMainBinding
import com.gmailclone.databinding.DialogUserSettingsBinding
import com.gmailclone.utils.extensions.hideViews
import com.gmailclone.utils.extensions.showViews
import com.gmailclone.utils.extensions.updateViewMargins
import com.gmailclone.model.EmailContent
import com.gmailclone.ui.main.callbacks.EmailDetailsListener
import com.gmailclone.ui.main.callbacks.ScrollListener
import com.gmailclone.ui.main.fragments.EmailDetailFragment
import com.gmailclone.ui.main.fragments.SearchSortFragment
import com.gmailclone.ui.main.viewmodel.SharedViewModel
import com.gmailclone.utils.Constants.KEY_EMAIL_DETAIL_OBJECT
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), ScrollListener, EmailDetailsListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val user = FirebaseAuth.getInstance().currentUser

    private val sharedViewModel: SharedViewModel by viewModels()

    val fragment = SearchSortFragment()
    private var isSearchSortFragmentVisible = false

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = binding.bottomNavigationView
        drawerLayout = binding.drawerLayout
        navigationView = binding.navigationView

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        FirebaseApp.initializeApp(this)

        setupBottomNavigationItemListener()
        setupListener()
        setupNavigation()
        setupNavigationItemListener()
        setupView()
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {

        override fun handleOnBackPressed() {
            if (EmailDetailFragment().onBackPressed()) {
                closeEmailDetail()
                isEnabled = false
                onBackPressed()
                isEnabled = true
            }
        }
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_primary, R.id.nav_promotions, R.id.nav_social, R.id.nav_starred,
                R.id.nav_snoozed, R.id.nav_important, R.id.nav_sent, R.id.nav_scheduled,
                R.id.nav_outbox, R.id.nav_drafts, R.id.nav_all_mail, R.id.nav_spam,
                R.id.nav_trash
            ),
            drawerLayout
        )
        navigationView.setupWithNavController(navController)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupNavigationItemListener() {

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_primary, R.id.nav_promotions, R.id.nav_social, R.id.nav_starred,
                R.id.nav_snoozed, R.id.nav_important, R.id.nav_sent, R.id.nav_scheduled,
                R.id.nav_outbox, R.id.nav_drafts, R.id.nav_all_mail, R.id.nav_spam,
                R.id.nav_trash -> {
                    val navController = findNavController(R.id.fragmentContainer)
                    navController.navigate(menuItem.itemId)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupListener() {

        binding.userProfileImageTextView.setOnClickListener { showUserDialog() }

        binding.composeButtonTextView.setOnClickListener {
            openCompose()

            findNavController(R.id.fragmentContainer).navigate(R.id.fragmentCompose)
        }

        binding.searchViewEditText.setOnClickListener{
            if (isSearchSortFragmentVisible) hideSearchSortFragment()
            else showSearchSortFragment()
        }
        binding.drawerButton.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        binding.searchViewEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearImageButton?.visibility = if (s.isNullOrBlank()) View.GONE else View.VISIBLE
                sharedViewModel.updateText(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.clearImageButton?.setOnClickListener {
            binding.searchViewEditText.text.clear()
        }
    }

    private fun setupBottomNavigationItemListener() {

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_meet -> {
                    navigateToMeetFragment()
                    hideViews(
                        binding.greyBackgroundView,
                        binding.searchViewEditText,
                        binding.composeButtonTextView
                    )
                    binding.titleMeetTextView.visibility = View.VISIBLE

                    updateViewMargins(binding.userProfileImageTextView, endDp = 10, context = this)
                    updateViewMargins(binding.drawerButton, 25, 10, context = this)
                    true
                }
                R.id.menu_notifications -> {
                    navigateToPrimaryFragment()
                    showViews(
                        binding.greyBackgroundView,
                        binding.searchViewEditText,
                        binding.composeButtonTextView
                    )
                    binding.titleMeetTextView.visibility = View.GONE

                    updateViewMargins(binding.userProfileImageTextView, endDp = 25, context = this)
                    updateViewMargins(binding.drawerButton, 25, 25, context = this)
                    true
                }
                else -> false
            }
        }
        bottomNavigationView.getOrCreateBadge(R.id.menu_notifications).apply {
            isVisible = true
            number = 7 }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupView() {
        val userName = user?.displayName
        binding.userProfileImageTextView.text = userName?.get(0).toString()
        binding.userProfileImageTextView.backgroundTintList = ColorStateList.valueOf(Color.BLUE)
    }

    private fun showUserDialog() {

        val dialogBinding = DialogUserSettingsBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setView(dialogBinding.root)

        dialogBinding.tvUserEmail.text = user?.email
        dialogBinding.tvUserName.text = user?.displayName

        dialogBuilder.create().show()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainer)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onScrollUp() {
        binding.bottomNavigationView.visibility = View.VISIBLE
        val buttonTextView: TextView = findViewById(R.id.composeButtonTextView)
        buttonTextView.text = "Compose"
    }

    override fun onScrollDown() {
        binding.bottomNavigationView.visibility = View.GONE
        val buttonTextView: TextView = findViewById(R.id.composeButtonTextView)
        buttonTextView.text = null
    }


    override fun openEmailDetail(fragmentId: Int?, emailContent: EmailContent?) {

        val args = Bundle().apply { putParcelable(KEY_EMAIL_DETAIL_OBJECT, emailContent) }

        fragmentId?.let { findNavController(R.id.fragmentContainer).navigate(it, args) }

        updateViewMargins(binding.fragmentContainer, context = this)

        hideViews(
            binding.greyBackgroundView, binding.searchViewEditText,
            binding.composeButtonTextView, binding.userProfileImageTextView, binding.drawerButton
        )

    }

    override fun closeEmailDetail() {
        showViews(
            binding.greyBackgroundView, binding.searchViewEditText, binding.composeButtonTextView,
            binding.userProfileImageTextView, binding.drawerButton, binding.bottomNavigationView
        )
        updateViewMargins(binding.fragmentContainer, topDp = 15, context = this)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    override fun openCompose(fragmentId: Int?, emailContent: EmailContent?) {
        val args = Bundle().apply { putParcelable(KEY_EMAIL_DETAIL_OBJECT, emailContent) }

        fragmentId?.let { findNavController(R.id.fragmentContainer).navigate(it, args) }

        updateViewMargins(binding.fragmentContainer, context = this)

        hideViews(
            binding.greyBackgroundView, binding.searchViewEditText,
            binding.composeButtonTextView, binding.userProfileImageTextView,
            binding.drawerButton, binding.bottomNavigationView, binding.composeButtonTextView
        )
    }

    override fun closeCompose() {
        binding.bottomNavigationView.visibility = View.VISIBLE
        binding.composeButtonTextView.visibility = View.GONE

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        onBackPressedCallback.remove()
    }

    private fun navigateToMeetFragment() {
        val navController = findNavController(R.id.fragmentContainer)
        navController.navigate(R.id.meetFragment)
    }

    private fun navigateToPrimaryFragment() {
        val navController = findNavController(R.id.fragmentContainer)
        navController.navigate(R.id.nav_primary)
    }

    private fun showSearchSortFragment() {

        val navController = findNavController(R.id.fragmentContainer)
        navController.navigate(R.id.searchSortFragment)

        binding.drawerButton.setBackgroundResource(R.drawable.ic_close)

        hideViews(
            binding.userProfileImageTextView,
            binding.bottomNavigationView,
            binding.composeButtonTextView
        )

        updateViewMargins(binding.greyBackgroundView, context = this)
        updateViewMargins(binding.drawerButton, startDp = 10, topDp = 25, context = this)
        updateViewMargins(binding.searchViewEditText, startDp = 25, context = this)

        binding.drawerButton.setOnClickListener { hideSearchSortFragment() }

        binding.greyBackgroundView.setBackgroundResource(R.drawable.shape_square_light_grey)


        isSearchSortFragmentVisible = true
    }

    private fun hideSearchSortFragment() {

        showViews(
            binding.userProfileImageTextView,
            binding.bottomNavigationView,
            binding.composeButtonTextView
        )

        updateViewMargins(binding.greyBackgroundView, 10, 15, 15, context = this)
        updateViewMargins(binding.drawerButton, 25, 25, context = this)
        updateViewMargins(binding.searchViewEditText, startDp = 10, context = this)

        val navController = findNavController(R.id.fragmentContainer)
        navController.popBackStack()

        binding.searchViewEditText.text.clear()

        binding.drawerButton.setBackgroundResource(R.drawable.ic_menu)

        binding.greyBackgroundView.setBackgroundResource(R.drawable.shape_rounded_light_grey)

        setupListener()

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        isSearchSortFragmentVisible = false
    }
}