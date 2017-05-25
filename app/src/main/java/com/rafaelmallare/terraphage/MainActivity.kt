package com.rafaelmallare.terraphage

/**
 * Created by Rj on 5/24/2017.
 */
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.rafaelmallare.terraphage.AttributeType.*
import com.rafaelmallare.terraphage.StatType.*
import com.rafaelmallare.terraphage.WeaponRank.*
import com.rafaelmallare.terraphage.WeaponType.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val character = Character()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        updateViewValues()

        //-------------------------------------Test Functions----------------------------------------------
        dex_val.setOnClickListener {
            character.increaseStatBy(StatType.DEX, 1)
            updateViewValues()
        }
        //-------------------------------------------------------------------------------------------------
    }

    fun updateViewValues() {
        character_name.text = character.name

        hp_total_val.text = character.getAttribute(HP).toString()
        hp_current_val.text = character.currentHealth.toString()

        init_val.text = character.getAttribute(INIT).toString()
        spd_val.text = character.getAttribute(SPD).toString()
        def_val.text = character.getAttribute(DEF).toString()
        dmg_val.text = if (character.equippedWeapon.type == Melee) character.getAttribute(MDMG).toString()
                       else character.getAttribute(RDMG).toString()

        exp_total_val.text = character.expTotal.toString()
        exp_current_val.text = character.expCurrent.toString()

        per_val.text = character.getStat(PER).toString()
        chr_val.text = character.getStat(CHR).toString()
        int_val.text = character.getStat(INT).toString()
        con_val.text = character.getStat(CON).toString()
        str_val.text = character.getStat(STR).toString()
        dex_val.text = character.getStat(DEX).toString()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}